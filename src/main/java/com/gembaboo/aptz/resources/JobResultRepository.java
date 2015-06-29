package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.JobResult;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobResultRepository extends MongoRepository<JobResult, String> {

    JobResult findByApiKey(String apiKey);

}
