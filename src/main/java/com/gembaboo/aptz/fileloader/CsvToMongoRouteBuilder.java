package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.domain.Airport;
import com.gembaboo.aptz.domain.AirportFileRecord;
import com.gembaboo.aptz.domain.AirportTimeZone;
import com.gembaboo.aptz.resources.AirportRepository;
import com.gembaboo.aptz.resources.AirportTimeZoneRepository;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.Map;

/**
 * Processes airport files and loads the content into MongoDB
 */
public class CsvToMongoRouteBuilder extends CsvRouteBuilder {


    /**
     * The MongoDB repository containing {@link AirportTimeZone} entries.
     */
    @Autowired
    private AirportTimeZoneRepository airportTimeZoneRepository;

    /**
     * The MongoDB repository containing {@link Airport} entries.
     */
    @Autowired
    private AirportRepository airportRepository;

    @SuppressWarnings("unchecked")
    @Override
    public void configure() throws Exception {
        super.configure();
        from(DIRECT_PROCESS_OUTPUT).routeId("save-records").process(
                exchange -> {
                    Message in = exchange.getIn();
                    List<Map> list = (List<Map>) in.getBody();
                    Map<String, AirportFileRecord> map = list.get(0);
                    AirportFileRecord record = map.get(AirportFileRecord.class.getName());
                    persistData(record);
                });
    }

    /**
     * Persists the airport record into MongoDB
     *
     * @param record Object representing one line in the airport file
     */
    private void persistData(AirportFileRecord record) {
        String airportCode = record.getIataCode();
        if (airportCode != null){
            AirportTimeZone airportTimeZone = new AirportTimeZone();
            airportTimeZone.setAirport(airportCode);
            airportTimeZoneRepository.save(airportTimeZone);
            Airport airport = new Airport();
            airport.setAirport(airportCode);
            airport.setLocation(new GeoJsonPoint(record.getLatitudeDeg(), record.getLongitudeDeg()));
            airportRepository.save(airport);
        }
    }
}
