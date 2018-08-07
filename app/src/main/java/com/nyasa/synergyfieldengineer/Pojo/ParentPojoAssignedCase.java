package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ParentPojoAssignedCase {

    @SerializedName("")
    ArrayList<LinkedHashMap<String,String>> objCase;

    public ArrayList<LinkedHashMap<String, String>> getObjCase() {
        return objCase;
    }

    public void setObjCase(ArrayList<LinkedHashMap<String, String>> objCase) {
        this.objCase = objCase;
    }
}
