package com.gembaboo.aptz.fileloader;


import com.gembaboo.aptz.fileloader.CsvRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ShutdownRoute;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.model.RouteDefinition;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CsvToMockRouteBuilder extends CsvRouteBuilder {

    private CountDownLatch doneSignal;

    @Override
    public void configure() throws Exception {
        super.configure();
        configureRunOnlyOnce();
        from(DIRECT_PROCESS_OUTPUT).to("mock:out");
    }

    public void configureRunOnlyOnce() {
        //Wait for 1 second to initiate shutdown
        getContext().getShutdownStrategy().setTimeout(1);
        //This route should stop after one file has been processed, but not before that.
        getContext().setShutdownRoute(ShutdownRoute.Defer);

        //parse-file route should call completion callback to trigger CountDownLatch.
        List<RouteDefinition> routes = getRouteCollection().getRoutes();
        routes.stream().filter(route -> route.getId().equals("parse-file")).forEach(route -> {
            route.startupOrder(1).shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                    .onCompletion().process(getCompletionCallback()).end();
        });

        this.doneSignal = new CountDownLatch(1);
    }

    /**
     * Releases the latch
     */
    private Processor getCompletionCallback() {
        return exchange -> {
            if (null != doneSignal) {
                doneSignal.countDown();
            }
        };
    }

    public CountDownLatch getDoneSignal() {
        return doneSignal;
    }

}
