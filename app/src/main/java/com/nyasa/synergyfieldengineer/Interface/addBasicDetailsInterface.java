package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface addBasicDetailsInterface {

    @FormUrlEncoded
    @POST("synergyapi/api/addBasicDetails/{case_id}")
    Call<CommonPojo> doGetListResources(@Field("clientName") String clientName,
                                        @Field("PropertyNo") String PropertyNo,
                                        @Field("FloorNo") String FloorNo,
                                        @Field("ProjectName") String ProjectName,
                                        @Field("BuildingWing") String BuildingWing,
                                        @Field("SurveyPlotNo") String SurveyPlotNo,
                                        @Field("Pincode") String Pincode,
                                        @Path("case_id") String case_id);





}
