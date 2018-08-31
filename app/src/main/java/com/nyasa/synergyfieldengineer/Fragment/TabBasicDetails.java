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
import com.nyasa.synergyfieldengineer.Activity.TabParentCaseDetailActivity;
import com.nyasa.synergyfieldengineer.Interface.addBasicDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.addPropertyOccuInterface;
import com.nyasa.synergyfieldengineer.Interface.assignedCaseInterface;
import com.nyasa.synergyfieldengineer.Interface.getBankInterface;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.getPOccuInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupOccupancyInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupRelationWithOccuInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoInstitute;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
import com.nyasa.synergyfieldengineer.Pojo.CommonPojo;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dell on 15-01-2018.
 */


public class TabBasicDetails extends Fragment implements View.OnClickListener {




    EditText etEmail;
    Button  btnSubmit;
    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;
    TextView tvCaseNo,tvCaseDate,tvBank,tvReportNo,tvVillage,tvDistrict;
    EditText etApplicantName,etPersonAtSite,etContactPersonAtSite,etPropertyNo,etFloorNo,etBuildingNo,etProjectName,
    etSurveyNo,etVillageCity,etDistrict,etPincode;
    String case_id="",insti_id="",pOccu="",pRel="";
    Boolean occuIsExist=false,flagAllValid=false;
    MaterialSpinner spOccu,spRelationWithOccu;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_occu=new ArrayList<String>();
    ArrayList<String> list_relation_occu=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_basic_details,container,false);

        Log.e("TabBasicDetails","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
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

        spOccu=(MaterialSpinner) rootView.findViewById(R.id.sp_occu);
        spRelationWithOccu=(MaterialSpinner) rootView.findViewById(R.id.sp_relation_with_occu);

        btnSubmit=(Button)rootView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        Log.e("case_id",case_id);
        getCaseDetails(case_id);

       // getRelationWithOccu();

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
     //   getPOccupancy();
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
                  //  getBank(childPojoCase.getInstituteId());
                    insti_id=childPojoCase.getInstituteId();
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

            //    progressDialog.dismiss();
                getPOccupancy();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoCase>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getPOccupancy(){


      //  progressDialog.show();
        getPOccuInterface getResponse = APIClient.getClient().create(getPOccuInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.doGetListResources(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                ArrayList<HashMap<String,String>> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    occuIsExist=true;
                   pOccu=childPojoStaticLookups.get(0).get("OccupancyStatus");
                   pRel=childPojoStaticLookups.get(0).get("RelationWithOccupant");
                   Log.e("pOccu",pOccu);
                    Log.e("pRel",pRel);
                }


               // progressDialog.dismiss();
               getOccupancy();
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getOccupancy(){

        if(list_occu!=null)
            list_occu.clear();

       // progressDialog.show();
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

                Log.e("occu List size inside",""+list_occu.size());
                if(list_occu!=null) {
                    ArrayAdapter aaOccu = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_occu);
                    aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spOccu.setAdapter(aaOccu);
                }
                if(!pOccu.equalsIgnoreCase(""))
                    spOccu.setSelection(list_occu.indexOf(pOccu)+1);
                Log.e("index",""+list_occu.indexOf(pOccu));
               // progressDialog.dismiss();
                getRelationWithOccu();
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

    //    progressDialog.show();
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

                Log.e("relationList size",""+mListItem.size());

                ArrayAdapter aaOccu = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_relation_occu);
                aaOccu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRelationWithOccu.setAdapter(aaOccu);
                if(!pRel.equalsIgnoreCase(""))
                    spRelationWithOccu.setSelection(list_relation_occu.indexOf(pRel)+1);
               // progressDialog.dismiss();
                getBank(insti_id);
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
      //  progressDialog.show();
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


                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoInstitute>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addBasicDetails(final String case_id){

   /*     Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());*/

        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        addBasicDetailsInterface getResponse = APIClient.getClient().create(addBasicDetailsInterface.class);
        Call<CommonPojo> call = getResponse.doGetListResources(etApplicantName.getText().toString(),etPropertyNo.getText().toString()
                ,etFloorNo.getText().toString(),etProjectName.getText().toString(),etBuildingNo.getText().toString(),
                etSurveyNo.getText().toString(),etPincode.getText().toString(),case_id);
        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                  //  Toast.makeText(getActivity(),response.body().getMessage() , Toast.LENGTH_SHORT).show();
                    TabParentCaseDetailActivity.client_name=etApplicantName.getText().toString();

                    }

              //  progressDialog.dismiss();
                addOccupancyDetails(case_id);
                }

            @Override
            public void onFailure(Call<CommonPojo> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addOccupancyDetails(final String case_id){

   /*     Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());*/

      //  progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        addPropertyOccuInterface getResponse = APIClient.getClient().create(addPropertyOccuInterface.class);
        Call<CommonPojo> call;
        if(occuIsExist==true)
          call = getResponse.updateOccupancy(spOccu.getSelectedItem().toString(),spRelationWithOccu.getSelectedItem().toString(),case_id);
        else
       call = getResponse.insertOccupancy(spOccu.getSelectedItem().toString(),spRelationWithOccu.getSelectedItem().toString(),case_id);
        call.enqueue(new Callback<CommonPojo>() {
            @Override
            public void onResponse(Call<CommonPojo> call, Response<CommonPojo> response) {

                Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/


                if (response != null) {

                    Toast.makeText(getActivity(),"Updated Successfully" , Toast.LENGTH_SHORT).show();
                    getCaseDetails(case_id);
                    TabParentCaseDetailActivity.bCompleted=true;
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


    @Override
    public void onClick(View v) {

     checkValidity();
       // addBasicDetails(case_id);
    }


    public void checkValidity(){

        if(
                spOccu.getSelectedItemPosition()<=0 || spRelationWithOccu.getSelectedItemPosition()<=0)

        showToast("Please fill all mandatory fields");
        else
            addBasicDetails(case_id);
    }
    //display toast
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
