package com.gembaboo.aptz.scheduling;

import com.gembaboo.aptz.domain.Airport;
import com.gembaboo.aptz.domain.JobResult;
import com.gembaboo.aptz.gateway.LocationTimeZone;
import com.gembaboo.aptz.resources.AirportRepository;
import com.gembaboo.aptz.resources.JobResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ListIterator;

@Service
@Slf4j
public class ScheduledUpdate implements Job {

    @Autowired
    private LocationTimeZone locationTimeZone;

    @Value("${google.api_key}")
    private String apiKey;

    @Autowired
    private JobResultRepository jobResultRepository;

    @Autowired
    private AirportRepository airportRepository;

    private JobResult jobResult;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        processUpdates();
    }

    public void processUpdates() {
        jobResult = calculateJobResult();
        if (jobResult.getNumberOfCalls() < 5000) {
            fillDataBase();
        }
        updateJobResult();
    }

    private void updateJobResult() {
        if (!jobResult.getJobCompleted()) {
            jobResult.setJobCompleted(true);
            jobResult.setJobFinishTime(DateTime.now());
            jobResultRepository.save(jobResult);
        }
    }

    private void fillDataBase() {
        ListIterator<Airport> airportIterator = airportRepository.findAll().listIterator();

        while (airportIterator.hasNext() && jobResult.getNumberOfCalls() < 5000) {
            Airport airport = airportIterator.next();
            try {
                if (airport.getTimeZone() == null) {
                    updateAirportUsingApi(airport);
                } else {
                    jobResult.setNumberOfIgnored(jobResult.getNumberOfIgnored() + 1);
                }

            } catch (Exception e) {
                log.error("Could not determine time zone for airport {}", airport.getAirport(), e);
                jobResult.setNumberOfIgnored(jobResult.getNumberOfIgnored() + 1);
            }
            jobResult.setNumberOfProcessed(jobResult.getNumberOfProcessed() + 1);
            jobResultRepository.save(jobResult);
        }
    }

    private void updateAirportUsingApi(Airport airport) {
        GeoJsonPoint location = airport.getLocation();
        ZoneId zoneId = locationTimeZone.getLocationTimeZone(location.getX(), location.getY());
        airport.setTimeZone(zoneId.getId());
        jobResult.setNumberOfCalls(jobResult.getNumberOfCalls() + 1);
        airportRepository.save(airport);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private JobResult calculateJobResult() {
        jobResult = jobResultRepository.findByApiKey(apiKey);
        if (null == jobResult) {
            //It was not running at all.
            jobResult = initApiUsage();
        } else if (jobResult.getJobStartTime().plusDays(1).minusSeconds(5).isBeforeNow()) {
            //The last job instance(s) already ran 24 more than hours ago
            registerNewBatch();
        }
        return jobResult;
    }

    private JobResult initApiUsage() {
        jobResult = new JobResult();
        jobResult.setApiKey(apiKey);
        registerNewBatch();
        return jobResult;
    }

    private void registerNewBatch() {
        jobResult.setJobStartTime(DateTime.now());
        jobResult.setJobCompleted(false);
        jobResult.setNumberOfCalls(0);
        jobResult.setNumberOfIgnored(0);
        jobResult.setNumberOfProcessed(0);
        jobResultRepository.save(jobResult);
    }


    public JobResult getJobResult() {
        return jobResult;
    }

}
