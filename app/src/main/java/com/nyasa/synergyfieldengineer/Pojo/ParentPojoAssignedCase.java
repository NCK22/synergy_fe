package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ParentPojoAssignedCase {

    @SerializedName("")
    ArrayList<ChildPojoAssignedCase> objCase;

    public ArrayList<ChildPojoAssignedCase> getObjCase() {
        return objCase;
    }

    public void setObjCase(ArrayList<ChildPojoAssignedCase> objCase) {
        this.objCase = objCase;
    }
}
