package com.gembaboo.aptz.fileloader;

public class FileLoaderFactory {

    public static FileLoader createSimpleFileLoader() {
        return new SimpleFileLoader();
    }


    public static FileLoader createMongoFileLoader() {
        return new AirportFileLoader();
    }
}