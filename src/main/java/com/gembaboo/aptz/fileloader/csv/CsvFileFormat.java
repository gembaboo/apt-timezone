package com.gembaboo.aptz.fileloader.csv;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", quoting = true, generateHeaderColumns = true)
public class CsvFileFormat {
    @DataField(pos = 1, columnName = "id", required = true)
    private int id;

    @DataField(pos = 2, columnName = "ident")
    private String ident;

    @DataField(pos = 3, columnName = "type")
    private String type;

    @DataField(pos = 4, columnName = "name")
    private String name;

    @DataField(pos = 5, columnName = "latitude_deg")
    private String latitudeDeg;

    @DataField(pos = 6, columnName = "longitude_deg")
    private String longitudeDeg;

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

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitudeDeg() {
        return latitudeDeg;
    }

    public void setLatitudeDeg(String latitudeDeg) {
        this.latitudeDeg = latitudeDeg;
    }

    public String getLongitudeDeg() {
        return longitudeDeg;
    }

    public void setLongitudeDeg(String longitudeDeg) {
        this.longitudeDeg = longitudeDeg;
    }

    public String getElevationFt() {
        return elevationFt;
    }

    public void setElevationFt(String elevationFt) {
        this.elevationFt = elevationFt;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getIsoDegion() {
        return isoDegion;
    }

    public void setIsoDegion(String isoDegion) {
        this.isoDegion = isoDegion;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getScheduledService() {
        return scheduledService;
    }

    public void setScheduledService(String scheduledService) {
        this.scheduledService = scheduledService;
    }

    public String getGpsCode() {
        return gpsCode;
    }

    public void setGpsCode(String gpsCode) {
        this.gpsCode = gpsCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public void setWikipediaLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "CsvFileFormat{" +
                "id='" + id + '\'' +
                ", ident='" + ident + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", latitudeDeg='" + latitudeDeg + '\'' +
                ", longitudeDeg='" + longitudeDeg + '\'' +
                ", elevationFt='" + elevationFt + '\'' +
                ", continent='" + continent + '\'' +
                ", isoCountry='" + isoCountry + '\'' +
                ", isoDegion='" + isoDegion + '\'' +
                ", municipality='" + municipality + '\'' +
                ", scheduledService='" + scheduledService + '\'' +
                ", gpsCode='" + gpsCode + '\'' +
                ", iataCode='" + iataCode + '\'' +
                ", localCode='" + localCode + '\'' +
                ", homeLink='" + homeLink + '\'' +
                ", wikipediaLink='" + wikipediaLink + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
