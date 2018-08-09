package com.nyasa.synergyfieldengineer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;


public class SplashActivity extends AppCompatActivity {

    SPUserProfile spUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spUserProfile=new SPUserProfile(this);
        Thread splash= new Thread(){
            public void run(){
                try{
                    Log.e("Thread","started");
                    sleep(3000);

                    if(spUserProfile.getIsLogin().equalsIgnoreCase("true"))
                            startActivity(new Intent(SplashActivity.this,VisitListActivity.class));
                                    else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }

        };
        splash.start();


    }
}
