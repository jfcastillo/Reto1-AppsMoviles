package com.felipe.reto1_appsmoviles;

public class Place {
    private String name;
    private double rate;
    private double longitude;
    private double latitude;
    private String address;

    public Place(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public Place(String name, double rate, double longitude, double latitude, String address) {
        this.name = name;
        this.rate = rate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
