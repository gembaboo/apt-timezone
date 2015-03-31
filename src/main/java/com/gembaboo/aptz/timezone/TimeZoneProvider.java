package com.gembaboo.aptz.timezone;

import com.gembaboo.aptz.dao.TimeZone;
import com.gembaboo.aptz.dao.XY;

public interface TimeZoneProvider {

    TimeZone getTimeZone(XY location);

}
