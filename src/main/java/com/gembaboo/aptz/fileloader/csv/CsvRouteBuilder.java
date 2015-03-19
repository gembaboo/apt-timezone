package com.gembaboo.aptz.fileloader.csv;

import org.apache.camel.ShutdownRoute;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

import java.io.File;


public abstract class CsvRouteBuilder extends RouteBuilder {

    private File file = new File(System.getProperty("user.dir"));

    @Override
    public void configure() throws Exception {
        configureContext();

        String fileUri = getFileUri();
        from(fileUri).routeId("read-file").startupOrder(1)
                .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks).split()
                .tokenize("\n").streaming().unmarshal().string("UTF-8")
                .filter().simple("${property.CamelSplitIndex} > 0")
                .transform(body().regexReplaceAll("\"\"", ""))
                .unmarshal().bindy(BindyType.Csv, CsvFileFormat.class.getPackage().getName())
                .to("direct:processOutput");
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

}
