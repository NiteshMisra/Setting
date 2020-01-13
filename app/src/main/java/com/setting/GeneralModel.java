package com.setting;

import com.google.gson.annotations.SerializedName;

public class GeneralModel {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;
    @SerializedName("photo")
    private String photo;
    @SerializedName("datetime")
    private String datetime;
    @SerializedName("otp")
    private String otp;

    public GeneralModel(String id, String name, String email, String phone, String password, String username, String photo, String datetime,String otp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.photo = photo;
        this.datetime = datetime;
        this.otp = otp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getOtp() {
        return otp;
    }
}
