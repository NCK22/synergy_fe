package com.nyasa.synergyfieldengineer.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class ChildPojoAssignedCase {

    @SerializedName("CaseId")
    String CaseId;

    @SerializedName("UserId")
    String UserId;

    @SerializedName("AssignedOn")
    String AssignedOn;

    @SerializedName("IsComplete")
    String IsComplete;


    @SerializedName("CompletedOn")
    String CompletedOn;


    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }
}
