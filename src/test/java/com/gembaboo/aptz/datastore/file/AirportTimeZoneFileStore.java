package com.gembaboo.aptz.datastore.file;

import com.gembaboo.aptz.datastore.AirportTimeZoneStore;
import com.gembaboo.aptz.domain.TimeZone;
import com.gembaboo.aptz.domain.XY;

import java.util.*;

/**
 * An experimental implementation of the AirportTimeZoneStore
 */
public class AirportTimeZoneFileStore implements AirportTimeZoneStore {

    private JsonFileDataStore<String, TimeZone> airportTimezone = new JsonFileDataStore<>();
    private JsonFileDataStore<String, XY> airportLocation = new JsonFileDataStore<>();
    private JsonFileDataStore<String, List<String>> timezoneAirports = new JsonFileDataStore<>();

    public AirportTimeZoneFileStore() {
        airportTimezone.appendDataDir("airport-timezone");
        timezoneAirports.appendDataDir("timezone-airports");
        airportLocation.appendDataDir("airport-location");
    }


    @Override
    public void storeAirportTimezone(String airport, TimeZone timeZone) {
        airportTimezone.save(airport, timeZone);
        List<String> airports = timezoneAirports.findByKey(timeZone.getValue());
        if (null == airports) {
            airports = new ArrayList<>();
        }
        airports.add(airport);
        Set<String> set = new HashSet<>();
        set.addAll(airports);
        airports.clear();
        airports.addAll(set);
        timezoneAirports.save(timeZone.getValue(), airports);
    }

    @Override
    public void storeAirportLocation(String airport, XY location){
        airportLocation.save(airport, location);
    }

}
