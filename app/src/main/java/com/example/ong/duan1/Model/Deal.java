package com.example.ong.duan1.Model;

public class Deal {
    private String title;
    private String dateStart;
    private String dateEnd;
    private int quantity;

    public Deal(){}

    public Deal(String title, String dateStart, String dateEnd, int quantity) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public int getQuantity() {
        return quantity;
    }
}

