package com.nyasa.synergyfieldengineer.Interface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkTabAddInterface {

    @FormUrlEncoded
    @POST("synergyapi/api/addPropertyWordStatus")
    Call<HashMap<String,String>> addWord(@Field("buildingId") String buildingId,
                                             @Field("foundationWork") String foundationWork,
                                             @Field("plinthWork") String plinthWork,
                                             @Field("rCCWork") String rCCWork,
                                             @Field("rCCWorkSlabs") String rCCWorkSlabs,
                                             @Field("brickWork") String brickWork,
                                             @Field("flooring") String flooring,
                                             @Field("allOtherWork") String allOtherWork,
                                             @Field("internalPlaster") String internalPlaster,
                                             @Field("externalPlaster") String externalPlaster,
                                             @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyWordStatus/{case_id}")
    Call<HashMap<String,String>> updateWord(@Field("buildingId") String buildingId,
                                         @Field("foundationWork") String foundationWork,
                                         @Field("plinthWork") String plinthWork,
                                         @Field("rCCWork") String rCCWork,
                                         @Field("rCCWorkSlabs") String rCCWorkSlabs,
                                         @Field("brickWork") String brickWork,
                                         @Field("internalPlaster") String internalPlaster,
                                         @Field("externalPlaster") String externalPlaster,
                                         @Field("flooring") String flooring,
                                         @Field("allOtherWork") String allOtherWork,
                                                @Path("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/addPropertySpecifications")
    Call<HashMap<String, String>> addSpecification(@Field("foundation") String foundation,
                                 @Field("walls") String walls,
                                 @Field("doors") String doors,
                                 @Field("windows") String windows,
                                 @Field("roofing") String roofing,
                                 @Field("flooring") String flooring,
                                 @Field("internalPlasterPainting") String internalPlasterPainting,
                                 @Field("externalPlasterPainting") String externalPlasterPainting,
                                 @Field("electricalInstallation") String electricalInstallation,
                                 @Field("plumbingInstallation") String plumbingInstallation,
                                 @Field("kitchenPlatform") String kitchenPlatform,
                                 @Field("parkingFlooring") String parkingFlooring,
                                 @Field("sideMargin") String sideMargin,
                                 @Field("noOfLifts") String noOfLifts,
                                 @Field("otherAmenitySpec") String otherAmenitySpec,
                                 @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertySpecifications/{case_id}")
    Call<HashMap<String, String>> updateSpecification(@Field("foundation") String foundation,
                                         @Field("walls") String walls,
                                         @Field("doors") String doors,
                                         @Field("windows") String windows,
                                         @Field("roofing") String roofing,
                                         @Field("flooring") String flooring,
                                         @Field("internalPlasterPainting") String internalPlasterPainting,
                                         @Field("externalPlasterPainting") String externalPlasterPainting,
                                         @Field("electricalInstallation") String electricalInstallation,
                                         @Field("plumbingInstallation") String plumbingInstallation,
                                         @Field("kitchenPlatform") String kitchenPlatform,
                                         @Field("parkingFlooring") String parkingFlooring,
                                         @Field("sideMargin") String sideMargin,
                                         @Field("noOfLifts") String noOfLifts,
                                         @Field("otherAmenitySpec") String otherAmenitySpec,
                                    @Path("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/addProjectSrtEndDt")
    Call<HashMap<String, String>> addSrtEndDt(@Field("projectStartDate") String projectStartDate,
                                    @Field("projExpCompletionDate") String projExpCompletionDate,
                                    @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updateProjectSrtEndDt/{case_id}")
    Call<HashMap<String, String>> updateSrtEndDt(@Field("projectStartDate") String projectStartDate,
                                       @Field("projExpCompletionDate") String projExpCompletionDate,
                                       @Path("case_id") String case_id);




    @FormUrlEncoded
    @POST("synergyapi/api/addProjectCostDetails")
    Call<HashMap<String, String>> addCost(@Field("marketRateFrom_RsPerSqFt") String marketRateFrom_RsPerSqFt,
                              @Field("marketRateTo_RsPerSqFt") String marketRateTo_RsPerSqFt,
                              @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updateProjectCostDetails/{case_id}")
    Call<HashMap<String, String>> updateCost(@Field("marketRateFrom_RsPerSqFt") String marketRateFrom_RsPerSqFt,
                                 @Field("marketRateTo_RsPerSqFt") String marketRateTo_RsPerSqFt,
                                 @Path("case_id") String case_id);



    @FormUrlEncoded
    @POST("synergyapi/api/addGpsDetails")
    Call<HashMap<String, String>> addGPS(@Field("gPSLatitude") String gPSLatitude,
                            @Field("gPSLongitude") String gPSLongitude,
                            @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updateGpsDetails/{case_id}")
    Call<HashMap<String, String>> updateGPS(@Field("gPSLatitude") String landMark,
                               @Field("gPSLongitude") String gPSLongitude,
                               @Path("case_id") String case_id);

    @FormUrlEncoded
    @POST("synergyapi/api/addCaseValRemark")
    Call<HashMap<String, String>> addCaseVal(@Field("remark_SeqNo") String remark_SeqNo,
                             @Field("remark_Text") String remark_Text,
                             @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updateCaseValRemark/{case_id}")
    Call<HashMap<String, String>> updateCaseVal(@Field("remark_SeqNo") String remark_SeqNo,
                                @Field("remark_Text") String remark_Text,
                                @Path("case_id") String case_id);

    @FormUrlEncoded
    @POST("synergyapi/api/addViolationObserved")
    Call<HashMap<String, String>> addViolation(@Field("violationObserved") String violationObserved,
                                @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updateViolationObserved/{case_id}")
    Call<HashMap<String, String>> updateViolation(@Field("violationObserved") String violationObserved,
                                   @Path("case_id") String case_id);


}
