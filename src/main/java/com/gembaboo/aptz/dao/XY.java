package com.gembaboo.aptz.dao;

import java.io.Serializable;

public class XY implements Serializable {
    private static final long serialVersionUID = 2458696715043120815L;


    private double x;

    private double y;

    public XY() {
        super();
    }

    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
