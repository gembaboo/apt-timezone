package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.domain.TimeZone;
import com.gembaboo.aptz.domain.XY;

public interface AirportTimeZoneStore {

    void storeAirportTimezone(String airport, TimeZone timeZone);

    void storeAirportLocation(String airport, XY location);
}
