package com.nyasa.synergyfieldengineer.Interface;


import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface uploadPhotoInterface {


    @Multipart
    @POST("synergyapi/api/uploadFile")

    Call<HashMap<String,String>> uploadFile(@Part MultipartBody.Part file, @Part("example") RequestBody name, @Part("profile_id") RequestBody profile_id);

   /* Call<CommonParentPojo> doGetListResources(
            @Field("email_id") String email_id,
            @Field("matrimony_id") String matrimony_id,
            @Field("phone") String phone
    );
*/

}
