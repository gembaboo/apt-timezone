package com.gembaboo.aptz.fileloader.csv;


import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class CsvRouteBuilderTest extends CamelTestSupport {


    @Test
    public void File_With3Lines_2LineRead() throws Exception {
        startCamel("airports3.csv");

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo(".*:out").whenDone(2)
                .create();

        boolean done = notify.matches(10, TimeUnit.SECONDS);
        assertTrue("Should read three lines", done);
    }

    @Test
    public void File_With3Lines_FirstOutMessageContainsId6523() throws Exception {
        startCamel("airports3.csv");

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo(".*:out").whenDone(1).and().whenAnyDoneMatches(body().convertToString().contains("6523"))
                .create();

        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }

    //@Test
    public void File_LargeFile_AllLinesRead() throws Exception {
        startCamel("airports.csv");

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo(".*:out").whenDone(46247)
                .create();

        assertTrue(notify.matches(100000, TimeUnit.SECONDS));
    }

    @Test
    public void File_With19Cols_Parsed() throws Exception {
        startCamel("airports19cols.csv");

        NotifyBuilder notify = new NotifyBuilder(context)
                .wereSentTo(".*:out").whenExactlyDone(1)
                .create();

        boolean done = notify.matches(10, TimeUnit.SECONDS);
        assertTrue("Should read three lines", done);
    }


    private void startCamel(String file) throws Exception {
        CsvRouteBuilder routesBuilder = new CsvToMockRouteBuilder();
        routesBuilder.setFile(new File(System.getProperty("user.dir") + "/src/test/resources/" + file));
        context.addRoutes(routesBuilder);
        context.start();
    }

    @After
    public void cleanUpContext() throws Exception {
        context.stop();
    }
}