package com.nyasa.synergyfieldengineer.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dell on 15-01-2018.
 */


public class TabBasicDetails extends Fragment implements View.OnClickListener {




    EditText etEmail;
    Button  btnSave;
    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_basic_details,container,false);

        Log.e("TabHome","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        spUserProfile=new SPUserProfile(getActivity());

      /*  etEmail=(EditText)rootView.findViewById(R.id.edt_email);
        btnSave=(Button)rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

*/
        return rootView;

    }






    @Override
    public void onClick(View v) {

       /* if(etEmail.getText().toString().length()==0)
            showToast("Please enter email");
        else if(!etEmail.getText().toString().contains("@")||!etEmail.getText().toString().contains("."))
            showToast("Please enter valid Email");
        else
            //editEmail();*/
    }


    //display toast
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
