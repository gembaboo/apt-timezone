package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "airport", path = "airport")
public interface AirportRepository extends MongoRepository<Airport, String> {


}
