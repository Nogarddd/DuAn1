package com.example.ong.duan1.Model;

public class User {
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String accType;
    private String avatarUrl;

    public User(){

    }

    public User(String fullName, String email, String phone, String gender, String accType, String avatarUrl) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.accType = accType;
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getAccType() {
        return accType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
