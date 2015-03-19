package com.gembaboo.aptz.fileloader.csv;


public class CsvToMockRouteBuilder extends CsvRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();
        from("direct:processOutput").to("mock:out");
    }
}
