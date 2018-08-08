package com.nyasa.synergyfieldengineer.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.nyasa.synergyfieldengineer.APIClient;
import com.nyasa.synergyfieldengineer.Adapter.VisitListAdapter;
import com.nyasa.synergyfieldengineer.Interface.assignedCaseInterface;
import com.nyasa.synergyfieldengineer.Interface.getCaseDetailsInterface;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoAssignedCase;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.nyasa.synergyfieldengineer.Pojo.ParentPojoAssignedCase;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    VisitListAdapter adapter;
    ArrayList<ChildPojoCase> mListItem=new ArrayList<ChildPojoCase>();
    ProgressDialog progressDialog;
    ArrayList<String> list_assigned_case=new ArrayList<String>();
    SPUserProfile spUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        spUserProfile=new SPUserProfile(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        recyclerView = (RecyclerView) findViewById(R.id.rv_visit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ChildPojoCase childPojoCase1=new ChildPojoCase();
        childPojoCase1.setCaseId("44768");
        childPojoCase1.setClientName("Mrs. Rashmi Shaunwk Pol");
        childPojoCase1.setProjectName("SyntNygyr");
        childPojoCase1.setVillageCity("Haveli,Pune");
        childPojoCase1.setState("Maharashtra");
        ChildPojoCase childPojoCase2=new ChildPojoCase();
        childPojoCase2.setCaseId("44893");
        childPojoCase2.setClientName("Mr. Ajit Shishir Joshi");
        childPojoCase2.setProjectName("SyntNygyr");
        childPojoCase2.setVillageCity("Shirwal,Pune");
        childPojoCase2.setState("Maharashtra");
        ChildPojoCase childPojoCase3=new ChildPojoCase();
        childPojoCase3.setCaseId("45211");
        childPojoCase3.setClientName("Mrs. Nilophar Sayyad");
        childPojoCase3.setProjectName("SyntNygyr");
        childPojoCase3.setVillageCity("Hadapsar,Pune");
        childPojoCase3.setState("Maharashtra");
        mListItem.add(childPojoCase1);
        mListItem.add(childPojoCase2);
        mListItem.add(childPojoCase3);
        displayData();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.inflateMenu(R.menu.menu_drawer);
        navigationView.setCheckedItem(R.id.menu_go_home);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //  setHeader();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getAssignecdCase();


    }

    private void displayData() {
        Log.e("displayData","called");
        Log.e("List size",""+mListItem.size());
        adapter = new VisitListAdapter(this, mListItem);
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {

        } else {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        View view =item.getActionView();
//        view.setDrawingCacheBackgroundColor(R.color.btm_state_list);
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.menu_go_home:
                Log.e("menu","home");
                //   toolbar.setTitle(getString(R.string.menu_home));
                startActivity(new Intent(getApplicationContext(), VisitListActivity.class));
                return true;

            case R.id.menu_go_history:
                Log.e("menu","matches");
                //   toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(),HistoryActivity.class).putExtra("tabFlag","matches"));
                finish();
                return true;

            case R.id.menu_go_profile:
                Log.e("menu","profile");
//                toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class).putExtra("tabFlag","profile"));
                finish();
                return true;

            case R.id.menu_go_location:
                Log.e("menu","settings");
//                toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(), AddLocationActivity.class).putExtra("tabFlag", "profile"));
                finish();
                return true;

            case R.id.menu_go_logout:
                logout();
                return true;

        }
        return false;
    }

    public void getAssignecdCase(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

            progressDialog.show();
            assignedCaseInterface getResponse = APIClient.getClient().create(assignedCaseInterface.class);
            Call<ArrayList<ChildPojoAssignedCase>> call = getResponse.doGetListResources(strDate,spUserProfile.getUser_id());
            call.enqueue(new Callback<ArrayList<ChildPojoAssignedCase>>() {
                @Override
                public void onResponse(Call<ArrayList<ChildPojoAssignedCase>> call, Response<ArrayList<ChildPojoAssignedCase>> response) {

                    Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                  ArrayList<ChildPojoAssignedCase> parentPojoAssignedCase = response.body();
                    if (parentPojoAssignedCase != null) {
                        if (parentPojoAssignedCase.size()!=0) {
                                mListItem.clear();

                        /* for(int i=0;i<=parentPojoAssignedCase.getObjCase().size();i++) {
                             list_assigned_case.add(parentPojoAssignedCase.getObjCase().get(i).getCaseId());
                             getCaseDetails(parentPojoAssignedCase.getObjCase().get(i).getCaseId());
                         }*/
                            for(int i=0;i<parentPojoAssignedCase.size();i++) {
                                list_assigned_case.add(parentPojoAssignedCase.get(i).getCaseId());
                                getCaseDetails(parentPojoAssignedCase.get(i).getCaseId());
                            }

                        }
                        Log.e("list_assigned_case size",""+list_assigned_case.size());
                        Log.e("List sizevafter for",""+mListItem.size());
                        displayData();
                        /*if(list_assigned_case.size()>0){
                            getCaseDetails();
                            }
                        */
                        //showToast(parentPojoAssignedCase.g());
                    }


                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ArrayList<ChildPojoAssignedCase>> call, Throwable t) {

                    Log.e("Throwabe ", "" + t);
                    progressDialog.dismiss();
                }
            });
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
                ArrayList<ChildPojoCase> childPojoCase = response.body();
              //  Log.e("ChildPojoCase",childPojoCase.get(0).getCaseId());
                if (childPojoCase != null) {

                    mListItem.add(childPojoCase.get(0));
                }

                Log.e("List size inside",""+mListItem.size());
                displayData();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChildPojoCase>> call, Throwable t) {

                Log.e("Throwabe ", "" + t);
                progressDialog.dismiss();
            }
        });
    }


    private void logout() {

        new AlertDialog.Builder(VisitListActivity.this)
                .setTitle(getString(R.string.menu_logout))
                .setMessage(getString(R.string.logout_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //MyApp.saveIsLogin(false);
                     /*   spCustProfile.setIsLogin("false");
                        spCustProfile.setProfilePhotoPath("");
                        spCustProfile.setGender("");
                        spCustProfile.clearGalleryPhotoPath();*/
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //  .setIcon(R.drawable.ic_logout)
                .show();
    }
    private void exitApp() {
        new AlertDialog.Builder(VisitListActivity.this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit_msg))
                //.setIcon(R.mipmap.ic_launcher_app)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exitApp();
    }

    public void showToast(String msg)
    {
        Toast.makeText(VisitListActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
