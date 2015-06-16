package com.gembaboo.aptz.datastore.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gembaboo.aptz.datastore.DataStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class JsonFileDataStore<K, T> implements DataStore<K, T> {

    public static final String APT_JSON_FILE_DATA_STORE = "aptz.datastore.file";

    private ObjectMapper objectMapper = new ObjectMapper();

    private Path dataDir;

    public JsonFileDataStore() {
        this.dataDir = Paths.get(System.getProperty(APT_JSON_FILE_DATA_STORE, System.getProperty("java.io.tmpdir")+"/aptz"));
        if (Files.notExists(dataDir)) {
            throw new CanNotInitialize();
        }
    }


    @Override
    public void save(K key, T object) {
        try {
            Path dataFilePath = getDataFile(key);
            objectMapper.writeValue(dataFilePath.toFile(), object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T findByKey(K key) {
        try {
            final Path dataKeyDir = getDataKeyDir(key);
            if (Files.exists(dataKeyDir)) {
                Path dataFile = Files.walk(dataKeyDir, 1).filter(path -> path.toFile().isFile()).findFirst().get();
                if (Files.exists(dataFile) && Files.isRegularFile(dataFile)) {
                    return objectMapper.readValue(dataFile.toFile(), new TypeReference<T>() {
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Path getDataFile(K key) throws IOException {
        final Path dataKeyDir = getDataKeyDir(key);
        Path dataFile = Paths.get(dataKeyDir.toString(), UUID.randomUUID().toString().concat(".json"));
        if (Files.notExists(dataKeyDir)) {
            Files.createDirectories(dataKeyDir);
            Files.createFile(dataFile);
        } else {
            Optional<Path> entry = Files.walk(dataKeyDir, 1).filter(path -> path.toFile().isFile()).findFirst();
            if (entry.isPresent() && Files.isRegularFile(entry.get())) {
                dataFile = entry.get();
            } else {
                Files.createFile(dataFile);
            }
        }
        return dataFile;
    }


    private Path getDataKeyDir(K key) {
        return Paths.get(dataDir.toString(), getKeyAsDirectoryName(key));
    }

    private String getKeyAsDirectoryName(K key) {
        return key.toString().replaceAll("/", ".").replaceAll("\\\\", ".");
    }

    public Path getDataDir() {
        return dataDir;
    }

    public void setDataDir(Path dataDir) {
        if (Files.notExists(dataDir)) {
            try {
                Files.createDirectories(dataDir);
            } catch (IOException e) {
                throw new CanNotInitialize();
            }
        }
        this.dataDir = dataDir;
    }

    public void appendDataDir(String suffix) {
        setDataDir(Paths.get(getDataDir().toString(), suffix));
    }


}
