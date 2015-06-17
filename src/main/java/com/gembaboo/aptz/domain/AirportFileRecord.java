package com.gembaboo.aptz.domain;

import lombok.Data;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Record format of the airport file, available at
 * <a href="http://ourairports.com/data/airports.csv">http://ourairports.com/data/airports.csv</a>
 * It uses Camel Bindy annotations for defining the parsing of the csv lines.
 * (See also <a href="http://camel.apache.org/bindy.html">http://camel.apache.org/bindy.html</a>
 */
@Data
@CsvRecord(separator = ",", quoting = true, generateHeaderColumns = true)
@Entity
public class AirportFileRecord {

    @DataField(pos = 1, columnName = "id", required = true)
    @Id
    private Integer id;

    @DataField(pos = 2, columnName = "ident")
    private String ident;

    @DataField(pos = 3, columnName = "type")
    private String type;

    @DataField(pos = 4, columnName = "name")
    private String name;

    @DataField(pos = 5, columnName = "latitude_deg")
    private Double latitudeDeg;

    @DataField(pos = 6, columnName = "longitude_deg")
    private Double longitudeDeg;

    @DataField(pos = 7, columnName = "elevation_ft")
    private String elevationFt;

    @DataField(pos = 8, columnName = "continent")
    private String continent;

    @DataField(pos = 9, columnName = "iso_country")
    private String isoCountry;

    @DataField(pos = 10, columnName = "iso_region")
    private String isoDegion;

    @DataField(pos = 11, columnName = "municipality")
    private String municipality;

    @DataField(pos = 12, columnName = "scheduled_service")
    private String scheduledService;

    @DataField(pos = 13, columnName = "gps_code")
    private String gpsCode;

    @DataField(pos = 14, columnName = "iata_code")
    private String iataCode;

    @DataField(pos = 15, columnName = "local_code")
    private String localCode;

    @DataField(pos = 16, columnName = "home_link")
    private String homeLink;

    @DataField(pos = 17, columnName = "wikipedia_link")
    private String wikipediaLink;

    @DataField(pos = 18, columnName = "keywords")
    private String keywords;
}
