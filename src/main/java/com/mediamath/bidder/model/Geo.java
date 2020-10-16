package com.mediamath.bidder.model;

public class Geo {
    private double lat;
    private double lon;
    private String id;
    private String country;
    private Integer zipCode;
    private Integer utcoffset;
    private Integer connectiontype;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getUtcoffset() {
        return utcoffset;
    }

    public void setUtcoffset(Integer utcoffset) {
        this.utcoffset = utcoffset;
    }

    public Integer getConnectiontype() {
        return connectiontype;
    }

    public void setConnectiontype(Integer connectiontype) {
        this.connectiontype = connectiontype;
    }
}
