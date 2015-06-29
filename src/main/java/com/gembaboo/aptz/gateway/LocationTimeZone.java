package com.gembaboo.aptz.gateway;

import com.gembaboo.aptz.domain.TimeZoneServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Retrieves timezone using Google Map API,
 * https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=API_KEY
 */
@Slf4j
@Service
public class LocationTimeZone {

    @Value("${google.api_key}")
    private String apiKey;

    private RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://maps.googleapis.com/maps/api/timezone/json?location={xlat},{ylon}&timestamp={timestamp}&key={google.api_key}";

    /**
     * @param x The latitude
     * @param y The longitude
     * @return The timezone
     */
    public ZoneId getLocationTimeZone(@RequestParam Double x, @RequestParam Double y) {
        //Create the rest request
        Map<String, String> params = new HashMap<String, String>();
        params.put("xlat", String.format(Locale.ENGLISH, "%.15f", x));
        params.put("ylon", String.format(Locale.ENGLISH, "%.15f", y));
        params.put("timestamp", String.format(Locale.ENGLISH, "%d", System.currentTimeMillis() / 1000));
        params.put("google.api_key", apiKey);

        //Call google API
        TimeZoneServiceResponse response = restTemplate.getForObject(url, TimeZoneServiceResponse.class, params);
        String timeZoneId = "";
        //Check response
        if (response.getStatus().equals("OK")) {
            timeZoneId = response.getTimeZoneId();
        } else if (response.getStatus().equals("OVER_QUERY_LIMIT")){
            throw new OverQueryLimitException();
        } else {
            log.error("Could not get timezone {}", response);
        }
        ZoneId result = null;
        try {
            result = ZoneId.of(timeZoneId);
        } catch (RuntimeException e) {
            log.error("Could not determine result. The returned time zone {} is not valid. Parameters: x:{}, y:{}", response.getTimeZoneId(), x, y, e);
            throw e;
        }
        return result;
    }

    public final class OverQueryLimitException extends RuntimeException{
        private static final long serialVersionUID = 3331206221585330957L;

        public OverQueryLimitException() {
            super("Status is OVER_QUERY_LIMIT");
        }
    }
}