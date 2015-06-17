package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.datastore.AirportTimeZoneStore;
import com.gembaboo.aptz.datastore.file.AirportTimeZoneFileStore;
import com.gembaboo.aptz.domain.AirportFileRecord;
import com.gembaboo.aptz.domain.XY;
import com.gembaboo.aptz.fileloader.CsvRouteBuilder;
import org.apache.camel.Message;

import java.util.List;
import java.util.Map;


public class CsvToJsonFileRouteBuilder extends CsvRouteBuilder {


    @SuppressWarnings("unchecked")
    @Override
    public void configure() throws Exception {

        final AirportTimeZoneStore airportTimeZoneStore = new AirportTimeZoneFileStore();
        super.configure();
        from(DIRECT_PROCESS_OUTPUT).process(
                exchange -> {
                    Message in = exchange.getIn();
                    List<Map> list = (List<Map>) in.getBody();
                    Map<String, AirportFileRecord> map = list.get(0);
                    AirportFileRecord record = map.get(AirportFileRecord.class.getName());
                    String airport = record.getIataCode();
                    if (null != airport && !"null".equals(airport)) {
                        Double x = record.getLatitudeDeg();
                        Double y = record.getLongitudeDeg();
                        airportTimeZoneStore.storeAirportLocation(airport, new XY(x, y));
                    }
                });
    }
}
