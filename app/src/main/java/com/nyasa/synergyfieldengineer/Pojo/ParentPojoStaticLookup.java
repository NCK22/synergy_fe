package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParentPojoStaticLookup {

    @SerializedName("")
    ArrayList<ChildPojoStaticLookup> objLookup;

    public ArrayList<ChildPojoStaticLookup> getObjLookup() {
        return objLookup;
    }

    public void setObjLookup(ArrayList<ChildPojoStaticLookup> objLookup) {
        this.objLookup = objLookup;
    }
}
