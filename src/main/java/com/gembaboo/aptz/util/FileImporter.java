package com.gembaboo.aptz.util;


import java.io.File;

public class FileImporter {


    private String fileLocation = System.getProperty("user.dir");


    public File openFile(){
        File file = new File(fileLocation);
        if (!file.exists()){
            throw new RuntimeException("File "+fileLocation+" does not exists.");
        }
        return file;
    }

}
