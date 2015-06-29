package com.gembaboo.aptz.scheduling;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB repository to manage the {@link BatchStatus} object(s)
 */
public interface BatchStatusRepository extends MongoRepository<BatchStatus, String> {

    BatchStatus findByApiKey(String apiKey);

}
