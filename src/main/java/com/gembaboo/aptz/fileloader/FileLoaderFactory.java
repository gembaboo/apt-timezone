package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.fileloader.airport.AirportFileLoader;

public class FileLoaderFactory {

    public static FileLoader createSimpleFileLoader() {
        return new SimpleFileLoader();
    }


    public static FileLoader createMongoFileLoader() {
        return new AirportFileLoader();
    }
}