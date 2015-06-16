package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.domain.AirportFileRecord;
import com.mongodb.MongoClient;
import org.apache.camel.Message;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

public class CsvToMongoRouteBuilder extends CsvRouteBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public void configure() throws Exception {
        final MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "database");

        super.configure();
        from("direct:processOutput").process(
                exchange -> {
                    Message in = exchange.getIn();
                    List<Map> list = (List<Map>) in.getBody();
                    Map<String, AirportFileRecord> map = list.get(0);
                    AirportFileRecord record = map.get(AirportFileRecord.class.getName());
                    mongoOps.insert(record);
                });
    }
}
