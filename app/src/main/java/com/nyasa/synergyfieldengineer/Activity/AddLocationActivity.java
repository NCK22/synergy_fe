package com.nyasa.synergyfieldengineer.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.nyasa.synergyfieldengineer.R;

public class AddLocationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Location");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.inflateMenu(R.menu.menu_drawer);
        navigationView.setCheckedItem(R.id.menu_go_location);
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
                startActivity(new Intent(getApplicationContext(), AddLocationActivity.class));
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


    private void logout() {

        new AlertDialog.Builder(AddLocationActivity.this)
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
        new AlertDialog.Builder(AddLocationActivity.this)
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
}
