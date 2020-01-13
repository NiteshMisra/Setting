package com.setting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateNameResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String data;

    public UpdateNameResponse(Integer status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
