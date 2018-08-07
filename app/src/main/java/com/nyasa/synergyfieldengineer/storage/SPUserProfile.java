package com.nyasa.synergyfieldengineer.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SPUserProfile {

    public static SPUserProfile spInstance;
    public SharedPreferences preferences;
    public String prefName = "profdtl";

    public SPUserProfile(Context context)
    {
        preferences = context.getSharedPreferences(prefName, 0);
        spInstance=this;
    }


    public static synchronized SPUserProfile getSpInstance() {
        return spInstance;
    }


    public String getIsLogin()
    {
        return preferences.getString("is_login","");
    }

    public String getProfile_id() {

        return preferences.getString("profile_id", "");
    }
    public String getUser_id() {

        return preferences.getString("user_id", "");
    }



    public String getMobile() {
        //preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("mobile", "");
    }

    public String getEmail() {
       // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("email", "");
    }





    public String getName() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("name", "");
    }



    public void setName(String name) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.apply();
    }


    public void setProfile_id(String profile_id) {
       // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profile_id", profile_id);
        editor.apply();
    }

    public void setUser_id(String user_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", user_id);
        editor.apply();
    }


    public void setMobile(String mobile) {
       // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }

    public void setEmail(String email) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public void setMatrimonyId(String matrimony_id) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("matrimony_id", matrimony_id);
        editor.apply();
    }

    public void setIsLogin(String is_login) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("is_login", is_login);
        editor.apply();
    }
}
