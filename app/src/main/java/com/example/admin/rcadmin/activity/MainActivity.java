package com.example.admin.rcadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.enquiry.EnquiryFragment;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.user_login.LoginActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PrefManager prefManager;
    FragmentTransaction fragmentTransaction;
    EnquiryFragment enquiryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefManager=new PrefManager(getApplicationContext());
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        enquiryFragment = new EnquiryFragment();
        fragmentTransaction.replace(R.id.frameLayout, enquiryFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_enq) {
            enquiryTransaction(Enquiry.NEW);

        } else if (id == R.id.material_sent) {
            enquiryTransaction(Enquiry.MATERIALSEND);
        } else if (id == R.id.construction_complete) {
            enquiryTransaction(Enquiry.CONSTRUCTION_COMPLITED);
        } else if (id == R.id.team_list) {
            enquiryTransaction(Enquiry.NEW);
        } else if (id == R.id.add_team) {
        } else if (id == R.id.setting) {


        } else if (id == R.id.about_help) {

        } else if (id == R.id.logout) {
            prefManager.setLogOut();
            Intent i= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void enquiryTransaction(String type)
    {
        enquiryFragment=new EnquiryFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        enquiryFragment = new EnquiryFragment();
        fragmentTransaction.replace(R.id.frameLayout, enquiryFragment).commit();
        enquiryFragment.setEnquiryType(type);
    }
}
