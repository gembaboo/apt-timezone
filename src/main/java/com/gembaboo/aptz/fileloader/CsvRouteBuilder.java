package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.domain.AirportFileRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ShutdownRoute;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

import java.io.File;
import java.util.concurrent.CountDownLatch;


public abstract class CsvRouteBuilder extends RouteBuilder {

    private File file = new File(System.getProperty("user.dir"));

    private CountDownLatch doneSignal;

    @Override
    public void configure() throws Exception {
        configureContext();
        String fileUri = getFileUri();
        this.doneSignal = new CountDownLatch(1);
        from(fileUri).routeId("read-file").startupOrder(1)
                .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                .onCompletion().process(getCompletionCallback()).end().split()
                .tokenize("\n").streaming().unmarshal().string("UTF-8")
                .filter().simple("${property.CamelSplitIndex} > 0")
                .transform(body().regexReplaceAll("\"\"", ""))
                .unmarshal().bindy(BindyType.Csv, AirportFileRecord.class.getPackage().getName())
                .to("direct:processOutput");
    }


    /**
     * Releases the latch
     */
    private Processor getCompletionCallback() {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                if (null != doneSignal) {
                    doneSignal.countDown();
                }
            }
        };
    }


    private void configureContext() {
        //This route should stop after one file has been processed, but not before that.
        getContext().setShutdownRoute(ShutdownRoute.Defer);
        getContext().getShutdownStrategy().setTimeout(1);
    }

    private String getFileUri() {
        String url = "file:" + file.getParent() + "?noop=true&charset=utf-8";
        if (file.isFile()) {
            url += "&fileName=" + file.getName();
        }
        return url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public CountDownLatch getDoneSignal() {
        return doneSignal;
    }
}
