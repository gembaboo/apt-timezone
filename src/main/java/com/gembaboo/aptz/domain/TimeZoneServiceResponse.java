package com.gembaboo.aptz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeZoneServiceResponse {
    private Long dstOffset;
    private Long rawOffset;
    private String timeZoneId;
    private String timeZoneName;
    private String status;
    private String error_message;
}
