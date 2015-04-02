package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.dao.TimeZone;
import com.gembaboo.aptz.dao.XY;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeZoneFileStoreTest {

    @Before
    public void setup(){
        System.setProperty(JsonFileDataStore.APT_JSON_FILE_DATA_STORE, "target/");
    }

    @Test
    public void Check_Persist_Data_Persisted(){
        TimeZoneFileStore timeZoneStore = new TimeZoneFileStore();
        timeZoneStore.storeTimeZoneWithLocation(new TimeZone("Europe/Andorra"), new XY("geo:42.5,1.516667"));
        timeZoneStore.persistsData();
        Assert.assertTrue(true);
    }
}
