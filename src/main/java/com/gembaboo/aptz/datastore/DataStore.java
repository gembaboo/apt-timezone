package com.gembaboo.aptz.datastore;

public interface DataStore<T> {
    void save(T object);

    T findByKey(Object key);
}
