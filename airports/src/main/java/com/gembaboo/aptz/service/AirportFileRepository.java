package com.gembaboo.aptz.service;

import com.gembaboo.aptz.domain.AirportFileRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "airport", path = "airport")
public interface AirportFileRepository extends PagingAndSortingRepository<AirportFileRecord, Integer> {

    List<AirportFileRecord> findByIataCode(@Param("iataCode") String iataCode);

}
