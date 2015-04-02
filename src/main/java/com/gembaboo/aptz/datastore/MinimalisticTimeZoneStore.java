package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.dao.TimeZone;
import com.gembaboo.aptz.dao.XY;
import com.gembaboo.aptz.timezone.TimeZoneStore;

import java.util.HashMap;
import java.util.Map;

class MinimalisticTimeZoneStore implements TimeZoneStore {

    protected final static Map<TimeZone, XY> TIMEZONES = new HashMap();
    protected final static Map<XY, TimeZone> LOCATIONS = new HashMap();

    @Override
    public TimeZone getTimeZone(XY location) {
        TimeZone timeZone = LOCATIONS.get(location);
        if (null == timeZone){
            throw new NotFoundException();
        }
        return timeZone;
    }

    @Override
    public void storeTimeZoneWithLocation(TimeZone timeZone, XY location) {
        TimeZone storedTimeZone = null;
        try {
            storedTimeZone= getTimeZone(location);
        }catch (Exception e){
            //Noop
        }
        if (storedTimeZone != null) {
            throw new AlreadyExistsException("TimeZone " + timeZone + "already exists for location " + location.toString());
        }
        insertRecord(timeZone, location);
    }

    private static void insertRecord(TimeZone zone, XY xy) {
        TIMEZONES.put(zone, xy);
        LOCATIONS.put(xy, zone);
    }

    static {
        //Initialize the maps with some data
        insertRecord(new TimeZone("Europe/Amsterdam"), new XY(52.366667, 4.9));
    }
}
