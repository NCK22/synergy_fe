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
import com.nyasa.synergyfieldengineer.Interface.PropertyTabGetInterface;
import com.nyasa.synergyfieldengineer.Interface.WorkTabAddInterface;
import com.nyasa.synergyfieldengineer.Interface.WorkTabGetInterface;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nyasa.synergyfieldengineer.Activity.TabParentCaseDetailActivity.client_name;


/**
 * Created by Dell on 15-01-2018.
 */


public class TabWorkStatus extends Fragment implements View.OnClickListener {





    Button  btnSubmit;
    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;
    TextView tvCaseNo,tvCaseDate,tvBank,tvReportNo,tvVillage,tvDistrict;
    EditText etSlabs,etLifts,etAminities,etLat,etLong,etRateFrom,etRateTo,etRemarks,etDeviation,
    etSurveyNo,etVillageCity,etDistrict,etPincode;
    DatePickerEditText dtFrom,dtTo;
    String case_id="",building_id="";
    Boolean wordExist=false,specExist=false,costExist=false,violExist=false,valExist=false,dtExist=false,gpsExist=false;
    MaterialSpinner spFoundationWork,spPlinth,spRcc,spBrick,spIntPlaster,spExtPlaster,spFlooringWork,spOtherWork,spRoofing,
            spFoundation,spWalls,spDoors,spWindows,spFlooring,spIntFinish,spExtFinish,spElectric,spPlumbing,spKitchen,spParking,spSideMargin;

    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ArrayList<String> list_found_work=new ArrayList<String>();
    ArrayList<String> list_found=new ArrayList<String>();
    ArrayList<String> list_walls=new ArrayList<String>();
    ArrayList<String> list_roof=new ArrayList<String>();
    ArrayList<String> list_doors=new ArrayList<String>();
    ArrayList<String> list_windows=new ArrayList<String>();
     ArrayList<String> list_flooring=new ArrayList<String>();
    ArrayList<String> list_int_finish=new ArrayList<String>();
    ArrayList<String> list_ext_finish=new ArrayList<String>();
    ArrayList<String> list_electric=new ArrayList<String>();
    ArrayList<String> list_plumbing=new ArrayList<String>();
    ArrayList<String> list_kitchen=new ArrayList<String>();
    ArrayList<String> list_parking=new ArrayList<String>();
    ArrayList<String> list_side_margin=new ArrayList<String>();
    ArrayList<String> list_yesNo=new ArrayList<String>();



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

        dtFrom = (DatePickerEditText) rootView.findViewById(R.id.et_dt_commensement);
        dtFrom.setManager(getActivity().getSupportFragmentManager());
        dtTo = (DatePickerEditText) rootView.findViewById(R.id.et_dt_completion);
        dtTo.setManager(getActivity().getSupportFragmentManager());

        etSlabs=(EditText)rootView.findViewById(R.id.et_slabs);
        etLifts=(EditText)rootView.findViewById(R.id.et_lifts);
        etAminities=(EditText)rootView.findViewById(R.id.et_aminities);
        etLat=(EditText)rootView.findViewById(R.id.et_lat);
        etLong=(EditText)rootView.findViewById(R.id.et_long);
        etRateFrom=(EditText)rootView.findViewById(R.id.et_rate_from);
        etRateTo=(EditText)rootView.findViewById(R.id.et_rate_to);
        etRemarks=(EditText)rootView.findViewById(R.id.et_remarks);
        etDeviation=(EditText)rootView.findViewById(R.id.et_deviation);

        spFoundationWork=(MaterialSpinner) rootView.findViewById(R.id.sp_foundation_work);
        spPlinth=(MaterialSpinner)rootView.findViewById(R.id.sp_plinth);
        spRcc=(MaterialSpinner)rootView.findViewById(R.id.sp_rcc_framework);
        spBrick=(MaterialSpinner)rootView.findViewById(R.id.sp_brick);
        spIntPlaster=(MaterialSpinner) rootView.findViewById(R.id.sp_internal_plaster);
        spExtPlaster=(MaterialSpinner)rootView.findViewById(R.id.sp_external_plaster);
        spFlooringWork=(MaterialSpinner)rootView.findViewById(R.id.sp_flooring_work);
        spOtherWork=(MaterialSpinner) rootView.findViewById(R.id.sp_other_work);

        spFoundation=(MaterialSpinner) rootView.findViewById(R.id.sp_foundation);
        spWalls=(MaterialSpinner) rootView.findViewById(R.id.sp_walls);
        spDoors=(MaterialSpinner) rootView.findViewById(R.id.sp_doors);
        spWindows=(MaterialSpinner) rootView.findViewById(R.id.sp_windows);
        spFlooring=(MaterialSpinner) rootView.findViewById(R.id.sp_flooring);
        spIntFinish=(MaterialSpinner) rootView.findViewById(R.id.sp_internal_finish);
        spExtFinish=(MaterialSpinner) rootView.findViewById(R.id.sp_external_finish);
        spElectric=(MaterialSpinner) rootView.findViewById(R.id.sp_electric);
        spPlumbing=(MaterialSpinner) rootView.findViewById(R.id.sp_plumbing);
        spKitchen=(MaterialSpinner) rootView.findViewById(R.id.sp_kitchen);
        spParking=(MaterialSpinner) rootView.findViewById(R.id.sp_parking);
        spSideMargin=(MaterialSpinner) rootView.findViewById(R.id.sp_side_margin);
        spRoofing=(MaterialSpinner) rootView.findViewById(R.id.sp_roofing);

        btnSubmit=(Button)rootView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        Bundle bundle=getArguments();
        case_id=bundle.getString("case_id");
        Log.e("case_id",case_id);
       // Log.e("building_id",bundle.getString("building_id"));
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
                if(getActivity()!=null) {
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_found);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spFoundation.setAdapter(aa);
                }
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_walls);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spWalls.setAdapter(aa);
                }
          //      progressDialog.dismiss();
                getRoofing();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getRoofing(){

        // progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getRoofing();
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

                        list_roof.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_walls size",""+list_roof.size());
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_roof);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spRoofing.setAdapter(aa);
                }
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_doors);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDoors.setAdapter(aa);
                }
            //    progressDialog.dismiss();
                getWindows();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getWindows(){

        //  progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getWindows();
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

                        list_windows.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_windows size",""+list_windows.size());
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_windows);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spWindows.setAdapter(aa);
                }
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

                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_flooring);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spFlooring.setAdapter(aa);
                }
               // progressDialog.dismiss();
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_int_finish);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spIntFinish.setAdapter(aa);
                }
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_ext_finish);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spExtFinish.setAdapter(aa);
                }
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_electric);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spElectric.setAdapter(aa);
                }
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

                 if(getActivity()!=null)
                 {
                ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_plumbing);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 spPlumbing.setAdapter(aa);

                }
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
                    if(getActivity()!=null) {
                        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_kitchen);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spKitchen.setAdapter(aa);
                    }
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
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_parking);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spParking.setAdapter(aa);

                    list_yesNo.add("Yes");
                    list_yesNo.add("No");
                    ArrayAdapter aa1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_yesNo);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPlinth.setAdapter(aa1);
                    spIntPlaster.setAdapter(aa1);
                    spExtPlaster.setAdapter(aa1);
                    spRcc.setAdapter(aa1);
                    spOtherWork.setAdapter(aa1);
                    spFlooringWork.setAdapter(aa1);
                    spBrick.setAdapter(aa1);
                   // progressDialog.dismiss();
                }

                getSideMargin();

            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getSideMargin(){

        //  progressDialog.show();
        lookupInterface getResponse = APIClient.getClient().create(lookupInterface.class);
        Call<ArrayList<ChildPojoStaticLookup>> call = getResponse.getSideMargin();
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

                        list_side_margin.add(childPojoStaticLookups.get(i).getLookupItemValue());
                    }
                }

                Log.e("list_side_margin size",""+list_side_margin.size());
                if(getActivity()!=null) {
                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_side_margin);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spSideMargin.setAdapter(aa);

                    progressDialog.dismiss();
                }

                getBuildingDetails(case_id);

            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoStaticLookup>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getBuildingDetails(final String case_id){

        progressDialog.show();
        PropertyTabGetInterface getResponse = APIClient.getClient().create(PropertyTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getBuilding(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {

                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        building_id=childPojoCase.get("BuildingId");

                    }
                }
                //progressDialog.dismiss();
                getWordDetails(case_id);

            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getWordDetails(final String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getWord(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    wordExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        spFoundationWork.setSelection(list_found_work.indexOf(childPojoCase.get("FoundationWork"))+1);
                        spPlinth.setSelection(list_yesNo.indexOf(childPojoCase.get("PlinthWork"))+1);
                        spRcc.setSelection(list_yesNo.indexOf(childPojoCase.get("RCCWork"))+1);
                         etSlabs.setText(childPojoCase.get("RCCWorkSlabs"));
                        spBrick.setSelection(list_yesNo.indexOf(childPojoCase.get("BrickWork"))+1);
                        spIntPlaster.setSelection(list_yesNo.indexOf(childPojoCase.get("InternalPlaster"))+1);
                        spExtPlaster.setSelection(list_yesNo.indexOf(childPojoCase.get("ExternalPlaster"))+1);
                        spFlooringWork.setSelection(list_yesNo.indexOf(childPojoCase.get("Flooring"))+1);
                        spOtherWork.setSelection(list_yesNo.indexOf(childPojoCase.get("AllOtherWork"))+1);

                    }
                }
                else
                    wordExist=false;

                    progressDialog.dismiss();
                getSpecifications(case_id);
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getSpecifications(final String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getSpecifications(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    specExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                      spFoundation.setSelection(list_found.indexOf(childPojoCase.get("Foundation"))+1);
                      spWalls.setSelection(list_walls.indexOf(childPojoCase.get("Walls"))+1);
                      spDoors.setSelection(list_doors.indexOf(childPojoCase.get("Doors"))+1);
                      spWindows.setSelection(list_windows.indexOf(childPojoCase.get("Windows"))+1);
                      spRoofing.setSelection(list_roof.indexOf(childPojoCase.get("Roofing"))+1);
                        spFlooring.setSelection(list_flooring.indexOf(childPojoCase.get("Flooring"))+1);
                        spIntFinish.setSelection(list_yesNo.indexOf(childPojoCase.get("InternalPlasterPainting"))+1);
                        spExtFinish.setSelection(list_yesNo.indexOf(childPojoCase.get("ExternalPlasterPainting"))+1);
                        spElectric.setSelection(list_electric.indexOf(childPojoCase.get("ElectricalInstallation"))+1);
                        spPlumbing.setSelection(list_plumbing.indexOf(childPojoCase.get("PlumbingInstallation"))+1);
                        spKitchen.setSelection(list_kitchen.indexOf(childPojoCase.get("KitchenPlatform"))+1);
                        spParking.setSelection(list_parking.indexOf(childPojoCase.get("ParkingFlooring"))+1);
                        spSideMargin.setSelection(list_side_margin.indexOf(childPojoCase.get("SideMargin"))+1);
                        etLifts.setText(childPojoCase.get("NoOfLifts"));
                        etAminities.setText(childPojoCase.get("OtherAmenitySpec"));

                    }
                }
                else
                    specExist=false;

                progressDialog.dismiss();
                getCaseVal(case_id);
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getCaseVal(final String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getCaseVal(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    valExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        etRemarks.setText(childPojoCase.get("Remark_Text"));

                       /* etBeforeFloor.setText(childPojoCase.get("BeforeFloorDetails"));
                        etNoOfFloors.setText(childPojoCase.get("PresentNoOfFloors"));
                        etProposedNoOfFloors.setText(childPojoCase.get("ProposedNoOfFloors"));
                        building_id=childPojoCase.get("BuildingId");*/

                    }
                }
                else
                    valExist=false;

                progressDialog.dismiss();
                //getBoundaryDetails();
                getCost(case_id);
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getCost(final String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getCost(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    costExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                  etRateFrom.setText(childPojoCase.get("MarketRateFrom_RsPerSqFt"));
                  etRateTo.setText(childPojoCase.get("MarketRateTo_RsPerSqFt"));

                    }
                }
                else
                    costExist=false;

                progressDialog.dismiss();
             getSrtEndDt(case_id);
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void getSrtEndDt(final String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getSrtEndDt(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    dtExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                       dtFrom.setText(childPojoCase.get("ProjectStartDate"));
                        dtTo.setText(childPojoCase.get("ProjExpCompletionDate"));

                    }
                }
                else
                    dtExist=false;

                progressDialog.dismiss();
                getViolation(case_id);
                //getBoundaryDetails();
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void getViolation(String case_id){

        progressDialog.show();
        WorkTabGetInterface getResponse = APIClient.getClient().create(WorkTabGetInterface.class);
        Call<ArrayList<HashMap<String,String>>> call = getResponse.getViolation(case_id);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {

                Log.e("Inside", "onResponse");

                //  ArrayList<ChildPojoCase> childPojoCase = response.body();
                if(response.body().size()>0) {
                    violExist=true;
                    HashMap<String, String> childPojoCase = response.body().get(0);
                    if (childPojoCase != null) {

                        etDeviation.setText(childPojoCase.get("ViolationObserved"));
                    }
                }
                else
                    violExist=false;

                progressDialog.dismiss();
                //getBoundaryDetails();
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addWordDetails(final String case_id){

        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(wordExist==true) {
            call = getResponse.updateWord(building_id,
                    spFoundationWork.getSelectedItem().toString(),
                    spPlinth.getSelectedItem().toString(),
                    spRcc.getSelectedItem().toString(),
                    etSlabs.getText().toString(),
                    spBrick.getSelectedItem().toString(),
                    spFlooringWork.getSelectedItem().toString(),
                    spOtherWork.getSelectedItem().toString(),
                    spIntPlaster.getSelectedItem().toString(),
                    spExtPlaster.getSelectedItem().toString(), case_id);
        }
        else{
            call = getResponse.addWord(building_id,
                    spFoundationWork.getSelectedItem().toString(),
                    spPlinth.getSelectedItem().toString(),
                    spRcc.getSelectedItem().toString(),etSlabs.getText().toString(),spBrick.getSelectedItem().toString(),
                    spFlooringWork.getSelectedItem().toString(),
                    spOtherWork.getSelectedItem().toString(),
                    spIntPlaster.getSelectedItem().toString(),
                    spExtPlaster.getSelectedItem().toString(), case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {



                //    progressDialog.dismiss();
                addSpecificaions(case_id);


            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addSpecificaions(final String case_id){

       // progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(specExist==true) {
            call = getResponse.updateSpecification(spFoundation.getSelectedItem().toString(),spWalls.getSelectedItem().toString(),spDoors.getSelectedItem().toString(),
                    spWindows.getSelectedItem().toString(),spRoofing.getSelectedItem().toString(),spFlooring.getSelectedItem().toString(),
                    spIntPlaster.getSelectedItem().toString(),spExtPlaster.getSelectedItem().toString(),
                    spExtFinish.getSelectedItem().toString(),spPlumbing.getSelectedItem().toString(),spKitchen.getSelectedItem().toString(),
                    spParking.getSelectedItem().toString(),spSideMargin.getSelectedItem().toString(),etLifts.getText().toString(),etAminities.getText().toString(),case_id);
        }
        else{
            call = getResponse.addSpecification(spFoundation.getSelectedItem().toString(),spWalls.getSelectedItem().toString(),spDoors.getSelectedItem().toString(),
                    spWindows.getSelectedItem().toString(),spRoofing.getSelectedItem().toString(),spFlooring.getSelectedItem().toString(),
                    spIntPlaster.getSelectedItem().toString(),spExtPlaster.getSelectedItem().toString(),
                    spExtFinish.getSelectedItem().toString(),spPlumbing.getSelectedItem().toString(),spKitchen.getSelectedItem().toString(),
                    spParking.getSelectedItem().toString(),spSideMargin.getSelectedItem().toString(),etLifts.getText().toString(),etAminities.getText().toString(),case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {





                //    progressDialog.dismiss();
               addSrtEndDt(case_id);

            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void addSrtEndDt(final String case_id){

        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(dtExist==true) {
            call = getResponse.updateSrtEndDt(dtFrom.getText().toString(),dtTo.getText().toString(),case_id);
        }
        else{
            call = getResponse.addSrtEndDt(dtFrom.getText().toString(),dtTo.getText().toString(),case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {


                addCostDetails(case_id);

            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void addCostDetails(final String case_id){


        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(costExist==true) {
            call = getResponse.updateCost(etRateFrom.getText().toString(),etRateTo.getText().toString(),case_id);
        }
        else{
            call = getResponse.addCost(etRateFrom.getText().toString(),etRateTo.getText().toString(),case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {



                //    progressDialog.dismiss();
               addCaseValDetails(case_id);

            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void addCaseValDetails(final String case_id){

        progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(valExist==true) {
            call = getResponse.updateCaseVal("0",etRemarks.getText().toString(),case_id);
        }
        else{
            call = getResponse.addCaseVal("0",etRemarks.getText().toString(),case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {



                //    progressDialog.dismiss();
                addViolation(case_id);

            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }
    public void addViolation(final String case_id){

        //progressDialog.show();
        JSONObject jsonObject=new JSONObject();
        WorkTabAddInterface getResponse = APIClient.getClient().create(WorkTabAddInterface.class);
        Call<HashMap<String,String>> call;
        if(violExist==true) {
            call = getResponse.updateViolation(etDeviation.getText().toString(),case_id);
        }
        else{
            call = getResponse.addViolation(etDeviation.getText().toString(),case_id);
        }

        call.enqueue(new Callback<HashMap<String,String>>() {

            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {

                   progressDialog.dismiss();
                   getWordDetails(case_id);

            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }

    public void checkValidity(){

        if(etRemarks.getText().toString().equalsIgnoreCase(""))

            showToast("Please fill all mandatory fields");
        else
            addWordDetails(case_id);

    }

    @Override
    public void onClick(View v) {
        checkValidity();
     //addWordDetails(case_id);
    }

    //display toast
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
