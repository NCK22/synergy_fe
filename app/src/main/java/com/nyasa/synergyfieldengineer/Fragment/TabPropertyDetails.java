package com.nyasa.synergyfieldengineer.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nyasa.synergyfieldengineer.APIClient;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupConstructionInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupLandStatusInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupLandUseInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupOccupancyInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupRelationWithOccuInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dell on 15-01-2018.
 */


public class TabPropertyDetails extends Fragment implements View.OnClickListener {


    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;

    EditText etBeforeFloor,etNoOfFloors,etProposedNoOfFloors,etLandmark,etTotRooms,etLivingRooms,etKitchen,etBedRooms,
    etBathRooms,etWc,etCmnToilet,etAttachToilet,etDryBalcony,etAttachTerrace,etParking,etGarden,etOther1Name,etOther1No,
    etOther2Name,etOther2No,etOther3Name,etOther3No,etEast,etWest,etNorth,etSouth,etAgeOfProperty;
    String case_id="";
    Spinner spConstruction,spLandUseActual,spLandStatus;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_constru=new ArrayList<String>();
    ArrayList<String> list_land_use=new ArrayList<String>();
    ArrayList<String> list_land_status=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_property_details,container,false);

        Log.e("TabBasicDetails","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        spUserProfile=new SPUserProfile(getActivity());

        etBeforeFloor=(EditText)rootView.findViewById(R.id.et_before_floor);
        etNoOfFloors=(EditText)rootView.findViewById(R.id.et_noOfFloors);
        etProposedNoOfFloors=(EditText)rootView.findViewById(R.id.et_proposed_noOfFloors);
        etLandmark=(EditText)rootView.findViewById(R.id.et_landmark);
        etTotRooms=(EditText)rootView.findViewById(R.id.et_total_rooms);
        etLivingRooms=(EditText)rootView.findViewById(R.id.et_living_rooms);
        etKitchen=(EditText)rootView.findViewById(R.id.et_kitchen);
        etBedRooms=(EditText)rootView.findViewById(R.id.et_bedrooms);
        etBathRooms=(EditText)rootView.findViewById(R.id.et_bathrooms);
        etWc=(EditText)rootView.findViewById(R.id.et_wc);
        etCmnToilet=(EditText)rootView.findViewById(R.id.et_common_toilet);
        etAttachToilet=(EditText)rootView.findViewById(R.id.et_attached_toilet);
        etDryBalcony=(EditText)rootView.findViewById(R.id.et_dry_balcony);
        etAttachTerrace=(EditText)rootView.findViewById(R.id.et_attached_terrace);
        etParking=(EditText)rootView.findViewById(R.id.et_parking);
        etGarden=(EditText)rootView.findViewById(R.id.et_garden);
        etOther1Name=(EditText)rootView.findViewById(R.id.et_other1_name);
        etOther1No=(EditText)rootView.findViewById(R.id.et_other1_no);
        etOther2Name=(EditText)rootView.findViewById(R.id.et_other2_name);
        etOther2No=(EditText)rootView.findViewById(R.id.et_other2_no);
        etOther3Name=(EditText)rootView.findViewById(R.id.et_other3_name);
        etOther3No=(EditText)rootView.findViewById(R.id.et_other3_no);
        etEast=(EditText)rootView.findViewById(R.id.et_boundary_east);
        etWest=(EditText)rootView.findViewById(R.id.et_boundary_west);
        etNorth=(EditText)rootView.findViewById(R.id.et_boundary_north);
        etSouth=(EditText)rootView.findViewById(R.id.et_boundary_south);
        etAgeOfProperty=(EditText)rootView.findViewById(R.id.et_age_of_propert);

        spConstruction=(Spinner)rootView.findViewById(R.id.sp_construction);
        spLandUseActual=(Spinner)rootView.findViewById(R.id.sp_land_use_actual);
        spLandStatus=(Spinner)rootView.findViewById(R.id.sp_land_status);

        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        Log.e("case_id",case_id);

        getConstruction();
        getLandUse();
        getLandStatus();

        return rootView;

    }




    public void getConstruction(){

    if(list_constru!=null)
    list_constru.clear();

        progressDialog.show();
        lookupConstructionInterface getResponse = APIClient.getClient().create(lookupConstructionInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.doGetListResources();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                 ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

              if(childPojoStaticLookups!=null){
                  for(int i=1;i<childPojoStaticLookups.size();i++){

                      list_constru.add(childPojoStaticLookups.get(i).getLookupItemValue());
                  }
              }

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_constru);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spConstruction.setAdapter(aaOccu);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getLandUse(){

        if(list_land_use!=null)
            list_land_use.clear();

        progressDialog.show();
        lookupLandUseInterface getResponse = APIClient.getClient().create(lookupLandUseInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.doGetListResources();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_land_use.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_land_use);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLandUseActual.setAdapter(aaOccu);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getLandStatus(){

        if(list_land_status!=null)
            list_land_status.clear();

        progressDialog.show();
        lookupLandStatusInterface getResponse = APIClient.getClient().create(lookupLandStatusInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.doGetListResources();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_land_status.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_land_status);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLandStatus.setAdapter(aaOccu);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
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
