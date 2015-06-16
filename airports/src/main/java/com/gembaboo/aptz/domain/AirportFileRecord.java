package com.gembaboo.aptz.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AirportFileRecord {

    @Id
    private Integer id;

    private String ident;

    private String type;

    private String name;

    private Double latitudeDeg;

    private Double longitudeDeg;

    private String elevationFt;

    private String continent;

    private String isoCountry;

    private String isoDegion;

    private String municipality;

    private String scheduledService;

    private String gpsCode;

    private String iataCode;

    private String localCode;

    private String homeLink;

    private String wikipediaLink;

    private String keywords;
}
