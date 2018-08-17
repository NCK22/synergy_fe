package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

public class ChildPojoStaticLookup {

    @SerializedName("StaticLookupId")
    String StaticLookupId;

    @SerializedName("LookupType")
    String LookupType;

    @SerializedName("LookupItemValue")
    String LookupItemValue;

    @SerializedName("UserEditable")
    String UserEditable;


    @SerializedName("LookupDescription")
    String LookupDescription;

    public String getStaticLookupId() {
        return StaticLookupId;
    }

    public void setStaticLookupId(String staticLookupId) {
        StaticLookupId = staticLookupId;
    }

    public String getLookupType() {
        return LookupType;
    }

    public void setLookupType(String lookupType) {
        LookupType = lookupType;
    }

    public String getLookupItemValue() {
        return LookupItemValue;
    }

    public void setLookupItemValue(String lookupItemValue) {
        LookupItemValue = lookupItemValue;
    }

    public String getUserEditable() {
        return UserEditable;
    }

    public void setUserEditable(String userEditable) {
        UserEditable = userEditable;
    }

    public String getLookupDescription() {
        return LookupDescription;
    }

    public void setLookupDescription(String lookupDescription) {
        LookupDescription = lookupDescription;
    }
}

