package com.gembaboo.aptz.fileloader.airport;

import com.gembaboo.aptz.datastore.AirportTimeZoneStore;
import com.gembaboo.aptz.datastore.file.AirportTimeZoneFileStore;
import com.gembaboo.aptz.dto.XY;
import com.mongodb.MongoClient;
import org.apache.camel.Message;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

public class CsvToJsonFileRouteBuilder extends CsvRouteBuilder {


    @SuppressWarnings("unchecked")
    @Override
    public void configure() throws Exception {

        final AirportTimeZoneStore airportTimeZoneStore =  new AirportTimeZoneFileStore();
        super.configure();
        from("direct:processOutput").process(
                exchange -> {
                    Message in = exchange.getIn();
                    List<Map> list = (List<Map>) in.getBody();
                    Map<String, CsvFileFormat> map = list.get(0);
                    CsvFileFormat record = map.get(CsvFileFormat.class.getName());
                    String airport = record.getIataCode();
                    if (null != airport && !"null".equals(airport)){
                        Double x = record.getLatitudeDeg();
                        Double y = record.getLongitudeDeg();
                        airportTimeZoneStore.storeAirportLocation(airport, new XY(x, y));
                    }
                });
    }
}
