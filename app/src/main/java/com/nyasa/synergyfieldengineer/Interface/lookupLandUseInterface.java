package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lookupLandUseInterface {

    @GET("synergyapi/api/staticlookup/landuse")
    Call<ArrayList<ChildPojoStaticLookup>> doGetListResources();



}
