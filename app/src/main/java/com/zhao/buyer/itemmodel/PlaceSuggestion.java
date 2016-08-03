package com.zhao.buyer.itemmodel;

/**
 * Created by zhao on 2016/5/12.
 */
public class PlaceSuggestion {
    private String name;
    private String city;
    private String district;
    private double longitude;
    private double latitude;

    public PlaceSuggestion(String name,String city,String district,double longitude,double latitude){
        this.name = name;
        this.city = city;
        this.district = district;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getName() {
        return name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
}
