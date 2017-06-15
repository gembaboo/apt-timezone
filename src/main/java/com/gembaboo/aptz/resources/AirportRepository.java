package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "airport", path = "airport/1")
public interface AirportRepository extends PagingAndSortingRepository<Airport, String> {


    Page<Airport> findByTimeZone(String timeZone, Pageable pageable);

    Airport findByTimeZoneNullOrderByLastModifiedDate();
}
