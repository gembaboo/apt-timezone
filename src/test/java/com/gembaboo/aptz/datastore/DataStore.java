package com.gembaboo.aptz.datastore;

public interface DataStore<K, T> {
    void save(K key, T object);

    T findByKey(K key);

    final class CanNotInitialize extends RuntimeException{
        private static final long serialVersionUID = 6331206421581330952L;
    }
}
