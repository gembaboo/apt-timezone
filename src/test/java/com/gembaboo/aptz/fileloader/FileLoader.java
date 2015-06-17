package com.gembaboo.aptz.fileloader;

import java.io.File;

public interface FileLoader {

    void loadFile(File file);

    void loadFile(String file);

    final class FileNotFound extends RuntimeException {
        private static final long serialVersionUID = 4347538676131587548L;
    }

    final class CanNotLoadFile extends RuntimeException {
        private static final long serialVersionUID = 5652783789222098778L;

        public CanNotLoadFile(Throwable cause) {
            super(cause);
        }
    }
}
