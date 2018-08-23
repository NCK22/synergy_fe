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
import com.nyasa.synergyfieldengineer.Activity.TabParentCaseDetailActivity;
import com.nyasa.synergyfieldengineer.Interface.PropertyTabAddInterface;
import com.nyasa.synergyfieldengineer.Interface.PropertyTabGetInterface;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupConstructionInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupLandStatusInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupLandUseInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupOccupancyInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupRelationWithOccuInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
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
    etBathRooms,etWc,etCmnToilet,etAttachToilet,etAttachBalcony,etDryBalcony,etAttachTerrace,etParking,etGarden,etOther1Name,etOther1No,
    etOther2Name,etOther2No,etOther3Name,etOther3No,etEast,etWest,etNorth,etSouth,etAgeOfProperty;
    String case_id="",client_name="";
    MaterialSpinner spConstruction,spLandUseActual,spLandStatus;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_constru=new ArrayList<String>();
    ArrayList<String> list_land_use=new ArrayList<String>();
    ArrayList<String> list_land_status=new ArrayList<String>();
    Boolean buildingExist=false,boundaryExist=false,surroundingExist=false,floorExist=false,gpsExist=false;

    Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_property_details,container,false);

        Log.e("TabBasicDetails","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
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
        etAttachBalcony=(EditText)rootView.findViewById(R.id.et_attach_balcony);
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

        spConstruction=(MaterialSpinner) rootView.findViewById(R.id.sp_construction);
        spLandUseActual=(MaterialSpinner) rootView.findViewById(R.id.sp_land_use_actual);
        spLandStatus=(MaterialSpinner) rootView.findViewById(R.id.sp_land_status);

        btnSubmit=(Button)rootView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        client_name=bundle.getString("client_name");
        Log.e("case_id",case_id);
        Log.e("client_name",client_name);

        getConstruction();
        getLandUse();
        getLandStatus();


        return rootView;

    }


    public void getBuildingDetails(String case_id){

        progressDialog.show();
        PropertyTabGetInterface getResponse = APIClient.getClient().create(PropertyTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getBuilding(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    buildingExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        etBeforeFloor.setText(childPojoCase.get("BeforeFloorDetails"));
                        etNoOfFloors.setText(childPojoCase.get("PresentNoOfFloors"));
                        etProposedNoOfFloors.setText(childPojoCase.get("ProposedNoOfFloors"));

                    }
                }
                else
                    buildingExist=false;

                //    progressDialog.dismiss();
             getBoundaryDetails();
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getBoundaryDetails(){

     //   progressDialog.show();
        PropertyTabGetInterface getResponse = APIClient.getClient().create(PropertyTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getBoundaries(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    boundaryExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        etEast.setText(childPojoCase.get("EastActual"));
                        etWest.setText(childPojoCase.get("WestActual"));
                        etSouth.setText(childPojoCase.get("SouthActual"));
                        etNorth.setText(childPojoCase.get("NorthActual"));
                        spLandUseActual.setSelection(list_land_use.indexOf(childPojoCase.get("LandUseActual")));
                        spLandStatus.setSelection(list_land_status.indexOf(childPojoCase.get("LandStatus")));

                    }
                }
                else
        boundaryExist=false;

                //    progressDialog.dismiss();
                getSurroundingDetails();
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getSurroundingDetails(){

      //  progressDialog.show();
        PropertyTabGetInterface getResponse = APIClient.getClient().create(PropertyTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getBoundaries(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    surroundingExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        etAgeOfProperty.setText(childPojoCase.get("AgeOfPropertyYears"));
                        spConstruction.setSelection(list_constru.indexOf(childPojoCase.get("ConstructionAsPerPlan")));

                    }
                }
                else
                    surroundingExist=false;

                //    progressDialog.dismiss();
                getGpsDetails();
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getGpsDetails(){

        //   progressDialog.show();
        PropertyTabGetInterface getResponse = APIClient.getClient().create(PropertyTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getBoundaries(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    gpsExist=true;
                HashMap<String, String> childPojoCase=response.body().get(0);
                if (childPojoCase != null) {

                    etLandmark.setText(childPojoCase.get("Landmark"));

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

    public void addBuildingDetails(final String case_id){

   /*     Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());*/

        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        PropertyTabAddInterface getResponse = APIClient.getClient().create(PropertyTabAddInterface.class);
        Call<CommonPojo> call;
        if(buildingExist==true) {
            call = getResponse.updateBuilding(client_name, etBeforeFloor.getText().toString()
                    , etNoOfFloors.getText().toString(), etProposedNoOfFloors.getText().toString(), case_id);
        }
        else{
            call = getResponse.addBuilding(client_name, etBeforeFloor.getText().toString()
                    , etNoOfFloors.getText().toString(), etProposedNoOfFloors.getText().toString(), case_id);
        }

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                    //  Toast.makeText(getActivity(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                }

              //    progressDialog.dismiss();
                addBoundaryDetails(case_id);

            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void addBoundaryDetails(final String case_id){

   //        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        PropertyTabAddInterface getResponse = APIClient.getClient().create(PropertyTabAddInterface.class);
        Call<CommonPojo> call;
        if(boundaryExist==true) {
            call = getResponse.updateBoundary(etEast.getText().toString(), etSouth.getText().toString()
                    , etWest.getText().toString(), etNorth.getText().toString(), spLandUseActual.getSelectedItem().toString(),
                    spLandStatus.getSelectedItem().toString(),case_id);
        }
        else{
            call = getResponse.addBoundary(etEast.getText().toString(), etSouth.getText().toString()
                    , etWest.getText().toString(), etNorth.getText().toString(), spLandUseActual.getSelectedItem().toString(),
                    spLandStatus.getSelectedItem().toString(),case_id);
        }

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                    //  Toast.makeText(getActivity(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                }

              //  progressDialog.dismiss();
                addSurroundingDetails(case_id);

            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addSurroundingDetails(final String case_id){

        //        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        PropertyTabAddInterface getResponse = APIClient.getClient().create(PropertyTabAddInterface.class);
        Call<CommonPojo> call;
        if(surroundingExist==true) {
            call = getResponse.updateSurrounding(spConstruction.getSelectedItem().toString(),etAgeOfProperty.getText().toString(), case_id);
        }
        else{
            call = getResponse.addSurrounding(spConstruction.getSelectedItem().toString(),etAgeOfProperty.getText().toString(), case_id);
        }

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                    //  Toast.makeText(getActivity(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                }

              //  progressDialog.dismiss();
                //addGpsDetails(case_id);

            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addFloorDetails(final String case_id){

        //        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        PropertyTabAddInterface getResponse = APIClient.getClient().create(PropertyTabAddInterface.class);
        Call<CommonPojo> call;
        if(floorExist==true) {
            call = getResponse.updateFloor("","",etTotRooms.getText().toString(),etLivingRooms.getText().toString(),
                    etKitchen.getText().toString(),etBedRooms.getText().toString(),etBathRooms.getText().toString(),
                    etWc.getText().toString(),etCmnToilet.getText().toString(),etAttachToilet.getText().toString(),
                    etAttachTerrace.getText().toString(),etAttachBalcony.getText().toString(),etDryBalcony.getText().toString(),etParking.getText().toString(),
                    etGarden.getText().toString(),etOther1Name.getText().toString(),etOther1No.getText().toString(),
                    etOther2Name.getText().toString(),etOther2No.getText().toString(),etOther3Name.getText().toString(),
                    etOther3No.getText().toString(),case_id);
        }
        else{
            call = getResponse.addSurrounding(spConstruction.getSelectedItem().toString(),etAgeOfProperty.getText().toString(), case_id);
        }

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                    //  Toast.makeText(getActivity(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addGpsDetails(final String case_id){

        //        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        PropertyTabAddInterface getResponse = APIClient.getClient().create(PropertyTabAddInterface.class);
        Call<CommonPojo> call;
        if(gpsExist==true) {
            call = getResponse.updateGPS(etLandmark.getText().toString(),case_id);
        }
        else{
            call = getResponse.addGPS(etLandmark.getText().toString(),case_id);        }

        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                    //  Toast.makeText(getActivity(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
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
                getBuildingDetails(case_id);
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

        addBuildingDetails(case_id);
    }


    //display toast
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
