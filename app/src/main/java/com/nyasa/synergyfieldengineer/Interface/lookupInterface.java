package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lookupInterface {

    @GET("synergyapi/api/staticlookup/foundationwork")
    Call<ArrayList<ChildPojoStaticLookup>> getFoundation();

    @GET("synergyapi/api/staticlookup/kitchen")
    Call<ArrayList<ChildPojoStaticLookup>> getKitchen();

    @GET("synergyapi/api/staticlookup/doors")
    Call<ArrayList<ChildPojoStaticLookup>> getDoors();

    @GET("synergyapi/api/staticlookup/internalfinish")
    Call<ArrayList<ChildPojoStaticLookup>> getInternalFinish();

    @GET("synergyapi/api/staticlookup/externalfinish")
    Call<ArrayList<ChildPojoStaticLookup>> getExternalFinish();

    @GET("synergyapi/api/staticlookup/flooring")
    Call<ArrayList<ChildPojoStaticLookup>> getFlooring();

    @GET("synergyapi/api/staticlookup/walls")
    Call<ArrayList<ChildPojoStaticLookup>> getWalls();

    @GET("synergyapi/api/staticlookup/plumbing")
    Call<ArrayList<ChildPojoStaticLookup>> getPlumbing();

    @GET("synergyapi/api/staticlookup/specfoundation")
    Call<ArrayList<ChildPojoStaticLookup>> getSpecFoundation();

    @GET("synergyapi/api/staticlookup/windows")
    Call<ArrayList<ChildPojoStaticLookup>> getWindows();

    @GET("synergyapi/api/staticlookup/parkingfloor")
    Call<ArrayList<ChildPojoStaticLookup>> getParkingFloor();

    @GET("synergyapi/api/staticlookup/electric")
    Call<ArrayList<ChildPojoStaticLookup>> getElectric();

}
