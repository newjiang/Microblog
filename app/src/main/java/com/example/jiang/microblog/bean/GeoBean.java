package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

import java.util.List;

public  class GeoBean {

    private String type;
    private List<Double> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "GeoBean{" +
                "type='" + type + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}