package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PropertTabGetInterface {

    @GET("synergyapi/api/getPropertyBuildingDetails/{case_id}")
    Call<ArrayList<ChildPojoCase>> doGetListResources(@Path("case_id") String case_id);

}
