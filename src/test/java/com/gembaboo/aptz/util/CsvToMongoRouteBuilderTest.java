package com.gembaboo.aptz.util;


import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CsvToMongoRouteBuilderTest extends CamelTestSupport {


    @Before
    public void setUpContext() throws Exception {
        CsvToMongoRouteBuilder routesBuilder = new CsvToMongoRouteBuilder();
        routesBuilder.setFileLocation(System.getProperty("user.dir")+"/src/test/resources/");
        routesBuilder.setFileName("airports.csv");
        context.addRoutes(routesBuilder);
        context.start();
    }

    @Test
    public void testReadFile() throws Exception {

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo("stream:out").whenDone(3)
                .create();

        boolean done = notify.matches(10, TimeUnit.SECONDS);
        assertTrue("Should read three lines", done);
    }

    @Test
    public void testFirstLineContainsLatitude() throws Exception {

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo("stream:out").whenDone(1).whenAnyDoneMatches(body().contains("latitude_deg"))
                .create();

        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }

    @After
    public void cleanUpContext() throws Exception {
        context.stop();
    }
}