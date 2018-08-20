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
import com.nyasa.synergyfieldengineer.Interface.getBankInterface;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupOccupancyInterface;
import com.nyasa.synergyfieldengineer.Interface.lookupRelationWithOccuInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoInstitute;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoStaticLookup;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dell on 15-01-2018.
 */


public class TabWorkStatus extends Fragment implements View.OnClickListener {





    Button  btnSave;
    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;
    TextView tvCaseNo,tvCaseDate,tvBank,tvReportNo,tvVillage,tvDistrict;
    EditText etApplicantName,etPersonAtSite,etContactPersonAtSite,etPropertyNo,etFloorNo,etBuildingNo,etProjectName,
    etSurveyNo,etVillageCity,etDistrict,etPincode;
    String case_id="";
    MaterialSpinner spFoundationWork,spFoundation,spWalls,spDoors,spFlooring,spIntFinish,spExtFinish,spElectric,spPlumbing,spKitchen,spParking;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_found_work=new ArrayList<String>();
    ArrayList<String> list_found=new ArrayList<String>();
    ArrayList<String> list_walls=new ArrayList<String>();
    ArrayList<String> list_doors=new ArrayList<String>();
    ArrayList<String> list_flooring=new ArrayList<String>();
    ArrayList<String> list_int_finish=new ArrayList<String>();
    ArrayList<String> list_ext_finish=new ArrayList<String>();
    ArrayList<String> list_electric=new ArrayList<String>();
    ArrayList<String> list_plumbing=new ArrayList<String>();
    ArrayList<String> list_kitchen=new ArrayList<String>();
    ArrayList<String> list_parking=new ArrayList<String>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.tab_work_status,container,false);

        Log.e("TabBasicDetails","onCreateView");
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        spUserProfile=new SPUserProfile(getActivity());

        /*tvCaseNo=(TextView)rootView.findViewById(R.id.tv_case_no);
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
*/
        spFoundationWork=(MaterialSpinner) rootView.findViewById(R.id.sp_foundation_work);
        spFoundation=(MaterialSpinner) rootView.findViewById(R.id.sp_foundation);
        spWalls=(MaterialSpinner) rootView.findViewById(R.id.sp_walls);
        spDoors=(MaterialSpinner) rootView.findViewById(R.id.sp_doors);
        spFlooring=(MaterialSpinner) rootView.findViewById(R.id.sp_flooring);
        spIntFinish=(MaterialSpinner) rootView.findViewById(R.id.sp_internal_finish);
        spExtFinish=(MaterialSpinner) rootView.findViewById(R.id.sp_external_finish);
        spElectric=(MaterialSpinner) rootView.findViewById(R.id.sp_electric);
        spPlumbing=(MaterialSpinner) rootView.findViewById(R.id.sp_plumbing);
        spKitchen=(MaterialSpinner) rootView.findViewById(R.id.sp_kitchen);
        spParking=(MaterialSpinner) rootView.findViewById(R.id.sp_parking);


        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        Log.e("case_id",case_id);
        //getCaseDetails(case_id);
        getFoundationWork();


        return rootView;

    }


    public void getFoundationWork(){

        progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getFoundation();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_found_work.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("List size inside",""+list_found_work.size());
                if(list_found_work!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_found_work);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spFoundationWork.setAdapter(aa);
                }
                //   progressDialog.dismiss();
                getFoundation();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getFoundation(){

       // progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getSpecFoundation();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                 ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

              if(childPojoStaticLookups!=null){
                  for(int i=1;i<childPojoStaticLookups.size();i++){

                      list_found.add(childPojoStaticLookups.get(i).getLookupItemValue());
                  }
              }

                Log.e("List size inside",""+mListItem.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_found);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spFoundation.setAdapter(aa);
             //   progressDialog.dismiss();
                getWalls();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getWalls(){

       // progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getWalls();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_walls.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_walls size",""+list_walls.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_walls);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spWalls.setAdapter(aa);
          //      progressDialog.dismiss();
                getDoors();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getDoors(){

      //  progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getDoors();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_doors.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_doors size",""+list_doors.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_doors);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDoors.setAdapter(aa);
            //    progressDialog.dismiss();
                getFlooring();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getFlooring(){

        progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getFlooring();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_flooring.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_flooring size",""+list_flooring.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_flooring);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spFlooring.setAdapter(aa);
                progressDialog.dismiss();
                getIntFinish();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getIntFinish(){

      //  progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getInternalFinish();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_int_finish.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_int_finish size",""+list_int_finish.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_int_finish);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spIntFinish.setAdapter(aa);
               // progressDialog.dismiss();
                getExtFinish();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getExtFinish(){

       // progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getExternalFinish();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_ext_finish.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_ext_finish size",""+list_ext_finish.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_ext_finish);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spExtFinish.setAdapter(aa);
             //   progressDialog.dismiss();
                getElectric();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }


    public void getElectric(){

      //  progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getElectric();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_electric.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_electric size",""+list_electric.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_electric);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spElectric.setAdapter(aa);
             //   progressDialog.dismiss();
                getPlumbing();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getPlumbing(){

     //   progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getPlumbing();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_plumbing.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_plumbing size",""+list_plumbing.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_plumbing);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPlumbing.setAdapter(aa);
              //  progressDialog.dismiss();
                getKitchen();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getKitchen(){

       // progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getKitchen();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_kitchen.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_kitchen size",""+list_kitchen.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_kitchen);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spKitchen.setAdapter(aa);
               // progressDialog.dismiss();
                getParking();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }


    public void getParking(){

      //  progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getParkingFloor();
        call.enqueue(new Callback<ArrayList<ChildPojoStaticLookup>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoStaticLookup>> call, Response<ArrayList<ChildPojoStaticLookup>> response) {

                Log.e("Inside", "onResponse");

                /*Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());
*/
                ArrayList<ChildPojoStaticLookup> childPojoStaticLookups = response.body();

                if(childPojoStaticLookups!=null){
                    for(int i=1;i<childPojoStaticLookups.size();i++){

                        list_parking.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_parking size",""+list_parking.size());

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list_parking);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spParking.setAdapter(aa);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

/*

    public void getCaseDetails(String case_id){

        progressDialog.show();
        getCaseDetailsInterface getResponse = APIClient.getClient().create(getCaseDetailsInterface.class);
        Call<ArrayList<ChildPojoCase>> call = getResponse.doGetListResources(case_id);
        call.enqueue(new Callback<ArrayList<ChildPojoCase>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildPojoCase>> call, Response<ArrayList<ChildPojoCase>> response) {

                Log.e("Inside", "onResponse");
               */
/* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*//*

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
               */
/* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*//*

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
               */
/* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*//*

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
*/




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
