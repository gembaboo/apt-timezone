package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.fileloader.csv.CsvRouteBuilder;
import com.gembaboo.aptz.fileloader.csv.CsvToMongoRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.File;

/**
 * Loads the airport file into MongoDB using the csv dataformat
 */
public class AirportFileLoader extends AbstractFileLoader {

    AirportFileLoader() {
        super();
    }

    @Override
    protected void doLoadFile(File file) {
        try {
            CamelContext camelContext = createCamelContext(file);
            runCamel(camelContext);
        } catch (Exception e) {
            throw new CanNotLoadFile(e);
        }
    }

    private CamelContext createCamelContext(File file) throws Exception {
        CsvRouteBuilder routesBuilder = new CsvToMongoRouteBuilder();
        routesBuilder.setFile(file);
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(routesBuilder);
        return context;
    }

    private void runCamel(CamelContext camelContext) throws Exception {
        camelContext.start();
    }

}
