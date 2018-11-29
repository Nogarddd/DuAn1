package com.example.ong.duan1.Model;

import java.io.Serializable;

public class Store implements Serializable {
    private String storeName;
    private String address;

    public Store(){}

    public Store(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }
}

