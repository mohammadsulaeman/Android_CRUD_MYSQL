package com.example.universitas.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseJson {

    @Expose
    @SerializedName("data")
    private String data;

    @Expose
    @SerializedName("message")
    private String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
