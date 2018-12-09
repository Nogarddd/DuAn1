package com.example.quocphu.getdealsapplication.model;

import java.io.Serializable;

public class Deal implements Serializable {
    private String title;
    private String oldPrice;
    private String newPrice;
    private String dateStart;
    private String dateEnd;
    private String quantity;
    private String codeDeal;

    public Deal(){}


    public Deal(String title, String oldPrice, String newPrice, String dateStart, String dateEnd, String quantity, String codeDeal) {
        this.title = title;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.quantity = quantity;
        this.codeDeal = codeDeal;
    }

    public String getTitle() {
        return title;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCodeDeal() {
        return codeDeal;
    }

}

