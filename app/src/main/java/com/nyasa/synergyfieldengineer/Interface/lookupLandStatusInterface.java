package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lookupLandStatusInterface {

    @GET("synergyapi/api/staticlookup/landstatus")
    Call<ArrayList<ChildPojoStaticLookup>> doGetListResources();



}
