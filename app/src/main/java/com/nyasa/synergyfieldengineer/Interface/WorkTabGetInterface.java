package com.nyasa.synergyfieldengineer.Interface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WorkTabGetInterface {

    @GET("synergyapi/api/getPropertyWordStatus/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getWord(@Path("case_id") String case_id);

    @GET("synergyapi/api/getPropertySpecifications/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getSpecifications(@Path("case_id") String case_id);

    @GET("synergyapi/api/getProjectSrtEndDt/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getSrtEndDt(@Path("case_id") String case_id);

    @GET("synergyapi/api/getProjectCostDetails/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getCost(@Path("case_id") String case_id);

    @GET("synergyapi/api/getGpsDetails/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getGps(@Path("case_id") String case_id);

    @GET("synergyapi/api/getViolationObserved/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getViolation(@Path("case_id") String case_id);

    @GET("synergyapi/api/getCaseValRemark/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getCaseVal(@Path("case_id") String case_id);




}
