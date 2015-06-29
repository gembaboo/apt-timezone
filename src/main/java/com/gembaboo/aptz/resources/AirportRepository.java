package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "airport", path = "airport/1")
public interface AirportRepository extends PagingAndSortingRepository<Airport, String> {


    Page<Airport> findByTimeZone(String timeZone, Pageable pageable);

    Airport findByTimeZoneNullOrderByLastModifiedDate();
}
