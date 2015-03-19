package com.gembaboo.aptz.fileloader;

import java.io.File;

public interface FileLoader {

    void loadFile(File file);

    void loadFile(String file);

    public static class FileNotFound extends RuntimeException {
        private static final long serialVersionUID = 4347538676131587548L;
    }

    public static class CanNotLoadFile extends RuntimeException {
        private static final long serialVersionUID = 5652783789222098778L;

        public CanNotLoadFile(Throwable cause) {
            super(cause);
        }
    }
}
