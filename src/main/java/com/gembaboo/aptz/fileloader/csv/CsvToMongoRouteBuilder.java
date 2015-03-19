package com.gembaboo.aptz.fileloader.csv;

import com.mongodb.Mongo;
import org.apache.camel.Message;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

public class CsvToMongoRouteBuilder extends CsvRouteBuilder {

    @Override
    public void configure() throws Exception {
        @SuppressWarnings("deprecation")
        final MongoOperations mongoOps = new MongoTemplate(new Mongo(), "database");

        super.configure();
        from("direct:processOutput").process(
                exchange -> {
                    Message in = exchange.getIn();
                    List<Map> list = (List<Map>) in.getBody();
                    Map<String, CsvFileFormat> map = list.get(0);
                    CsvFileFormat record = map.get(CsvFileFormat.class.getName());
                    mongoOps.insert(record);
                });
    }
}
