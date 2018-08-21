package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class CommonPojo {

    @SerializedName("Message ")
    String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
