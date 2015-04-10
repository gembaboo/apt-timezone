package com.gembaboo.aptz.datastore;

public interface DataStore<T, K> {
    void save(T object, K key);

    T findByKey(K key);

    final class CanNotInitialize extends RuntimeException{
        private static final long serialVersionUID = 6331206421581330952L;
    }
}
