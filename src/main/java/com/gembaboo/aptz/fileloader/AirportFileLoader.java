package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.fileloader.AbstractFileLoader;
import com.gembaboo.aptz.fileloader.CsvRouteBuilder;
import com.gembaboo.aptz.fileloader.CsvToMongoRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.File;

/**
 * Loads the airport file into MongoDB using the airport dataformat
 */
public class AirportFileLoader extends AbstractFileLoader {


    public AirportFileLoader() {
        super();
    }

    @Override
    protected void doLoadFile(File file) {
        try {
            CsvRouteBuilder routesBuilder = createRouteBuilder(file);
            CamelContext camelContext = createCamelContext(file, routesBuilder);
            runCamel(camelContext);
            routesBuilder.getDoneSignal().await();
        } catch (Exception e) {
            throw new CanNotLoadFile(e);
        }
    }

    private CamelContext createCamelContext(File file, RoutesBuilder routesBuilder) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(routesBuilder);
        return context;
    }

    private CsvRouteBuilder createRouteBuilder(File file) {
        CsvRouteBuilder routesBuilder = new CsvToMongoRouteBuilder();
        routesBuilder.setFile(file);
        return routesBuilder;
    }

    private void runCamel(CamelContext camelContext) throws Exception {
        camelContext.start();
    }

}
