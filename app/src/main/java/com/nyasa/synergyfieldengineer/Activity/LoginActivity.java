package com.nyasa.synergyfieldengineer.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nyasa.synergyfieldengineer.APIClient;
import com.nyasa.synergyfieldengineer.Interface.loginInterface;
import com.nyasa.synergyfieldengineer.Pojo.ParentPojoLogin;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etUname,etPwd;
    ProgressDialog progressDialog;
    Boolean flagAllValid=false;
    SPUserProfile spUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        spUserProfile=new SPUserProfile(this);

        etUname=(EditText)findViewById(R.id.edt_email);
        etPwd=(EditText)findViewById(R.id.edt_pwd);
        btnLogin=(Button)findViewById(R.id.btn_submit);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
              //  startActivity(new Intent(LoginActivity.this, VisitListActivity.class));

            }
        });
    }

    public void login(){


        checkValidity();

        if(flagAllValid==true) {
            progressDialog.show();
            loginInterface getResponse = APIClient.getClient().create(loginInterface.class);
            Call<ParentPojoLogin> call = getResponse.doGetListResources(etUname.getText().toString(),etPwd.getText().toString());
            call.enqueue(new Callback<ParentPojoLogin>() {
                @Override
                public void onResponse(Call<ParentPojoLogin> call, Response<ParentPojoLogin> response) {

                    Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                    ParentPojoLogin parentPojoLogin = response.body();
                    if (parentPojoLogin != null) {
                        if (parentPojoLogin.getMessage().contains("Success")) {
                            if (parentPojoLogin.getUserdata().size() != 0) {
                                Log.e("Response", parentPojoLogin.getMessage());

                                spUserProfile.setIsLogin("true");
                                spUserProfile.setUser_id(parentPojoLogin.getUserdata().get("UserId"));

                                startActivity(new Intent(LoginActivity.this, VisitListActivity.class));
                                finish();
                            }
                        }
                            showToast(parentPojoLogin.getMessage());
                        }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ParentPojoLogin> call, Throwable t) {

                    Log.e("Throwable ", "" + t);
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void checkValidity()
    {
        progressDialog.show();
        if(etUname.getText().toString().equals("")|| etPwd.getText().toString().equals("")){
            showToast("Please fill all fields");
        }
        else if(etPwd.getText().toString().length()<4)
            showToast("Enter valid password");
        else
            flagAllValid=true;
        progressDialog.dismiss();
    }

    public void showToast(String msg)
    {
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}
