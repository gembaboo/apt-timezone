package com.gembaboo.aptz.datastore.mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
class QueryUtils {
    static final String _ID = "_id";
    /**
     * makes a $gte/$lte range query (both ends inclusive)
     */
    static DBObject between(Object from, Object to) {
        return new BasicDBObject(VersioningMongoDao._ID, new BasicDBObject(QueryOperators.GTE,
                from).append(QueryOperators.LTE, to));
    }
}
