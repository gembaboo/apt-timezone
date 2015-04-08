package com.gembaboo.aptz.spring.beans;

import com.gembaboo.aptz.fileloader.FileLoader;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class JmxInvoker {

    private FileLoader fileLoader;

    @ManagedOperation
    public void loadFile(String file) {
        fileLoader.loadFile(file);
    }

    public FileLoader getFileLoader() {
        return fileLoader;
    }

    public void setFileLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }
}
