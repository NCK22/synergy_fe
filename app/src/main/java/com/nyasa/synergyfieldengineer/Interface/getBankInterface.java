package com.nyasa.synergyfieldengineer.Interface;

import com.nyasa.synergyfieldengineer.Pojo.ChildPojoInstitute;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface getBankInterface {

    @GET("synergyapi/api/getInstituteDetails/{bank_id}")
    Call<ArrayList<ChildPojoInstitute>> doGetListResources(@Path("bank_id") String bank_id);



}
