package com.gembaboo.aptz.datastore;

import com.gembaboo.aptz.dto.TimeZone;
import com.gembaboo.aptz.dto.XY;

public interface TimeZoneStore {

    void storeTimeZoneWithLocation(TimeZone timeZone, XY location);

    final class AlreadyExistsException extends RuntimeException {
        private static final long serialVersionUID = 5422186970125411410L;

        public AlreadyExistsException() {
            super();
        }

        public AlreadyExistsException(String message) {
            super(message);
        }
    }
}
