package com.gembaboo.aptz.scheduling;

import com.gembaboo.aptz.domain.Airport;
import com.gembaboo.aptz.gateway.LocationTimeZone;
import com.gembaboo.aptz.resources.AirportRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * Quartz job with the purpose to look for unknown timezones and update them.
 * Once all timezones are populated, it will maintain the airport collection by rechecking the timezone of
 * the airport's location.
 * See {@link com.gembaboo.aptz.main.config.SchedulingConfiguration} for the configuration details
 */
@Service
@Slf4j
public class ScheduledUpdate implements Job {

    // Google Maps API can be called this number of times per day
    @Value("${google.daily_call_limit}")
    private Integer DAILY_CALL_LIMIT;


    // For the apiKey we record the number of calls made for each job. Jobs can restart after 24 hours.
    @Value("${google.api_key}")
    private String API_KEY;

    @Autowired
    private LocationTimeZone locationTimeZone;

    @Autowired
    private BatchStatusRepository batchStatusRepository;

    @Autowired
    private AirportRepository airportRepository;

    // The status of the batch, for the purpose of tracking the Google Maps API calls considering the daily allowance.
    private BatchStatus batchStatus;

    /**
     * The scheduled method, invoked by Quartz Scheduler
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        calculateBatchStatus();
        if (batchStatus.getNumberOfCalls() < DAILY_CALL_LIMIT) {
            processEntry();
            jobCompleted();
        }
    }


    /**
     * Updates the database. In case it does not find record with null timezone, it gets the one updated least recently.
     */
    private void processEntry() {
        Airport airport = airportRepository.findByTimeZoneNullOrderByLastModifiedDate();
        if (airport == null) {
            // All timezone is populated. Make updates, just in case a timezone is changed.
            Sort sort = new Sort("lastModifiedDate");
            Iterator<Airport> iterator = airportRepository.findAll(sort).iterator();
            if (iterator.hasNext()) {
                airport = iterator.next();
            }
        }
        processAirport(airport);
    }

    /**
     * Updates an airport.
     *
     * @param airport
     */
    private void processAirport(Airport airport) {
        if (airport != null) {
            if (airport.getLocation() != null) {
                updateAirportUsingApi(airport);
            } else {
                log.warn("Location for airport {} ({}) is not provided, can not update timezone.", airport.getAirport(), airport.getName());
                airportRepository.save(airport);
                processEntry();
            }
        }
    }


    private void updateAirportUsingApi(Airport airport) {
        Point location = airport.getLocation();
        try {
            String timezone = locationTimeZone.getLocationTimeZone(location.getX(), location.getY()).getId();
            updateTimezone(airport, timezone);
            batchStatus.setNumberOfProcessed(batchStatus.getNumberOfProcessed() + 1);
        } catch (Exception e) {
            log.error("Could not update airport {} ({}/{}) due to error {}", airport.getAirport(), airport.getName(), airport.getCountry(), e.getMessage());
            batchStatus.setNumberOfFailed(batchStatus.getNumberOfFailed() + 1);
        } finally {
            batchStatus.setNumberOfCalls(batchStatus.getNumberOfCalls() + 1);
            airportRepository.save(airport);
        }
    }

    private void updateTimezone(Airport airport, String timezone) {
        if (timezone.equals(airport.getTimeZone())) {
            log.info("Timezone for airport {} ({}/{}) is still valid {}", airport.getAirport(), airport.getName(), airport.getCountry(), airport.getTimeZone());
        } else {
            log.info("Timezone for airport {} ({}/{}) updated from {} to {}", airport.getAirport(), airport.getName(), airport.getCountry(), airport.getTimeZone(), timezone);
            airport.setTimeZone(timezone);
        }
    }

    /**
     * Calculates the job result. In case the previous job ran 24 hours ago, it initiates a new batch.
     *
     * @return
     */
    private void calculateBatchStatus() {
        batchStatus = batchStatusRepository.findByApiKey(API_KEY);
        if (null == batchStatus) {
            //It was not running at all.
            initJobResult();
        } else if (batchStatus.getBatchStartTime().plusDays(1).isBeforeNow()) {
            //The last job instance(s) already ran 24 or more than hours ago
            registerNewBatch();
        }
    }

    /**
     * Initiates the Job Result
     */
    private void initJobResult() {
        batchStatus = new BatchStatus();
        batchStatus.setApiKey(API_KEY);
        registerNewBatch();
    }

    /**
     * Registers a batch, meaning a new daily allowance.
     */
    private void registerNewBatch() {
        batchStatus.setBatchStartTime(DateTime.now());
        batchStatus.setBatchFinishTime(null);
        batchStatus.setNumberOfCalls(0);
        batchStatus.setNumberOfProcessed(0);
        batchStatus.setNumberOfFailed(0);
        saveJobResult();
        log.info("New batch started to update airport records.");
    }

    /**
     * Registers that the job is completed.
     */
    private void jobCompleted() {
        if (batchStatus.getNumberOfCalls() == DAILY_CALL_LIMIT) {
            batchStatus.setBatchFinishTime(DateTime.now());
        }
        saveJobResult();
    }

    private void saveJobResult() {
        batchStatusRepository.save(batchStatus);
    }


    public BatchStatus getBatchStatus() {
        return batchStatus;
    }


}
