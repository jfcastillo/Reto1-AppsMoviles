package com.felipe.reto1_appsmoviles;

public class Place {
    private String name;
    private float rate;
    private double longitude;
    private double latitude;
    private String address;
    private String photoPath;

    public Place(String name, float rate) {
        this.name = name;
        this.rate = rate;
    }

    public Place(String name, float rate, double longitude, double latitude, String address, String photoPath ){
        this.name = name;
        this.rate = rate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.photoPath = photoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoPath() { return photoPath;}

    public void setPhotoPath(String photoPath) { this.photoPath = photoPath;}
}
