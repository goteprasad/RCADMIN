package com.example.admin.rcadmin.construction_team;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.scanner.AdharScanner;
import com.example.admin.rcadmin.utils.validations.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ConstructionTeamRegistrationFragment extends Fragment {


    private Toolbar constructionTeamToolbar;
    private Button saveBtn,register_Byscanid;
    private TextInputEditText construction_member_name, construction_member_address,
            construction_member_mobileno, construction_member_age;
    private RadioGroup radioGroupGender;
    private RadioButton male, female;
    private String selectedGender = "";
    private int SCAN_ID=4;

    public static final int MY_PERMISSIONS_REQUEST_ACCOUNTS=111;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA=112;


    public ConstructionTeamRegistrationFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_construction_team_details, container, false);

        initializations(view);
        onclicklisteners();
        setGender();
        checkAndRequestPermissions();
        return view;
    }


    private void initializations(View view)
    {
        ((AppCompatActivity) getActivity()).setSupportActionBar(constructionTeamToolbar);

        constructionTeamToolbar = (Toolbar) view.findViewById(R.id.construction_team_toolbar);
        register_Byscanid=(Button)view.findViewById(R.id.register_Byscanid);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        construction_member_name = (TextInputEditText) view.findViewById(R.id.construction_member_name);
        construction_member_address = (TextInputEditText) view.findViewById(R.id.construction_member_address);
        construction_member_mobileno = (TextInputEditText) view.findViewById(R.id.construction_member_mobileno);
        construction_member_age = (TextInputEditText) view.findViewById(R.id.construction_member_age);
        radioGroupGender = (RadioGroup) view.findViewById(R.id.radio_gender);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

    }

    private void onclicklisteners()
    {
        register_Byscanid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AdharScanner.class);
                startActivityForResult(intent, SCAN_ID);// Activity is started with requestCode 2
            }
        });

    }



   /* private String generateUniqueId()
    {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        String uniqueId = CONSTRUCT_PREFIX+datetime;

        return uniqueId;
    }*/

    public void setGender()
    {
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    selectedGender = "M";
                } else if (checkedId == R.id.female) {
                    selectedGender = "F";
                }
            }
        });

    }

    private boolean checkValidation()
    {
        boolean response = true;

        if (construction_member_name.getText().toString().trim().length() == 0) {
            construction_member_name.setError("Please Enter Construction Member Name");
            response = false;
        } else {
            construction_member_name.setError(null);
        }

        if (construction_member_age.getText().toString().trim().length() == 0) {
            construction_member_age.setError("Please Enter Construction Member Age ");
            response = false;
        } else {
            construction_member_age.setError(null);
        }

        if (construction_member_address.getText().toString().trim().length() == 0) {
            construction_member_address.setError("Please Enter Construction Member Address ");
            response = false;
        } else {
            construction_member_address.setError(null);
        }

        if(!Validations.isValidPhoneNumber(construction_member_mobileno.getText().toString()))
        {
            construction_member_mobileno.setError("Please Enter valid Mobile Number");
            response = false;
        }

        else
        {
            construction_member_mobileno.setError(null);
        }

        if (radioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
            response = false;
            // no radio buttons are checked
        } else {
            // one of the radio buttons is checked

        }
        return response;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == SCAN_ID) {
            String scanData = data.getStringExtra("MESSAGE");

            /*   */
            try {
                JSONObject object = new JSONObject(scanData);
                construction_member_name.setText(object.getString("name"));
                construction_member_name.setFocusable(false);
                construction_member_address.setText(object.getString("address"));
                construction_member_address.setFocusable(false);
                construction_member_age.setText(object.getString("age"));
                construction_member_age.setFocusable(false);
                construction_member_mobileno.setText(object.getString("mobile"));
                construction_member_mobileno.setFocusable(false);
                selectedGender = object.getString("gender");
                if(selectedGender.equalsIgnoreCase("F"))
                {
                    female.setChecked(true);
                }
                else
                {
                    male.setChecked(true);
                }
                female.setClickable(false);
                male.setClickable(false);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }


    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);


        int storagePermission = ContextCompat.checkSelfPermission(getActivity(),


                Manifest.permission.READ_EXTERNAL_STORAGE);



        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(),


                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permission Granted Successfully. Write working code here.
                } else {
                    //You did not accept the request can not use the functionality.
                }
                break;
        }
    }

}


