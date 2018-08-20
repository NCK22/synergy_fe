package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface getPOccuInterface {

    @GET("synergyapi/api/getPropertyOccupancy/{case_id}")
    Call<ArrayList<HashMap<String,String>>> doGetListResources(@Path("case_id") String case_id);

}
