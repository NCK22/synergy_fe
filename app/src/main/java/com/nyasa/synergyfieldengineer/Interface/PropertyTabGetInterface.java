package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PropertyTabGetInterface {

    @GET("synergyapi/api/getPropertyBuildingDetails/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getBuilding(@Path("case_id") String case_id);


    @GET("synergyapi/api/getPropertyBoundaryLandusage/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getBoundaries(@Path("case_id") String case_id);

    @GET("synergyapi/api/getPropertySurroundings/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getSurroundings(@Path("case_id") String case_id);

    @GET("synergyapi/api/getPropertyBuildingFloorDetail/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getFloor(@Path("case_id") String case_id);

    @GET("synergyapi/api/getPropertyGps/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getGps(@Path("case_id") String case_id);

}
