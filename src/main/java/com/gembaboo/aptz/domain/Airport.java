package com.gembaboo.aptz.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "airport.timezones")
public class Airport {

    @Id
    private String airport;

    private TimeZone timeZone;
}
