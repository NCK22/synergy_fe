package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ParentPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.ParentPojoLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface assignedCaseInterface {

    @FormUrlEncoded
    @POST("api/assignedcase/{user_id}")
    Call<ParentPojoAssignedCase> doGetListResources(@Field("assignedDate") String assignedDate, @Path("user_id") String case_id);


}
