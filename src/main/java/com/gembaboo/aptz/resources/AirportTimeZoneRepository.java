package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.AirportTimeZone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "timezone", path = "timezone/1")
public interface AirportTimeZoneRepository extends MongoRepository<AirportTimeZone, String> {


}
