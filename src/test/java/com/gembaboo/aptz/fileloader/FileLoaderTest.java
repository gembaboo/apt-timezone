package com.gembaboo.aptz.fileloader;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileLoaderTest {

    @Test
    public void File_Exists_CanRead() {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/airports3.csv");
        FileLoader fileLoader = FileLoaderFactory.createSimpleFileLoader();
        fileLoader.loadFile(file);
        assertTrue(true);
    }

    @Test(expected = FileLoader.FileNotFound.class)
    public void File_NoExists_ExceptionIsThrown() {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/invalid_file.csv");
        FileLoader fileLoader = FileLoaderFactory.createSimpleFileLoader();
        fileLoader.loadFile(file);
    }
}
