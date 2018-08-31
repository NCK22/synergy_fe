package com.nyasa.synergyfieldengineer.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nyasa.synergyfieldengineer.Fragment.TabLocationDetails;
import com.nyasa.synergyfieldengineer.Fragment.TabPropertyDetails;
import com.nyasa.synergyfieldengineer.Fragment.TabUploadPhoto;
import com.nyasa.synergyfieldengineer.Fragment.TabWorkStatus;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.storage.SPUserProfile;
import com.nyasa.synergyfieldengineer.Fragment.TabBasicDetails;
import com.squareup.picasso.Picasso;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabParentCaseDetailActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private TabParentCaseDetailActivity.SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    String quotes[];
    TextView bottomText;
    Toolbar toolbar;
    TextView toolbar_textView;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;

    TextView testtv;
    TabLayout tabLayout;
    SPUserProfile spUserProfile;
    ProgressDialog progressDialog;

    Intent intent;
    Bundle bundle;
    String tabFlag="home";
    public static boolean bCompleted=false,pCompleted=false,wCompleted=false;
    public static String client_name="",buildingId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_parent_case_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        progressDialog=new ProgressDialog(this);
        spUserProfile=new SPUserProfile(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tabLayout=(TabLayout)findViewById(R.id.tl_parent);
        tabLayout.addOnTabSelectedListener(this);
       // tabLayout.setTabTextColors(Color.BLACK,Color.WHITE);
/*
        tabLayout.getChildAt(1).setEnabled(false);
        tabLayout.getChildAt(2).setEnabled(false);
*/

        intent=getIntent();

        bundle = new Bundle();
        tabFlag=intent.getStringExtra("tabFlag");


        //   Tabs Activity

        mSectionsPagerAdapter = new TabParentCaseDetailActivity.SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_parent);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_parent);



        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

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

        setHeader();


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

       /* if(tab.getPosition()==1)
        {
            if(bCompleted==true){

              //  tabLayout.getChildAt(1).setEnabled(true);
            }
            else {
              *//*  tabLayout.getChildAt(1).setSelected(false);
                tabLayout.getChildAt(0).setSelected(true);
                tabLayout.getChildAt(1).setEnabled(false);*//*
            }
                        //tab.getCustomView().setEnabled(false);
        }
        if(tab.getPosition()==2)
        {
            if(pCompleted==true)
                tab.getCustomView().setEnabled(true);
            else
                tab.getCustomView().setEnabled(false);
        }*/

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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





    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Log.e("position", String.valueOf(position));
            //Log.e("positi", "" + tabLayout.getSelectedTabPosition());


            switch (position) {


                case 0:
                    Log.e("Tab", "home");
                    TabBasicDetails tabBasicDetails=new TabBasicDetails();
                    Bundle bundle=new Bundle();
                    bundle.putString("case_id",intent.getStringExtra("case_id"));
                    tabBasicDetails.setArguments(bundle);
                    return tabBasicDetails;

                case 1:

                            Log.e("Tab", "whoViewed");
                            TabPropertyDetails tabPropertyDetails = new TabPropertyDetails();
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("client_name",intent.getStringExtra("client_name"));
                            bundle1.putString("case_id", intent.getStringExtra("case_id"));
                            tabPropertyDetails.setArguments(bundle1);
                            Log.e("bCompleted", "" + bCompleted);
                            return tabPropertyDetails;




                case 2:

                        Log.e("Tab", "whoShortListed");
                    TabWorkStatus tabWorkStatus=new TabWorkStatus();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("case_id",intent.getStringExtra("case_id"));
                    bundle2.putString("building_id",buildingId);
                    tabWorkStatus.setArguments(bundle2);
                    return tabWorkStatus;

                case 3:

                    Log.e("Tab", "whoShortListed");
                    TabLocationDetails tabLocationDetails=new TabLocationDetails();
                    Bundle bundle3=new Bundle();
                    bundle3.putString("case_id",intent.getStringExtra("case_id"));
                    tabLocationDetails.setArguments(bundle3);
                    return tabLocationDetails;

                case 4:

                    Log.e("Tab", "whoShortListed");
                    TabUploadPhoto tabUploadPhoto=new TabUploadPhoto();
                    Bundle bundle4=new Bundle();
                    bundle4.putString("case_id",intent.getStringExtra("case_id"));
                    tabUploadPhoto.setArguments(bundle4);
                    return tabUploadPhoto;



                default:
                    return null;

            }


        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            Log.e("positionItem",""+object);
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return 5;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
               // startActivity(new Intent(getApplicationContext(),TabViewParentActivity.class).putExtra("tabFlag","home"));
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void logout() {

        new AlertDialog.Builder(TabParentCaseDetailActivity.this)
                .setTitle(getString(R.string.menu_logout))
                .setMessage(getString(R.string.logout_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //MyApp.saveIsLogin(false);
                        spUserProfile.setIsLogin("false");

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

   /* public void getProfile(){

        Log.e("Inside","getProfile");
        progressDialog.show();

        getProfileInterface getResponse = APIClient.getClient().create(getProfileInterface.class);
        Call<ParentPojoProfile> call = getResponse.doGetListResources(spCustProfile.getMatrimonyId());
        call.enqueue(new Callback<ParentPojoProfile>() {
            @Override
            public void onResponse(Call<ParentPojoProfile> call, Response<ParentPojoProfile> response) {

                Log.e("Inside","onResponse");
 Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());

                ParentPojoProfile parentPojoProfile =response.body();
                if(parentPojoProfile !=null){
                    if(parentPojoProfile.getStatus().equalsIgnoreCase("1")){
                        Log.e("Response","Success");
                        Log.e("objsize", ""+ parentPojoProfile.getObjProfile().size());
                        if(parentPojoProfile.getObjProfile().get(0).getProfile_photo()!=null) {
                            Log.e("profile_photo res", parentPojoProfile.getObjProfile().get(0).getProfile_photo());
                            spCustProfile.setProfilePhotoPath(parentPojoProfile.getObjProfile().get(0).getProfile_photo());
                        }
                        spCustProfile.setName(parentPojoProfile.getObjProfile().get(0).getProfile_name());
                        Log.e("In getProfile",parentPojoProfile.getObjProfile().get(0).getProfile_name());
                        spCustProfile.setGender(parentPojoProfile.getObjProfile().get(0).getGender());
                       setHeader();

                    }
                }
                else
                    Log.e("parentpojotabwhome","null");
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ParentPojoProfile> call, Throwable t) {

                Log.e("throwable",""+t);
                progressDialog.dismiss();
            }
        });

    }
*/
    private void setHeader() {/*
        Log.e("setHeader","TabViewParent");
        if (spCustProfile.getIsLogin().equalsIgnoreCase("true")) {
            View header = navigationView.getHeaderView(0);
            TextView txtHeaderName = (TextView) header.findViewById(R.id.header_name);
            TextView txtHeaderEmail = (TextView) header.findViewById(R.id.header_email);
            final ShapedImageView imageUser = (ShapedImageView) header.findViewById(R.id.header_image);
            Log.e("In header",spCustProfile.getName());
             txtHeaderName.setText(spCustProfile.getName());
            txtHeaderEmail.setText(spCustProfile.getEmail());

            if(spCustProfile.getProfilePhotoPath()!=null) {
                Log.e("profile_photo","http://applex360.in/Deshpande-family/Matrimony-web/" + spCustProfile.getProfilePhotoPath());
  Picasso.with(this).load("http://applex360.in/Deshpande-family/Matrimony-web/" + spCustProfile.getProfilePhotoPath())
                        .placeholder(R.mipmap.userprofile)
                        .into(imageUser);



                Picasso.Builder builder = new Picasso.Builder(this);
                builder.listener(new Picasso.Listener()
                {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                    {
                        exception.printStackTrace();
                        Log.e("Exception",""+exception);
                    }
                });
                builder.build()
                        .load("http://applex360.in/Deshpande-family/Matrimony-web/"+spCustProfile.getProfilePhotoPath())
                        .placeholder(R.mipmap.userprofile)
                        .fit()
                        .into(imageUser);

            }
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

            Intent profile = new Intent(TabParentCaseDetailActivity.this, ProfileActivity.class);
                    profile.putExtra("id", spUserProfile.getUser_id());

                    profile.putExtra("editFlag", "true");
                    profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(profile);

                }
            });
        }*/
    }

    private void exitApp() {
        new AlertDialog.Builder(TabParentCaseDetailActivity.this)
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

    public void setToolbarTitle(String title)
    {
        toolbar.setTitle(title);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // exitApp();
    }
}

