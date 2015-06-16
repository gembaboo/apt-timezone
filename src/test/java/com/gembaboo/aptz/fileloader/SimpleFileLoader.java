package com.gembaboo.aptz.fileloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Prints the content of a file to the standart out using java nio
 */
public class SimpleFileLoader extends AbstractFileLoader {

    SimpleFileLoader() {
        super();
    }

    @Override
    protected void doLoadFile(File file) {
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
