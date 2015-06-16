package com.gembaboo.aptz.datastore.mongo;

import com.mongodb.DBObject;


class UpdateConflictException extends Exception {

    private static final long serialVersionUID = 5173128629824389276L;

    private final DBObject localVersion;
    private final DBObject databaseVersion;

    UpdateConflictException(DBObject local, DBObject current) {
        this.localVersion = local;
        this.databaseVersion = current;
    }

    int getUpdateBaseVersionNumber() {
        return (Integer) localVersion.get(VersioningMongoDao._VERSION);
    }

    int getConflictingCurrentVersionNumber() {
        return (Integer) databaseVersion.get(VersioningMongoDao._VERSION);
    }

    Object getDocumentId() {
        return localVersion.get("_id");
    }
}