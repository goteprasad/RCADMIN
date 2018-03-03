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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.about.AboutFragment;
import com.example.admin.rcadmin.add_technician.AddTechnicianFragment;
import com.example.admin.rcadmin.enquiry.EnquiryFragment;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.locality.database.CityTableHelper;
import com.example.admin.rcadmin.locality.database.StateTableHelper;
import com.example.admin.rcadmin.locality.database.VillageTableHelper;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.runtime_permissions.RuntimePermissions;
import com.example.admin.rcadmin.technician_list.TechnicianListFragment;
import com.example.admin.rcadmin.user_login.LoginActivity;
import com.example.admin.rcadmin.utils.CloseApp;

import static com.example.admin.rcadmin.constants.AppConstants.MARATHI;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PrefManager prefManager;
    FragmentTransaction fragmentTransaction;
    EnquiryFragment enquiryFragment;
    AddTechnicianFragment addTechnicianFragment;
    Enquiry enquiry;
    String title;
    public static  Toolbar toolbar;
    private TextView navTextViewMobile;
    int PRIVATE_MODE = 0;
    private View navHeader;
    private TextView appUserName,mobile;
    private ImageView navImageview;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private  NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager=new PrefManager(getApplication());

        if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {

            setContentView(R.layout.activity_main_marathi);
        }
        else
        {
            setContentView(R.layout.activity_main_english);
        }

        enquiry=getIntent().getParcelableExtra("");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
            setTitle(getResources().getString(R.string.new_enquiry_marathi));
        }
        else
        {
            setTitle(getResources().getString(R.string.new_enquiry_english));
        }
        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.new_enquiry);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        appUserName=(TextView)navHeader.findViewById(R.id.nav_app_user_name);
        mobile=(TextView)navHeader.findViewById(R.id.nav_textView_mobile);
        navImageview=(ImageView)navHeader.findViewById(R.id.nav_imageview);

        appUserName.setText(prefManager.getUserName());
        mobile.setText(prefManager.getMobile());

        RuntimePermissions.checkReadExternalStoragePermission(MainActivity.this);
        RuntimePermissions.checkWriteExternalStoragePermission(MainActivity.this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }

        CloseApp.closeAppDialog(MainActivity.this);

    }


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
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.new_enquiry_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.new_enquiry_english));
                    }
                    enquiryTransaction(Enquiry.NEW);
                    break;

                case R.id.material_sent:
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.material_sent_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.material_sent_english));
                    }
                    enquiryTransaction(Enquiry.MATERIALSEND);
                    break;

                case R.id.construction_complete:
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.complete_construction_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.complete_construction_english));
                    }
                    enquiryTransaction(Enquiry.CONSTRUCTION_COMPLITED);
                    break;

                case R.id.cancel_order:
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.cancel_order_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.cancel_order_english));
                    }
                    enquiryTransaction(Enquiry.DENIED);
                    break;

                case R.id.technician_list:
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.technician_list_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.technician_list_english));
                    }
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    TechnicianListFragment technicianListFragment = new TechnicianListFragment();
                    fragmentTransaction.replace(R.id.frameLayout, technicianListFragment).addToBackStack(null).commit();
                    break;


                case R.id.add_technician:
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.technician_information_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.technician_information_english));
                    }

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    AddTechnicianFragment addTechnicianFragment = new AddTechnicianFragment();
                    fragmentTransaction.replace(R.id.frameLayout, addTechnicianFragment).addToBackStack(null).commit();
                    break;


                case R.id.about:
                    if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
                        toolbar.setTitle(getResources().getString(R.string.about_marathi));
                    }
                    else
                    {
                        toolbar.setTitle(getResources().getString(R.string.about_english));
                    }

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    AboutFragment aboutFragment = new AboutFragment();
                    fragmentTransaction.replace(R.id.frameLayout, aboutFragment).addToBackStack(null).commit();
                    break;

                case R.id.logout:
                    prefManager.setLogOut();
                    StateTableHelper.deleteStateData(this);
                    CityTableHelper.deleteCityData(this);
                    VillageTableHelper.deleteCityData(this);

                    Intent i = new Intent(MainActivity.this, LanguageSelectionActivity.class);
                    startActivity(i);
                    finish();
                    break;

                default:
                    break;
            }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }


    void enquiryTransaction(String type)
    {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        enquiryFragment =  EnquiryFragment.getInstance(type);
        fragmentTransaction.replace(R.id.frameLayout, enquiryFragment).addToBackStack(null).commit();



    }


    @Override
    public void onResume() {
        super.onResume();

        if(prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {
            getSupportActionBar().setTitle(getResources().getString(R.string.new_enquiry_marathi));
        }
        else
        {
            getSupportActionBar().setTitle(getResources().getString(R.string.new_enquiry_english));
        }
    }

}
