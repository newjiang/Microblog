package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class PlaceBean {

    private double lon;
    private String poiid;
    private String title;
    private String type;
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPoiid() {
        return poiid;
    }

    public void setPoiid(String poiid) {
        this.poiid = poiid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "PlaceBean{" +
                "lon=" + lon +
                ", poiid='" + poiid + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", lat=" + lat +
                '}';
    }
}