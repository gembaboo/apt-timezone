package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.dao.TimeZone;

import java.util.Set;

public class TimeZoneFileStore extends MinimalisticTimeZoneStore {

    private DataStore<TimeZone> dataStore = new JsonFileDataStore<TimeZone>();

    public void persistsData() {
        Set<TimeZone> timeZones = TIMEZONES.keySet();
        for (TimeZone timeZone : timeZones) {
            dataStore.save(timeZone);
        }
    }
}
