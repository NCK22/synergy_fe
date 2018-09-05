package com.nyasa.synergyfieldengineer.Interface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PhotoTabAddInterface {

    @FormUrlEncoded
    @POST("synergyapi/api/addDcoImgDetails")
    Call<HashMap<String, String>> addPhoto(@Field("userFileName") String userFileName,
                                           @Field("systemFileName") String systemFileName,
                                           @Field("fileExtension") String fileExtension,
                                           @Field("uploadPath") String uploadPath,
                                           @Field("uploadedOn") String uploadedOn,
                                           @Field("uploadedBy") String uploadedBy,
                                           @Field("locationMap") String locationMap,
                                           @Field("docImage_Caption") String docImage_Caption,
                                           @Field("originalPath") String originalPath,
                                           @Field("uploadFileType") String uploadFileType,
                                           @Field("photoType") String photoType,
                                           @Field("case_id") String case_id);


    @FormUrlEncoded
    @POST("synergyapi/api/updatePropertyWordStatus/{case_id}")
    Call<HashMap<String, String>> updatePhoto(@Field("userFileName") String userFileName,
                                              @Field("systemFileName") String systemFileName,
                                              @Field("fileExtension") String fileExtension,
                                              @Field("uploadPath") String uploadPath,
                                              @Field("uploadedOn") String uploadedOn,
                                              @Field("uploadedBy") String uploadedBy,
                                              @Field("locationMap") String locationMap,
                                              @Field("docImage_Caption") String docImage_Caption,
                                              @Field("originalPath") String originalPath,
                                              @Field("uploadFileType") String uploadFileType,
                                              @Field("photoType") String photoType,
                                              @Path("case_id") String case_id);
}


