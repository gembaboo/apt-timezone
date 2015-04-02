package com.gembaboo.aptz.dao;

import org.codehaus.groovy.util.StringUtil;

import java.io.Serializable;
import java.util.Objects;

public class XY implements Serializable {
    private static final long serialVersionUID = 2458696715043120815L;


    private Double x;

    private Double y;

    public XY() {
        super();
    }

    public XY(String geoURI){
        if (null == geoURI){
            throw new RuntimeException("Invalid parameter");
        }
        if (!geoURI.startsWith("geo:")){
            throw new RuntimeException("Please use the format geo:xloc,yloc");
        }
        String[] params = geoURI.split(":");
        if (params.length<2){
            throw new RuntimeException("Please use the format geo:xloc,yloc");
        }
        String[] location = params[1].split(",");
        this.x = Double.parseDouble(location[0]);
        this.y = Double.parseDouble(location[1]);
    }

    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XY xy = (XY) o;
        return Objects.equals(x, xy.x) &&
                Objects.equals(y, xy.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "XY{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
