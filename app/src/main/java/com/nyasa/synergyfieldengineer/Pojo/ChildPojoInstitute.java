package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

public class ChildPojoInstitute {

    @SerializedName("InstituteId")
    String InstituteId;

    @SerializedName("InstituteName")
    String InstituteName;

    @SerializedName("InstituteBranch")
    String InstituteBranch;

    @SerializedName("BranchLocality")
    String BranchLocality;


    @SerializedName("Address")
    String Address;


    @SerializedName("ContactPerson")
    String ContactPerson;


    @SerializedName("ContactNumber")
    String ContactNumber;

    public String getInstituteId() {
        return InstituteId;
    }

    public void setInstituteId(String instituteId) {
        InstituteId = instituteId;
    }

    public String getInstituteName() {
        return InstituteName;
    }

    public void setInstituteName(String instituteName) {
        InstituteName = instituteName;
    }
}

