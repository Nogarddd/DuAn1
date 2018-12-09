package com.example.quocphu.getdealsapplication.model;

import java.io.Serializable;

public class Store implements Serializable {
    private String storeName;
    private String address;
    private int follower;

    public Store(){}

    public Store(String storeName, String address, int follower) {
        this.storeName = storeName;
        this.address = address;
        this.follower=follower;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }


    public int getFollower() {
        return follower;
    }
}

