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

public class JsonFileDataStore<T, K> implements DataStore<T, K> {

    public static final String APT_JSON_FILE_DATA_STORE = "aptz.datastore.file";

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String dataPath;

    private final Path dataDir;


    public JsonFileDataStore() {
        dataPath = System.getProperty(APT_JSON_FILE_DATA_STORE, System.getProperty("java.io.tmpdir"));
        this.dataDir = Paths.get(dataPath);
        if (Files.notExists(dataDir)) {
            throw new CanNotInitialize();
        }
    }

    @Override
    public void save(T object, K key) {
        try {
            Path dataFilePath = getDataFilePath(key);
            objectMapper.writeValue(dataFilePath.toFile(), object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T findByKey(K key) {
        try {
            final Path dataKeyPath = getDataKeyPath(key);
            Path dataFile = Files.walk(dataKeyPath, 1).findFirst().get();
            if (Files.exists(dataFile) && Files.isRegularFile(dataFile)) {
                return objectMapper.readValue(dataFile.toFile(), new TypeReference() {});
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    private Path getDataFilePath(K key) throws IOException {
        final Path dataKeyPath = getDataKeyPath(key);
        Path dataFilePath = Paths.get(dataKeyPath.toString(), UUID.randomUUID().toString().concat(".json"));
        if (Files.notExists(dataKeyPath)) {
            Files.createDirectories(dataKeyPath);
            Files.createFile(dataFilePath);
        } else {
            Optional<Path> entry = Files.list(Paths.get(dataPath, getKeyAsDirectoryName(key))).findFirst();
            if (entry.isPresent() && Files.isRegularFile(entry.get())) {
                dataFilePath = entry.get();
            } else {
                Files.createFile(dataFilePath);
            }
        }
        return dataFilePath;
    }

    private Path getDataKeyPath(K key) {
        return Paths.get(dataPath, getKeyAsDirectoryName(key));
    }

    private String getKeyAsDirectoryName(K key) {
        return key.toString().replaceAll("/", ".").replaceAll("\\\\", ".");
    }


}
