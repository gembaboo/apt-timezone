package com.gembaboo.aptz.datastore.file;

import com.gembaboo.aptz.domain.TimeZone;
import com.gembaboo.aptz.domain.XY;
import org.junit.Before;
import org.junit.Test;

public class AirportTimeZoneFileStoreTest {
    private AirportTimeZoneFileStore dataStore;
    private TimeZone timeZone;
    private XY xy;

    @Before
    public void setUp() throws Exception {
        System.setProperty(JsonFileDataStore.APT_JSON_FILE_DATA_STORE, System.getProperty("user.dir") + "/target");
        dataStore = new AirportTimeZoneFileStore();
        timeZone = new TimeZone();
        timeZone.setValue(java.util.TimeZone.getDefault().getID());
        xy = new XY("geo:47.4925,19.051389");
    }

    @Test
    public void testStoreAirportTimezone() throws Exception {
        dataStore.storeAirportTimezone("BUD", timeZone);
    }

    @Test
    public void testStoreAirportLocation() throws Exception {
        dataStore.storeAirportLocation("BUD", xy);
    }
}