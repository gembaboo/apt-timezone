package com.gembaboo.aptz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Simple wrapper class for TimeZone, for storing time zone in the format of "Area/Location".
 * See <a href="https://en.wikipedia.org/wiki/Tz_database#Names_of_time_zones">https://en.wikipedia.org/wiki/Tz_database#Names_of_time_zones</a>
 */
public class TimeZone implements Serializable {
    private static final long serialVersionUID = 5496807185512943273L;

    /**
     * Java timezone (ingored during persisting)
     */
    @JsonIgnore
    @Transient
    private ZoneId zoneId;

    /**
     * Time zone in the format of "Area/Location".
     */
    private String value;

    public TimeZone() {
        super();
    }

    public TimeZone(String value) {
        setValue(value);
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public String getValue() {
        return this.zoneId.getId();
    }

    public void setValue(String value) {
        this.zoneId = ZoneId.of(value);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeZone timeZone = (TimeZone) o;
        return Objects.equals(zoneId, timeZone.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId);
    }
}
