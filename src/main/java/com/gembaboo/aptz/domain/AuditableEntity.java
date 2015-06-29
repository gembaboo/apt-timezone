package com.gembaboo.aptz.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public abstract class AuditableEntity {

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private DateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private DateTime lastModifiedDate;
}
