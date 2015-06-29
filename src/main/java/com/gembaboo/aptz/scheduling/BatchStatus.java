package com.gembaboo.aptz.scheduling;

import com.gembaboo.aptz.domain.AuditableEntity;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Document to store the status of the update process.
 * See {@link ScheduledUpdate}
 */
@Data
@Document(collection = "batchstatus")
@CompoundIndexes({
        @CompoundIndex(name = "job_idx", def = "{'apiKey': 1, 'batchStartTime': 1}")
})
public class BatchStatus extends AuditableEntity {

    @Id
    private String _id;

    @Indexed
    private String apiKey;

    @Indexed
    private DateTime batchStartTime;

    private DateTime batchFinishTime;

    private Integer numberOfCalls = 0;

    private Integer numberOfProcessed = 0;

    private Integer numberOfFailed = 0;

}
