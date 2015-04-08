package com.gembaboo.aptz.timezone;

import com.gembaboo.aptz.dto.TimeZone;
import com.gembaboo.aptz.dto.XY;

public interface TimeZoneProvider {

    TimeZone getTimeZone(XY location);

    final class NotFoundException extends RuntimeException {
        private static final long serialVersionUID = 6150618649649500561L;
    }
}
