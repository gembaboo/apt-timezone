package com.gembaboo.aptz.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Document object to store airport and its Timezone
 */
@Data
@Document(collection = "airport.timezones")
public class AirportTimeZone {


    /**
     * Iata airport code.
     * See <a href="https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code">
     *     https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code</a>
     */
    @Id
    private String airport;

    /**
     * Timezone of the airport.
     */
    private TimeZone timeZone;
}
