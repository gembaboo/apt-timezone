package com.gembaboo.aptz.resources;

import com.gembaboo.aptz.domain.Airport;
import com.gembaboo.aptz.gateway.LocationTimeZone;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@Api("Determine timezone for an airport")
@Controller
@RequestMapping(value = "/airport/1")
public class AirportResource {

    @Reference
    private LocationTimeZone locationTimeZone;

    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(value = "/{iataCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Resource<Airport>> getAirport(@PathVariable String iataCode) {
        Airport airportRecord = airportRepository.findOne(iataCode);
        if (airportRecord == null) {
            airportRecord = new Airport();
            airportRecord.setIataCode(iataCode);
        }
        if (airportRecord.getZoneId() == null && airportRecord.getLocation() != null) {
            airportRecord = obtainZoneId(airportRecord);
        }
        Resource<Airport> airportResource = new Resource<>(airportRecord);
        //add self reference
        airportResource.add(linkTo(methodOn(AirportResource.class).getAirport(iataCode)).withSelfRel());
        airportResource.add(linkTo(methodOn(AirportResource.class).saveAirportTimeZone(iataCode, "")).withSelfRel());
        return new ResponseEntity<>(airportResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/airports?timezone={timezone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<PagedResources<Airport>> getAirportByTimezone(@PathVariable String timezone, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Airport> airportPage = airportRepository.findByTimeZone(timezone, pageable);
        return new ResponseEntity<>(assembler.toResource(airportPage), HttpStatus.OK);
    }

    @RequestMapping(value = "/{iataCode}?timezone={timezone}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Resource<Airport>> saveAirportTimeZone(@PathVariable String iataCode, @PathVariable String timezone) {
        Airport airportRecord = airportRepository.findOne(iataCode);
        if (airportRecord == null) {
            airportRecord = new Airport();
            airportRecord.setIataCode(iataCode);
        }
        airportRecord.setTimeZone(timezone);
        airportRepository.save(airportRecord);
        Resource<Airport> airportResource = new Resource<>(airportRecord);
        //add reference to the get method
        airportResource.add(linkTo(methodOn(AirportResource.class).getAirport(iataCode)).withSelfRel());
        return new ResponseEntity<>(airportResource, HttpStatus.OK);
    }


    private Airport obtainZoneId(Airport airportRecord) {
        Point location = airportRecord.getLocation();
        ZoneId zoneId = locationTimeZone.getLocationTimeZone(location.getX(), location.getY());
        updateLocalDb(airportRecord, zoneId);
        return airportRecord;
    }

    private void updateLocalDb(Airport airport, ZoneId zoneId) {
        airport.setTimeZone(zoneId.getId());
        airportRepository.save(airport);
    }
}
