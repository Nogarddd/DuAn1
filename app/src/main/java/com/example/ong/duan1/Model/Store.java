package com.example.ong.duan1.Model;

import java.io.Serializable;

public class Store implements Serializable {
    private String storeName;
    private String address;
    private double lat;
    private double lng;
    private int follower;

    public Store(){}

    public Store(String storeName, String address, double lat, double lng, int follower) {
        this.storeName = storeName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.follower=follower;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getFollower() {
        return follower;
    }
}

