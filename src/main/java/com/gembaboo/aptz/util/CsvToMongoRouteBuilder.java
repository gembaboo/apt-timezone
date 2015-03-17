package com.gembaboo.aptz.util;

import org.apache.camel.builder.RouteBuilder;


public class CsvToMongoRouteBuilder extends RouteBuilder {


    private String fileLocation = System.getProperty("user.dir");
    private String fileName = null;


    @Override
    public void configure() throws Exception {
        String url = "file:"+fileLocation+"?noop=true";
        if (null != fileName){
            url += "&fileName="+ fileName;
        }
        from(url).split().tokenize("\n").streaming().to("stream:out");
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
