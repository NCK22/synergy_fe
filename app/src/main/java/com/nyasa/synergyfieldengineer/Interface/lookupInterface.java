package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lookupInterface {

    @GET("synergyapi/api/staticlookup/foundationwork")
    Call<ArrayList<ChildPojoStaticLookup>> getFoundation();



}
