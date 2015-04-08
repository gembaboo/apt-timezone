package com.gembaboo.aptz.dto.test;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.impl.DefaultRandomGenerator;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.*;


/**
 * Provides random values of custom types for pojo testing.
 * See DefaultRandomGenerator for usage.
 */
class DtoClassesRandomGenerator extends DefaultRandomGenerator {
    private final Class<?>[] clazz;

    public DtoClassesRandomGenerator(Class<?>... clazz) {
        this.clazz = clazz;
    }

    @Override
    public Collection<Class<?>> getTypes() {
        return Arrays.asList(clazz);
    }

    @Override
    public Object doGenerate(Class<?> type) {
        if (type == XMLGregorianCalendar.class) {
            return getXMLGregorianCalendar();
        } else if (type == ZoneId.class) {
            return getRandomZoneId();
        }
        return null;
    }

    private Object getRandomZoneId() {
        final Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        final int index = Math.abs(RandomFactory.getRandomValue(Integer.class)) % availableZoneIds.size();
        return ZoneId.of(availableZoneIds.stream().skip(index).findFirst().get());
    }

    private Object getXMLGregorianCalendar() {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date(RandomFactory.getRandomValue(Long.class)));
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
