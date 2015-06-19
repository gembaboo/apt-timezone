package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.Airport;
import com.gembaboo.aptz.domain.AirportTimeZone;
import com.gembaboo.aptz.domain.TimeZone;
import com.gembaboo.aptz.domain.TimeZoneServiceResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Api(description = "Determine timezone for an airport")
@Controller
@RequestMapping(value = "/location/1")
public class TimeZoneClientService {


    @Value("${api_key}")
    private String apiKey;

    private RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://maps.googleapis.com/maps/api/timezone/json?location={xlat},{ylon}&timestamp={timestamp}&key={google.api_key}";


    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportTimeZoneRepository airportTimeZoneRepository;


    /**
     * https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=API_KEY
     *
     * @param x The latitude
     * @param y The longitude
     * @return The timezone
     */
    @RequestMapping(value = "/timezone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimeZone getLocationTimeZone(@RequestParam Double x, @RequestParam Double y) {
        //Create the rest request
        TimeZone timeZone = new TimeZone();
        Map<String, String> params = new HashMap<String, String>();
        params.put("xlat", String.format(Locale.ENGLISH, "%.15f", x));
        params.put("ylon", String.format(Locale.ENGLISH, "%.15f", y));
        params.put("timestamp", String.format(Locale.ENGLISH, "%d", System.currentTimeMillis() / 1000));
        params.put("google.api_key", apiKey);

        //Call google API
        TimeZoneServiceResponse response = restTemplate.getForObject(url, TimeZoneServiceResponse.class, params);

        //Check response
        if (response.getStatus().equals("OK")) {
            timeZone.setValue(response.getTimeZoneId());
        }else{
            log.error("Could not get timezone {}", response);
        }
        return timeZone;
    }

    @RequestMapping(value = "/timezone/airport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimeZone getAirportTimeZone(@RequestParam String airport) {
        Airport airportRecord = airportRepository.findOne(airport);
        if (airportRecord == null){
            throw new RuntimeException("Airport not found");
        }
        AirportTimeZone airportTimeZone = airportTimeZoneRepository.findOne(airport);
        if (airportTimeZone.getTimeZone()!=null){
            return airportTimeZone.getTimeZone();
        }

        GeoJsonPoint location = airportRecord.getLocation();
        TimeZone locationTimeZone = getLocationTimeZone(location.getX(), location.getY());
        airportTimeZone.setTimeZone(locationTimeZone);
        airportTimeZoneRepository.save(airportTimeZone);

        return locationTimeZone;
    }


}
