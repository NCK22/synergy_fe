/*
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

import com.squareup.picasso.Picasso;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabParentCaseDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

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
    //SPCustProfile spCustProfile;
    ProgressDialog progressDialog;


    Intent intent;
    Bundle bundle;
    String tabFlag="home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_parent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        progressDialog=new ProgressDialog(this);
        spCustProfile=new SPCustProfile(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tabLayout=(TabLayout)findViewById(R.id.tl_parent);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabTextColors(Color.BLACK,Color.WHITE);

        intent=getIntent();

        bundle = new Bundle();
        tabFlag=intent.getStringExtra("tabFlag");


        //   Tabs Activity

        mSectionsPagerAdapter = new TabViewParentActivity.SectionsPagerAdapter(getSupportFragmentManager());
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

        bottmNavView=(BottomNavigationViewEx)findViewById(R.id.bottomNavigationView);
        bottmNavView.setOnNavigationItemSelectedListener(this);
        bottmNavView.setSelectedItemId(R.id.bmenu_go_home);
        bottmNavView.enableAnimation(false);
        bottmNavView.enableShiftingMode(false);
        bottmNavView.enableItemShiftingMode(false);
        bottmNavView.setIconSize(30,30);
        bottmNavView.setTextSize(0);
        bottmNavView.setTextVisibility(false);
        bottmNavView.setFocusableInTouchMode(true);
        bottmNavView.setPadding(0,0,0,0);

      //  bottmNavView.setItemBackground(0,R.color.colorTheme);
     //   bottmNavView.setIconSizeAt(bottmNavView.getMenuItemPosition(bottmNavView.getMenu().findItem(bottmNavView.getSelectedItemId())),32,32);

        // if(spCustProfile.getProfilePhotoPath().equalsIgnoreCase(""))
        getProfile();
    Log.e("SPProfilephoto",spCustProfile.getProfilePhotoPath());
        setHeader();


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {



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

                startActivity(new Intent(TabViewParentActivity.this, TabViewParentActivity.class));
                return true;

            case R.id.menu_go_matches:
                Log.e("menu","matches");
             //   toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(),TabParentMatchesActivity.class).putExtra("tabFlag","matches"));
                finish();
                return true;

            case R.id.menu_go_profile:
                Log.e("menu","profile");
//                toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(),TabParentProfileActivity.class).putExtra("tabFlag","profile"));
                finish();
                return true;

            case R.id.menu_go_setting:
                Log.e("menu","settings");
//                toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(), TabParentSettingsActivity.class).putExtra("tabFlag", "profile"));
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
                    TabHome tabHome = new TabHome();
                    return tabHome;

                case 1:

                        Log.e("Tab", "whoViewed");
                        TabWhoViewed tabWhoViewed = new TabWhoViewed();
                        return tabWhoViewed;

                case 2:

                        Log.e("Tab", "whoShortListed");
                        TabWhoShortlisted tabWhoShortlisted = new TabWhoShortlisted();
                        return tabWhoShortlisted;



                case 3:
                        Log.e("Tab", "whoInterested");
                        TabWhoInterested tabWhoInterested = new TabWhoInterested();
                        return tabWhoInterested;

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

            return 4;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
               // startActivity(new Intent(getApplicationContext(),TabViewParentActivity.class).putExtra("tabFlag","home"));
                break;

            case R.id.menu_go_matches:
                //startActivity(new Intent(getApplicationContext(),TabParentMatchesActivity.class).putExtra("tabFlag","matches"));
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void logout() {

        new AlertDialog.Builder(TabViewParentActivity.this)
                .setTitle(getString(R.string.menu_logout))
                .setMessage(getString(R.string.logout_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //MyApp.saveIsLogin(false);
                        spCustProfile.setIsLogin("false");
                        spCustProfile.setProfilePhotoPath("");
                        spCustProfile.setGender("");
                        spCustProfile.clearGalleryPhotoPath();
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

    public void getProfile(){

        Log.e("Inside","getProfile");
        progressDialog.show();

        getProfileInterface getResponse = APIClient.getClient().create(getProfileInterface.class);
        Call<ParentPojoProfile> call = getResponse.doGetListResources(spCustProfile.getMatrimonyId());
        call.enqueue(new Callback<ParentPojoProfile>() {
            @Override
            public void onResponse(Call<ParentPojoProfile> call, Response<ParentPojoProfile> response) {

                Log.e("Inside","onResponse");
               */
/* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*//*

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

    private void setHeader() {
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
              */
/*  Picasso.with(this).load("http://applex360.in/Deshpande-family/Matrimony-web/" + spCustProfile.getProfilePhotoPath())
                        .placeholder(R.mipmap.userprofile)
                        .into(imageUser);*//*



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

                    */
/*Intent profile = new Intent(TabParentProfileActivity.this, TabParentProfileActivity.class);
                    profile.putExtra("id", MyApp.getUserId());
                    if (MyApp.getIsJobProvider())
                        profile.putExtra("p_type", "jp");
                    else
                        profile.putExtra("p_type", "js");
                    profile.putExtra("editFlag", "true");
                    profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(profile);*//*

                }
            });
        }
    }

    private void exitApp() {
        new AlertDialog.Builder(TabViewParentActivity.this)
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
        //super.onBackPressed();
        exitApp();
    }
}

*/
