package com.gembaboo.aptz.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class JsonFileDataStore<T> implements DataStore<T> {

    public static final String APT_JSON_FILE_DATA_STORE = "aptz.json.file.data.store";

    private ObjectMapper objectMapper = new ObjectMapper();

    private String dataPath = System.getProperty(APT_JSON_FILE_DATA_STORE, System.getProperty("java.io.tmpdir"));

    @Override
    public void save(T object) {
        try {
            File file = new File(dataPath + "/" + getFileName(object));
            objectMapper.writeValue(file, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T findByKey(Object key) {
        return null;
    }

    private String getFileName(T object) {
        return object.getClass().getSimpleName() + "-" + UUID.randomUUID() + ".json";
    }
}
