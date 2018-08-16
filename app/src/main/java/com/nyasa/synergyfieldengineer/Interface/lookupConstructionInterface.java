package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lookupConstructionInterface {

    @GET("synergyapi/api/staticlookup/constructionasperplan")
    Call<ArrayList<ChildPojoStaticLookup>> doGetListResources();



}
