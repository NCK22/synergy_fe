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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nyasa.synergyfieldengineer.APIClient;
import com.nyasa.synergyfieldengineer.Interface.getBankInterface;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupOccupancyInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupRelationWithOccuInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoInstitute;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
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
    TextView tvCaseNo,tvCaseDate,tvBank,tvReportNo,tvVillage,tvDistrict;
    EditText etApplicantName,etPersonAtSite,etContactPersonAtSite,etPropertyNo,etFloorNo,etBuildingNo,etProjectName,
    etSurveyNo,etVillageCity,etDistrict,etPincode;
    String case_id="";
    Spinner spOccu,spRelationWithOccu;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_occu=new ArrayList<String>();
    ArrayList<String> list_relation_occu=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_basic_details,container,false);

        Log.e("TabBasicDetails","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        spUserProfile=new SPUserProfile(getActivity());

        tvCaseNo=(TextView)rootView.findViewById(R.id.tv_case_no);
        tvCaseDate=(TextView)rootView.findViewById(R.id.tv_date);
        tvBank=(TextView)rootView.findViewById(R.id.tv_bank);
        tvReportNo=(TextView)rootView.findViewById(R.id.tv_report_no);
        tvVillage=(TextView)rootView.findViewById(R.id.tv_village);
        tvDistrict=(TextView)rootView.findViewById(R.id.tv_district);

        etApplicantName=(EditText)rootView.findViewById(R.id.et_applicant_name);
        etPersonAtSite=(EditText)rootView.findViewById(R.id.et_name_person_met);
        etContactPersonAtSite=(EditText)rootView.findViewById(R.id.et_contact_person_met);
        etPropertyNo=(EditText)rootView.findViewById(R.id.et_property_no);
        etFloorNo=(EditText)rootView.findViewById(R.id.et_floor_no);
        etBuildingNo=(EditText)rootView.findViewById(R.id.et_building_no);
        etProjectName=(EditText)rootView.findViewById(R.id.et_project_name);
        etSurveyNo=(EditText)rootView.findViewById(R.id.et_survey_no);
        etPincode=(EditText)rootView.findViewById(R.id.et_pincode);

        spOccu=(Spinner)rootView.findViewById(R.id.sp_occu);
        spRelationWithOccu=(Spinner)rootView.findViewById(R.id.sp_relation_with_occu);

        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        Log.e("case_id",case_id);
        getCaseDetails(case_id);
        getOccupancy();
        getRelationWithOccu();

        return rootView;

    }


    public void getCaseDetails(String case_id){

        progressDialog.show();
        getCaseDetailsInterface getResponse = APIClient.getClient().create(getCaseDetailsInterface.class);
        Call<ArrayList<ChildPojoCase>> call = getResponse.doGetListResources(case_id);
        call.enqueue(new Callback<ArrayList<ChildPojoCase>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoCase>> call, Response<ArrayList<ChildPojoCase>> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
              //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                ChildPojoCase childPojoCase=response.body().get(0);
                if (childPojoCase != null) {

                   tvCaseNo.append(childPojoCase.getCaseNo());
                   tvCaseDate.append(childPojoCase.getInwardDate().substring(0,10));
                    getBank(childPojoCase.getInstituteId());
                   etApplicantName.setText(childPojoCase.getClientName());
                   etPropertyNo.setText(childPojoCase.getPropertyNo());
                   etFloorNo.setText(childPojoCase.getFloorNo());
                   etBuildingNo.setText(childPojoCase.getBuildingWing());
                   etProjectName.setText(childPojoCase.getProjectName());
                   etSurveyNo.setText(childPojoCase.getSurveyPlotNo());
                   if(childPojoCase.getVillageCity()!=null) {
                       tvVillage.append(childPojoCase.getVillageCity());
                       tvDistrict.append(childPojoCase.getDistrict());
                   }
                   etPincode.setText(childPojoCase.getPincode());

                }


                Log.e("List size inside",""+mListItem.size());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoCase>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getOccupancy(){


        progressDialog.show();
        lookupOccupancyInterface getResponse = APIClient.getClient().create(lookupOccupancyInterface.class);
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

                      list_occu.add(childPojoStaticLookups.get(i).getLookupItemValue());
                  }
              }

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_occu);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spOccu.setAdapter(aaOccu);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getRelationWithOccu(){

        if(list_relation_occu!=null)
            list_relation_occu.clear();

        progressDialog.show();
        lookupRelationWithOccuInterface getResponse = APIClient.getClient().create(lookupRelationWithOccuInterface.class);
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

                        list_relation_occu.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_relation_occu);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRelationWithOccu.setAdapter(aaOccu);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getBank(String bank_id){

        Log.e("Inside","getBank");
        Log.e("bank_id",bank_id);
        progressDialog.show();
        getBankInterface getResponse = APIClient.getClient().create(getBankInterface.class);
        Call<ArrayList<ChildPojoInstitute>> call = getResponse.doGetListResources(bank_id);
        call.enqueue(new Callback<ArrayList<ChildPojoInstitute>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoInstitute>> call, Response<ArrayList<ChildPojoInstitute>> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                ArrayList<ChildPojoInstitute> childPojoInstitute = response.body();

                if(childPojoInstitute!=null)
               tvBank.append(childPojoInstitute.get(0).getInstituteName());

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_occu);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spOccu.setAdapter(aaOccu);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoInstitute>> call, Throwable t) {

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
