package com.nyasa.synergyfieldengineer.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nyasa.synergyfieldengineer.APIClient;
import com.nyasa.synergyfieldengineer.Activity.TabParentCaseDetailActivity;
import com.nyasa.synergyfieldengineer.Interface.WorkTabAddInterface;
import com.nyasa.synergyfieldengineer.Interface.WorkTabGetInterface;
import com.nyasa.synergyfieldengineer.Interface.addBasicDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.addPropertyOccuInterface;
import com.nyasa.synergyfieldengineer.Interface.getBankInterface;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.getPOccuInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupOccupancyInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupRelationWithOccuInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoInstitute;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dell on 15-01-2018.
 */


public class TabLocationDetails extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {




    Button  btnSubmit;
    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;
    TextView tvCaseNo,tvCaseDate,tvBank,tvReportNo,tvVillage,tvDistrict;
    EditText etLat,etLong;
    String case_id="",insti_id="",pOccu="",pRel="";
    Boolean gpsExist=false,flagAllValid=false;
    MaterialSpinner spOccu,spRelationWithOccu;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_occu=new ArrayList<String>();
    ArrayList<String> list_relation_occu=new ArrayList<String>();
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    public static  boolean permissionresult=false;
    private static final long INTERVAL = 1000 * 5;
    private static final long FASTEST_INTERVAL = 1000 * 2;
    private static final String TAG = "LocationActivity";
    private FusedLocationProviderClient mFusedLocationClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_location_details,container,false);

        Log.e("TabBasicDetails","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        spUserProfile=new SPUserProfile(getActivity());



        etLat=(EditText)rootView.findViewById(R.id.et_lat);
        etLong=(EditText)rootView.findViewById(R.id.et_long);

        btnSubmit=(Button)rootView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        Log.e("case_id",case_id);
        getGps(case_id);

        createLocationRequest();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        else
            {
                mFusedLocationClient.getLastLocation().addOnSuccessListener((Executor) mGoogleApiClient, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {

                        // Toast.makeText(FusedActivity.this, "Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(FusedActivity.this, "Lattitude:" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                        // Logic to handle location object
                    }
                }
            });

        }

        return rootView;

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onResume() {
        super.onResume();

        if(this.mGoogleApiClient!=null) {
            if (this.mGoogleApiClient.isConnected())
                this.mGoogleApiClient.disconnect();
        }
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.mGoogleApiClient.connect();
        Log.e("After googleapiclient", String.valueOf(mGoogleApiClient.isConnected()));


    }

    public void getGps(final String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getGps(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    gpsExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        etLat.setText(childPojoCase.get("GPSLatitude"));
                        etLong.setText(childPojoCase.get("GPSLongitude"));


                    }
                }
                else
                    gpsExist=false;

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addGpsDetails(final String case_id){


        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(gpsExist==true) {
            call = getResponse.updateGPS(Double.parseDouble(etLat.getText().toString()),Double.parseDouble(etLong.getText().toString()),case_id);
        }
        else{
            call = getResponse.addGPS(Double.parseDouble(etLat.getText().toString()),Double.parseDouble(etLong.getText().toString()),case_id);

        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {





                //    progressDialog.dismiss();
                //  addCaseValDetails(case_id);

            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

     checkValidity();
       // addBasicDetails(case_id);
    }

    public void checkValidity(){

        if(
                etLat.getText().toString().equalsIgnoreCase("") ||
                etLong.getText().toString().equalsIgnoreCase(""))

        showToast("Please fill all mandatory fields");
        else
            addGpsDetails(case_id);
    }
    //display toast
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e("onConnected-isConnected", String.valueOf(mGoogleApiClient.isConnected()));

            startLocationUpdates();
       /* else
            Log.e("Location update stopped","logged out");*/
    }

    private void startLocationUpdates() {

        Log.e("inside","startlocationupdate");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("permission","not granted");
            permissionresult=false;
            return;
        }
        if(mGoogleApiClient.isConnected()) {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,mLocationRequest, getActivity());
            Log.d(TAG, "Location update started ..............: ");
        }


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
