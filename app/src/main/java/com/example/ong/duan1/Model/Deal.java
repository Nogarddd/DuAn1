package com.example.ong.duan1.Model;

public class Deal {
    private String storeName;
    private int follower;
    private String title;
    private double percentSale;
    private double newPrice;
    private double oldPrice;
    private int saved;

    public Deal(){}

    public Deal(String storeName, int follower, String title, double percentSale, double newPrice, double oldPrice, int saved) {
        this.storeName = storeName;
        this.follower = follower;
        this.title = title;
        this.percentSale = percentSale;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
        this.saved = saved;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getFollower() {
        return follower;
    }

    public String getTitle() {
        return title;
    }

    public double getPercentSale() {
        return percentSale;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public int getSaved() {
        return saved;
    }
}

