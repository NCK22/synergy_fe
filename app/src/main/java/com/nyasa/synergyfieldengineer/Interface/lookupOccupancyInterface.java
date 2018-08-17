package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface lookupOccupancyInterface {

    @GET("synergyapi/api/staticlookup/occupancystatus")
    Call<ArrayList<ChildPojoStaticLookup>> doGetListResources();



}
