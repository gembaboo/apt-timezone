package com.gembaboo.aptz.datastore.file;

import com.gembaboo.aptz.datastore.DataStore;
import com.gembaboo.aptz.dto.TimeZone;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;

import static org.junit.Assert.*;

public class JsonFileDataStoreTest {

    DataStore<TimeZone, String> dataStore = new JsonFileDataStore<>();
    TimeZone timeZone = new TimeZone();

    @Before
    public void setUp() throws Exception {
        System.setProperty(JsonFileDataStore.APT_JSON_FILE_DATA_STORE, System.getProperty("user.dir")+"/target");
        timeZone.setValue(ZoneId.getAvailableZoneIds().iterator().next());
    }

    @Test
    public void testSave() throws Exception {
        dataStore.save(timeZone, timeZone.getValue());
    }

    @Test
    public void testFindByKey() throws Exception {
        dataStore.save(timeZone, timeZone.getValue());
        dataStore.findByKey(timeZone.getValue());
    }
}