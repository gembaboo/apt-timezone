package com.gembaboo.aptz.fileloader;


import com.gembaboo.aptz.fileloader.CsvRouteBuilder;

public class CsvToMockRouteBuilder extends CsvRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();
        from("direct:processOutput").to("mock:out");
    }
}
