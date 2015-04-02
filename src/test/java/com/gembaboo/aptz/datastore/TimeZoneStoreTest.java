package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.dao.XY;
import com.gembaboo.aptz.timezone.TimeZoneProvider;
import com.gembaboo.aptz.timezone.TimeZoneStore;
import org.junit.Assert;
import org.junit.Test;

public class TimeZoneStoreTest {

    @Test(expected = TimeZoneProvider.NotFoundException.class)
    public void When_Unknown_Location_Provided_Expect_Exception() {
        TimeZoneStore provider = new MinimalisticTimeZoneStore();
        provider.getTimeZone(new XY(0.0, 0.0));
    }

    @Test
    public void Given_Null_Null_TimeZone_Retrieved() {
        JsonFileDataStore timeZoneStore = new JsonFileDataStore();
        Assert.assertNull(timeZoneStore.findByKey(null));
    }
}
