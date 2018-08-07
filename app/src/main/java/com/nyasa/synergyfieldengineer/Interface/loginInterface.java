package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ParentPojoLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface loginInterface {

    @FormUrlEncoded
    @POST("api/userlogin")
    Call<ParentPojoLogin> doGetListResources(@Field("username") String username, @Field("password") String password);


}
