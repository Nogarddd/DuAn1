package com.example.quocphu.getdealsapplication.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String accType;
    private String birthday;
    private String avatarUrl;

    public User(){

    }

    public User(String fullName, String email, String phone, String gender, String accType, String birthday, String avatarUrl) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.accType = accType;
        this.birthday=birthday;
        this.avatarUrl=avatarUrl;
    }

    public User(String fullName, String email, String phone, String gender, String accType, String birthday) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.accType = accType;
        this.birthday=birthday;
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

    public String getBirthday() {
        return birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result  =new HashMap<>();
        result.put("fullname",getFullName());
        result.put("email",getEmail());
        result.put("phone",getPhone());
        result.put("gender",getGender());
        result.put("accType",getAccType());
        result.put("birthday",getBirthday());
        result.put("avatarUrl",getAvatarUrl());
        return result;
    }
}



