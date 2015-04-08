package com.gembaboo.aptz.fileloader.airport;

import com.gembaboo.aptz.fileloader.AbstractFileLoader;
import com.gembaboo.aptz.fileloader.airport.CsvRouteBuilder;
import com.gembaboo.aptz.fileloader.airport.CsvToMongoRouteBuilder;
import org.apache.camel.CamelContext;
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
