package com.setting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneralResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<GeneralModel> data;

    public GeneralResponse(Integer status, String message, List<GeneralModel> data) {
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

    public List<GeneralModel> getData() {
        return data;
    }
}
