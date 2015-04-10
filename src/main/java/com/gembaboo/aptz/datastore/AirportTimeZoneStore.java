package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.dto.TimeZone;
import com.gembaboo.aptz.dto.XY;

public interface AirportTimeZoneStore {

    void storeAirportTimezone(String airport, TimeZone timeZone);

    void storeAirportLocation(String airport, XY location);
}
