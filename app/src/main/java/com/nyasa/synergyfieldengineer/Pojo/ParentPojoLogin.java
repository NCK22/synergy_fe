package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class ParentPojoLogin {

    @SerializedName("Message")
    String Message;

    @SerializedName("userdata")
    LinkedHashMap<String,String> userdata;

    public String getMessage() {
        return Message;
    }

    public LinkedHashMap<String, String> getUserdata() {
        return userdata;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setUserdata(LinkedHashMap<String, String> userdata) {
        this.userdata = userdata;
    }
}
