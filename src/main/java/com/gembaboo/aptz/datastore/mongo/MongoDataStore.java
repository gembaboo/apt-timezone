package com.gembaboo.aptz.datastore.mongo;

import com.gembaboo.aptz.datastore.DataStore;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

public class MongoDataStore<T, K> implements DataStore<T, K> {

    private MongoOperations mongoOps;

    private Class<T> clazz;

    public MongoDataStore(Class<T> clazz){
        this.clazz = clazz;
    }

    public void init(){
        try {
            mongoOps = new MongoTemplate(new MongoClient(), "database");
        } catch (UnknownHostException e) {
            throw new CanNotInitialize();
        }
    }

    @Override
    public void save(T object, K key) {
        mongoOps.save(object);
    }

    @Override
    public T findByKey(Object key) {
        return mongoOps.findById(key, this.clazz);
    }
}
