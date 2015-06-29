package com.gembaboo.aptz.domain;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "apiusage")
@CompoundIndexes({
        @CompoundIndex(name = "job_idx", def = "{'apiKey': 1, 'jobStartTime': 1}")
})
public class JobResult extends AuditableEntity {

    @Id
    private String _id;

    @Indexed
    private String apiKey;

    private DateTime jobStartTime;

    private DateTime jobFinishTime;

    private Boolean jobCompleted = false;

    private Integer numberOfCalls = 0;

    private Integer numberOfIgnored = 0;

    private Integer numberOfProcessed = 0;

}
