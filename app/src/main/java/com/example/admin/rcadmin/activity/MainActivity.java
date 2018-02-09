package com.example.admin.rcadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.construction_team.ConstructionTeamRegistrationFragment;
import com.example.admin.rcadmin.enquiry.EnquiryFragment;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.user_login.LoginActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PrefManager prefManager;
    FragmentTransaction fragmentTransaction;
    EnquiryFragment enquiryFragment;
    Enquiry enquiry;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enquiry=getIntent().getParcelableExtra("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefManager=new PrefManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.new_enquiry);



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
        // Handle navigation view item clicks here.\
        /*fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.new_enquiry) {
            //enquiryTransaction(Enquiry.NEW);
            //enquiryFragment = new EnquiryFragment();
            //fragmentTransaction.replace(R.id.frameLayout, enquiryFragment).commit();
        }
        else if (id == R.id.material_sent) {
            //enquiryTransaction(Enquiry.MATERIALSEND);
        }
        else if (id == R.id.construction_complete) {
            //enquiryTransaction(Enquiry.CONSTRUCTION_COMPLITED);
        }
        else if (id == R.id.team_list) {
            //enquiryTransaction(Enquiry.NEW);
        }
        else if (id == R.id.add_team) {
        }
        else if (id == R.id.setting) {
        }
        else if (id == R.id.about_help) {

        }
        else if (id == R.id.logout) {

        }
*/
        int id = item.getItemId();
        displaySelectedScreen(id);

        return true;
    }


    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {
            case R.id.new_enquiry:
                title = getString(R.string.new_enquiry);
                enquiryTransaction(Enquiry.NEW);
                break;

            case R.id.material_sent:
                title = getString(R.string.material_sent);
                enquiryTransaction(Enquiry.MATERIALSEND);
                break;

            case R.id.construction_complete:
                title = getString(R.string.complete_construction);
                enquiryTransaction(Enquiry.CONSTRUCTION_COMPLITED);
                break;


            case R.id.add_team:
                title = getString(R.string.member_information);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                ConstructionTeamRegistrationFragment constructionTeamRegistrationFragment = new ConstructionTeamRegistrationFragment();
                fragmentTransaction.replace(R.id.frameLayout, constructionTeamRegistrationFragment).commit();
                break;

            case R.id.logout:
                prefManager.setLogOut();
                Intent i= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;
        }

        setTitle();
        /*//replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, fragment);
            ft.commit();
        }
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    void enquiryTransaction(String type)
    {
        enquiryFragment=new EnquiryFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        enquiryFragment = new EnquiryFragment();
        fragmentTransaction.replace(R.id.frameLayout, enquiryFragment).commit();
        enquiryFragment.setEnquiryType(type);
    }

    private void setTitle() {
        getSupportActionBar().setTitle(title);
    }
}
