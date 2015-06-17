package com.gembaboo.aptz.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Document object providing location of an airport
 */
@Data
@Document(collection = "airports")
public class Airport {

    /**
     * Iata airport code.
     * See <a href="https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code">
     *     https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code</a>
     */
    @Id
    private String airport;


    /**
     * See also <a href="http://geojson.org/geojson-spec.html#point">http://geojson.org/geojson-spec.html#point</a>
     */
    @Field
    private GeoJsonPoint location;
}
