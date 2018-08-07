package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ParentPojoAssignedCase;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface getCaseDetailsInterface {

    @FormUrlEncoded
    @GET("api/cases/{case_id}")
    Call<ChildPojoCase> doGetListResources(@Path("case_id") String case_id);

}
