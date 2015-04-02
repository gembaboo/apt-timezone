package com.gembaboo.aptz.datastore.mongo;

import com.mongodb.*;
import org.bson.BSONObject;

import java.util.ArrayList;
import java.util.List;

class VersioningMongoDao {

    static final String _VERSION = "_version";
    public static final String _ID = "_id";
    public static final String SHADOW = "history";

    /**
     * inserts a new object into the collection. The _version property must not
     * be present in the object, and will be set to 1 (integer).
     *
     * @param object
     */
    static void insert(DBCollection collection, DBObject object) {
        if (object.containsField(_VERSION)) {
            throw new IllegalArgumentException();
        }
        object.put(_VERSION, 1);
        collection.insert(object, WriteConcern.SAFE);
    }

    /**
     * updates an existing object. The object must have been the _version
     * property set to the version number of the base revision (i.e. the version
     * number to be replaced). If the current version in the DB does not have a
     * matching version number, the operation aborts with an
     * UpdateConflictException.
     * <p/>
     * After the update is successful, _version in the object is updated to the
     * new version number.
     * <p/>
     * The version that was replaced is moved into the collection's shadow
     * collection.
     *
     * @param collection
     * @param object
     * @throws UpdateConflictException
     */
    static void update(DBCollection collection, DBObject object) throws UpdateConflictException {
        checkVersionFieldExists(object);
        DBObject originalObject = getOriginalObject(collection, object);
        checkObjectVersion(object, originalObject);
        copyToShadow(collection, originalObject);
        updateOriginal(collection, object);
    }

    private static void checkObjectVersion(DBObject newObject, DBObject baseObject) throws UpdateConflictException {
        Integer baseObjectVersion = (Integer) baseObject.get(_VERSION);
        Integer baseVersion = (Integer) newObject.get(_VERSION);
        if (baseVersion != baseObjectVersion) {
            throw new UpdateConflictException(newObject, baseObject);
        }
    }

    private static DBObject getOriginalObject(DBCollection collection, DBObject object) {
        DBObject baseObject = collection.findOne(new BasicDBObject(_ID, getId(object)));
        if (baseObject == null) {
            throw new IllegalArgumentException("document to update not found in collection");
        }
        return baseObject;
    }

    private static void checkVersionFieldExists(DBObject object) {
        if (!object.containsField(_VERSION)) {
            throw new IllegalArgumentException("the base version number needs to be included as _version");
        }
    }

    private static void updateOriginal(DBCollection collection, DBObject object) throws UpdateConflictException {
        int baseVersion = (int) object.get(_VERSION);
        try {
            object.put(_VERSION, baseVersion + 1);
            DBObject found = collection.findAndModify(new BasicDBObject(_ID, getId(object)).append(_VERSION, baseVersion), object);
            if (found == null) {
                // document has changed in the mean-time. get the latest version again
                DBObject base = getOriginalObject(collection, object);
                throw new UpdateConflictException(object, base);
            }
        } catch (RuntimeException e) {
            object.put(_VERSION, baseVersion);
            throw e;
        }
    }

    private static void copyToShadow(DBCollection collection, DBObject baseObject) {
        DBCollection shadow = getShadowCollection(collection);
        baseObject.put(_ID, new BasicDBObject(_ID, getId(baseObject)).append(_VERSION, baseObject.get(_VERSION)));
        WriteResult r = shadow.insert(baseObject, WriteConcern.SAFE);
    }

    /**
     * @return the _id property of the object
     */
    private static Object getId(BSONObject o) {
        return o.get(_ID);
    }

    /**
     * @return the version number of the object (from the _version property)
     */
    private static Integer getVersion(BSONObject o) {
        return (Integer) o.get(_VERSION);
    }

    /**
     * deletes the object without checking for conflicts. An existing version is
     * moved to the shadow collection, along with a dummy version to mark the
     * deletion. This dummy version can contain optional meta-data (such as who
     * deleted the object, and when).
     */
    static DBObject remove(DBCollection collection, Object id, BSONObject metaData) {

        DBObject base = collection.findOne(new BasicDBObject(_ID, id));
        if (base == null) {
            return null;
        }
        DBCollection shadow = getShadowCollection(collection);
        int version = getVersion(base);
        BasicDBObject revId = new BasicDBObject(_ID, getId(base)).append(_VERSION, version);
        base.put(_ID, revId);
        WriteResult r = shadow.insert(base, WriteConcern.SAFE);
        // add the dummy version
        BasicDBObject dummy = new BasicDBObject(_ID, revId.append(_VERSION, version + 1)).append(_VERSION, "deleted:" + (version + 1));
        if (metaData != null){
            dummy.putAll(metaData);
        }
        shadow.insert(dummy, WriteConcern.SAFE);
        collection.remove(new BasicDBObject(_ID, id));
        return base;
    }

    /**
     * @return the shadow collection wherein the old versions of documents are
     * stored
     */
    private static DBCollection getShadowCollection(DBCollection c) {
        return c.getCollection(SHADOW);
    }

    /**
     * @return an old version of the document with the given id, at the given
     * version number
     */
    static DBObject getOldVersion(DBCollection c, long id, int version) {
        BasicDBObject query = new BasicDBObject(_ID, new BasicDBObject(_ID, id).append(_VERSION, version));
        DBObject result = getShadowCollection(c).findOne(query);
        if (result == null) {
            return null;
        }
        result.put(_ID, ((BasicDBObject) getId(result)).get(_ID));
        return result;
    }

    /**
     * The list of old versions does not include the current version of the
     * document, but it does include dummy entries to mark the deletion (if the
     * document was deleted). The list is ordered by version number.
     *
     * @return the list of old version of the document with the given id
     */
    static List<DBObject> getOldVersions(DBCollection c, Object id) {
        DBObject query = QueryUtils.between(_ID, new BasicDBObject(_ID, id)
                .append(_VERSION, 0), new BasicDBObject(_ID, id).append(_VERSION, Integer.MAX_VALUE));

        List<DBObject> result = new ArrayList<DBObject>();
        for (DBObject o : getShadowCollection(c).find(query).sort(new BasicDBObject(_ID, 1))) {
            o.put(_ID, ((BasicDBObject) getId(o)).get(_ID));
            result.add(o);
        }
        return result;
    }
}

