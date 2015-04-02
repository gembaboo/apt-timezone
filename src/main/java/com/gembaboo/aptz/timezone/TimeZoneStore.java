package com.gembaboo.aptz.timezone;

import com.gembaboo.aptz.dao.TimeZone;
import com.gembaboo.aptz.dao.XY;

import java.util.spi.TimeZoneNameProvider;

public interface TimeZoneStore extends TimeZoneProvider {

    void storeTimeZoneWithLocation(TimeZone timeZone, XY location);

    public final static class AlreadyExistsException extends RuntimeException {
        private static final long serialVersionUID = 5422186970125411410L;

        public AlreadyExistsException() {
            super();
        }

        public AlreadyExistsException(String message) {
            super(message);
        }
    }
}
