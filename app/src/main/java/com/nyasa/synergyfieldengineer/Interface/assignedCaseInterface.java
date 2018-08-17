package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.ParentPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.ParentPojoLogin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface assignedCaseInterface {

    @FormUrlEncoded
    @POST("synergyapi/api/assignedcase/{user_id}")
    Call<ArrayList<ChildPojoAssignedCase>> doGetListResources(@Field("assignedDate") String assignedDate, @Path("user_id") String case_id);



}
