package com.gembaboo.aptz.fileloader;

import java.io.File;

public abstract class AbstractFileLoader implements FileLoader {

    @Override
    public void loadFile(File file) {
        if (!file.exists()) {
            throw new FileNotFound();
        }
        doLoadFile(file);
    }

    @Override
    public void loadFile(String file) {
        loadFile(new File(file));
    }

    protected abstract void doLoadFile(File file);

}
