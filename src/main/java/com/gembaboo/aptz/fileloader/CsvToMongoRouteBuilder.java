package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.domain.AirportFileRecord;
import com.gembaboo.aptz.domain.AirportTimeZone;
import com.gembaboo.aptz.domain.TimeZone;
import com.gembaboo.aptz.resources.AirportTimeZoneRepository;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class CsvToMongoRouteBuilder extends CsvRouteBuilder {


    @Autowired
    private AirportTimeZoneRepository repository;

    @SuppressWarnings("unchecked")
    @Override
    public void configure() throws Exception {

        super.configure();
        from("direct:processOutput").process(
                exchange -> {
                    Message in = exchange.getIn();
                    List<Map> list = (List<Map>) in.getBody();
                    Map<String, AirportFileRecord> map = list.get(0);
                    AirportFileRecord record = map.get(AirportFileRecord.class.getName());
                    AirportTimeZone airportTimeZone = new AirportTimeZone();
                    //TODO map the airport with the timezone
                    airportTimeZone.setTimeZone(new TimeZone(ZoneId.getAvailableZoneIds().iterator().next()));
                    airportTimeZone.setAirport(record.getIataCode());
                    repository.save(airportTimeZone);
                });
    }

    public AirportTimeZoneRepository getRepository() {
        return repository;
    }

    public void setRepository(AirportTimeZoneRepository repository) {
        this.repository = repository;
    }
}
