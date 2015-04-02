package com.gembaboo.aptz.timezone;

import com.gembaboo.aptz.dao.TimeZone;
import com.gembaboo.aptz.dao.XY;

public interface TimeZoneProvider {

    TimeZone getTimeZone(XY location);

    public final static class NotFoundException extends RuntimeException {
        private static final long serialVersionUID = 6150618649649500561L;
    }
}
