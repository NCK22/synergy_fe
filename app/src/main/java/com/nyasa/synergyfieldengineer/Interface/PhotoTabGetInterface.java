package com.nyasa.synergyfieldengineer.Interface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotoTabGetInterface {

    @GET("synergyapi/api/getDcoImgDetails/{case_id}")
    Call<ArrayList<HashMap<String,String>>> getPhotos(@Path("case_id") String case_id);

}
