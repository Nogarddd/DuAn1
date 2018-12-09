package com.example.quocphu.getdealsapplication.model;

public class Post {
    private String id_post;
    private String tittle;
    private String contentPost;
    private String typePost;
    private String timePost;
    private String timeStart;
    private String timeEnd;
    private String quantity;
    private String codeDeal;
    private int saved;


    public Post() {

    }

    public Post(String id_post, String tittle, String contentPost, String typePost, String timePost, String timeStart, String timeEnd, String quantity, String codeDeal) {
        this.id_post = id_post;
        this.tittle = tittle;
        this.contentPost = contentPost;
        this.timePost = timePost;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.quantity = quantity;
        this.codeDeal = codeDeal;
        this.typePost = typePost;
    }

    public String getTypePost() {
        return typePost;
    }

    public void setTypePost(String typePost) {
        this.typePost = typePost;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getCodeDeal() {
        return codeDeal;
    }

    public void setCodeDeal(String codeDeal) {
        this.codeDeal = codeDeal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id_post='" + id_post + '\'' +
                ", tittle='" + tittle + '\'' +
                ", contentPost='" + contentPost + '\'' +
                ", timePost='" + timePost + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", codeDeal='" + codeDeal + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
