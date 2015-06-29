package com.gembaboo.aptz.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Base class for auditable domain objects, see {@link com.gembaboo.aptz.main.config.MongoConfiguration}
 * for the configuiration of this feature
 */
public abstract class AuditableEntity {

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private DateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @Indexed
    @LastModifiedDate
    private DateTime lastModifiedDate;
}
