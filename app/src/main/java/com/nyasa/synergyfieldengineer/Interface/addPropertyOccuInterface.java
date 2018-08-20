package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface addPropertyOccuInterface {

    @FormUrlEncoded
    @POST("synergyapi/api/addPropertyOccupancy")
    Call<CommonPojo> insertOccupancy(@Field("occupancyStatus") String occupancyStatus,
                                        @Field("relationWithOccupant") String relationWithOccupant,
                                        @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyOccupancy/{case_id}")
    Call<CommonPojo> updateOccupancy(@Field("occupancyStatus") String occupancyStatus,
                                        @Field("relationWithOccupant") String relationWithOccupant,
                                        @Path("case_id") String case_id);





}
