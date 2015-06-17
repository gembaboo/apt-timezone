package com.gembaboo.aptz.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class XY implements Serializable {
    private static final long serialVersionUID = 2458696715043120815L;


    //Latitude
    private Double x;

    //Longitude
    private Double y;

    public XY() {
        super();
    }

    public XY(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public XY(String geoURI) {
        if (null == geoURI) {
            throw new RuntimeException("Invalid parameter");
        }
        if (!geoURI.startsWith("geo:")) {
            throw new RuntimeException("Please use the format geo:xlatitude,ylongitude");
        }
        String[] params = geoURI.split(":");
        if (params.length < 2) {
            throw new RuntimeException("Please use the format geo:xlatitude,ylongitude");
        }
        String[] location = params[1].split(",");
        this.x = Double.parseDouble(location[0]);
        this.y = Double.parseDouble(location[1]);
    }

    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
