package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PropertyTabAddInterface {

    @FormUrlEncoded
    @POST("synergyapi/api/addPropertyBuildingDetails")
    Call<CommonPojo> addBuilding(@Field("clientName") String clientName,
                                        @Field("beforeFloorDetails") String beforeFloorDetails,
                                        @Field("Ground") String Ground,
                                        @Field("presentNoOfFloors") String presentNoOfFloors,
                                        @Field("proposedNoOfFloors") String proposedNoOfFloors,
                                        @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyBuildingDetails/{case_id}")
    Call<CommonPojo> updateBuilding(@Field("clientName") String clientName,
                                 @Field("beforeFloorDetails") String beforeFloorDetails,
                                 @Field("Ground") String Ground,
                                 @Field("presentNoOfFloors") String presentNoOfFloors,
                                 @Field("proposedNoOfFloors") String proposedNoOfFloors,
                                 @Path("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/addPropertyBoundaryLandusage")
    Call<CommonPojo> addBoundary(@Field("eastActual") String eastActual,
                                 @Field("southActual") String southActual,
                                 @Field("westActual") String westActual,
                                 @Field("northActual") String northActual,
                                 @Field("landUseActual") String landUseActual,
                                 @Field("LandStatus") String LandStatus,
                                 @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyBoundaryLandusage/{case_id}")
    Call<CommonPojo> updateBoundary(@Field("eastActual") String eastActual,
                                    @Field("southActual") String southActual,
                                    @Field("westActual") String westActual,
                                    @Field("northActual") String northActual,
                                    @Field("landUseActual") String landUseActual,
                                    @Field("LandStatus") String LandStatus,
                                    @Path("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/addPropertySurroundings")
    Call<CommonPojo> addSurrounding(@Field("constructionAsPerPlan") String constructionAsPerPlan,
                                 @Field("ageOfPropertyYears") String ageOfPropertyYears,
                                 @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertySurroundings/{case_id}")
    Call<CommonPojo> updateSurrounding(@Field("constructionAsPerPlan") String constructionAsPerPlan,
                                       @Field("ageOfPropertyYears") String ageOfPropertyYears,
                                    @Path("case_id") String case_id);




    @FormUrlEncoded
    @POST("synergyapi/api/addPropertyBuildingFloorDetail")
    Call<CommonPojo> addFloor(@Field("buildingId") String buildingId,
                              @Field("floorNoName") String floorNoName,
                              @Field("totalNoOfRooms") String totalNoOfRooms,
                              @Field("noOfLivingRooms") String noOfLivingRooms,
                              @Field("noOfKitchens") String noOfKitchens,
                              @Field("noOfBedrooms") String noOfBedrooms,
                              @Field("noOfBath") String noOfBath,
                              @Field("noOfWC") String noOfWC,
                              @Field("noOfCommonToilets") String noOfCommonToilets,
                              @Field("noOfAttachedToilets") String noOfAttachedToilets,
                              @Field("noOfAttachedTerraces") String noOfAttachedTerraces,
                              @Field("noOfAttachedBalconies") String noOfAttachedBalconies,
                              @Field("noOfDryBalconyTerraces") String noOfDryBalconyTerraces,
                              @Field("noOfParkings") String noOfParkings,
                              @Field("noOfGardens") String noOfGardens,
                              @Field("otherUnit1Name") String otherUnit1Name,
                              @Field("noOfOtherUnit1") String noOfOtherUnit1,
                              @Field("otherUnit2Name") String otherUnit2Name,
                              @Field("noOfOtherUnit2") String noOfOtherUnit2,
                              @Field("otherUnit3Name") String otherUnit3Name,
                              @Field("noOfOtherUnit3") String noOfOtherUnit3,
                              @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyBuildingFloorDetail/{case_id}")
    Call<CommonPojo> updateFloor(@Field("buildingId") String buildingId,
                                 @Field("floorNoName") String floorNoName,
                                 @Field("totalNoOfRooms") String totalNoOfRooms,
                                 @Field("noOfLivingRooms") String noOfLivingRooms,
                                 @Field("noOfKitchens") String noOfKitchens,
                                 @Field("noOfBedrooms") String noOfBedrooms,
                                 @Field("noOfBath") String noOfBath,
                                 @Field("noOfWC") String noOfWC,
                                 @Field("noOfCommonToilets") String noOfCommonToilets,
                                 @Field("noOfAttachedToilets") String noOfAttachedToilets,
                                 @Field("noOfAttachedTerraces") String noOfAttachedTerraces,
                                 @Field("noOfAttachedBalconies") String noOfAttachedBalconies,
                                 @Field("noOfDryBalconyTerraces") String noOfDryBalconyTerraces,
                                 @Field("noOfParkings") String noOfParkings,
                                 @Field("noOfGardens") String noOfGardens,
                                 @Field("otherUnit1Name") String otherUnit1Name,
                                 @Field("noOfOtherUnit1") String noOfOtherUnit1,
                                 @Field("otherUnit2Name") String otherUnit2Name,
                                 @Field("noOfOtherUnit2") String noOfOtherUnit2,
                                 @Field("otherUnit3Name") String otherUnit3Name,
                                 @Field("noOfOtherUnit3") String noOfOtherUnit3,
                                 @Path("case_id") String case_id);



    @FormUrlEncoded
    @POST("synergyapi/api/addPropertyGps")
    Call<CommonPojo> addGPS(@Field("landMark") String landMark,
                                    @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyGps/{case_id}")
    Call<CommonPojo> updateGPS(@Field("landMark") String landMark,
                               @Path("case_id") String case_id);

}
