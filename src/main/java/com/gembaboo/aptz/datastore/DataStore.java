package com.gembaboo.aptz.datastore;

public interface DataStore<T> {
    void save(T object);

    T findByKey(Object key);

    final class CanNotInitialize extends RuntimeException{
        private static final long serialVersionUID = 6331206421581330952L;
    }
}
