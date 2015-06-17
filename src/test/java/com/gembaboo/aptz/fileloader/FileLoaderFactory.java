package com.gembaboo.aptz.fileloader;

public class FileLoaderFactory {

    public static FileLoader createSimpleFileLoader() {
        return new SimpleFileLoader();
    }

}