package com.gembaboo.aptz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZoneId;

/**
 * Document object providing location and timezone of an airport
 */
@Data
@Document(collection = "airport")
public class Airport extends AuditableEntity {

    /**
     * IATA airport code.
     * See <a href="https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code">
     * https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code</a>
     */
    @Id
    private String iataCode;


    /**
     * The name of the airport
     */
    @Field
    private String name;


    /**
     * The country of the airport
     */
    @Field
    private String country;


    /**
     * See also <a href="http://geojson.org/geojson-spec.html#point">http://geojson.org/geojson-spec.html#point</a>
     */
    @Field
    @GeoSpatialIndexed
    private Point location;


    /**
     * See also <a href="https://en.wikipedia.org/wiki/Tz_database#Names_of_time_zones">
     * https://en.wikipedia.org/wiki/Tz_database#Names_of_time_zones</a>
     */
    @Field
    private String timeZone;


    @JsonIgnore
    public ZoneId getZoneId() {
        if (null == timeZone) {
            return null;
        }
        return ZoneId.of(timeZone);
    }
}
