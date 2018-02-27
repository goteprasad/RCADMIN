package com.example.admin.rcadmin.add_technician;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.add_technician.apihelper.Technician_ApiHelper;
import com.example.admin.rcadmin.add_technician.model.Technician;
import com.example.admin.rcadmin.locality.database.CityTable;
import com.example.admin.rcadmin.locality.database.CityTableHelper;
import com.example.admin.rcadmin.locality.database.StateTable;
import com.example.admin.rcadmin.locality.database.StateTableHelper;
import com.example.admin.rcadmin.locality.database.VillageTable;
import com.example.admin.rcadmin.locality.database.VillageTableHelper;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.technician_list.TechnicianListFragment;
import com.example.admin.rcadmin.utils.Utility;
import com.example.admin.rcadmin.utils.validations.Validations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.admin.rcadmin.constants.AppConstants.MARATHI;
import static com.example.admin.rcadmin.current_date_time_function.CurrentDateTime.getCurrentDateTime;

public class AddTechnicianFragment extends Fragment {

    private Technician technician;
    private ApiResultListener listner;
    private SweetAlertDialog sweetAlertDialog;
    private Toolbar constructionTeamToolbar;
    private Button saveBtn;
    private LinearLayout dobDatePickerLayout;
    private EditText technicianName, technicianAddress, technicianMobileNo;
    private RadioGroup radioGroupGender;
    private RadioButton male, female;
    private String selectedGender = "";
    private PrefManager prefManager;
    private Enquiry enquiry;
    private ApiResultListener apiResultListener;
    private TextView technicianDob;
    private DatePickerDialog dpd;
    private int year, day, month;


    private Spinner villageNameSpinner,citySpinner,stateSpinner;
    private ArrayList<CityTable> cityArrayList;
    private ArrayList<VillageTable> villageArrayList;
    private ArrayList<StateTable> stateArrayList;
    private String villageId,cityid,stateId;



    public AddTechnicianFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefManager=new PrefManager(getActivity());
        View view;

        if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {

            view = inflater.inflate(R.layout.fragment_add_technician_marathi, container, false);

        }
        else
        {
            view = inflater.inflate(R.layout.fragment_add_technician_english, container, false);

        }

        initializations(view);
        setGender();
        generateUniqueId();
        setStateSpinnerList();

        stateSelectedListener();

        dobDatePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatePicker();
            }
        });

        getTechnicianDataFromServer();

        return view;
    }


    private void initializations(View view)
    {
        technician=new Technician();
        ((AppCompatActivity) getActivity()).setSupportActionBar(constructionTeamToolbar);

        prefManager=new PrefManager(getContext());
        constructionTeamToolbar = (Toolbar) view.findViewById(R.id.construction_team_toolbar);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        technicianName = (EditText) view.findViewById(R.id.technician_name);
        technicianAddress = (EditText) view.findViewById(R.id.technician_address);
        technicianMobileNo = (EditText) view.findViewById(R.id.technician_mobileno);
        technicianDob = (TextView) view.findViewById(R.id.technician_dob);
        radioGroupGender = (RadioGroup) view.findViewById(R.id.radio_gender);
        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);
        villageNameSpinner=(Spinner)view.findViewById(R.id.village_spinner);
        citySpinner=(Spinner)view.findViewById(R.id.city_spinner);
        stateSpinner=(Spinner)view.findViewById(R.id.state_spinner);
        dobDatePickerLayout=(LinearLayout)view.findViewById(R.id.dob_date_picker_layout);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

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

        if (technicianName.getText().toString().trim().length() == 0)
        {
            /*technicianNameLayout.setErrorEnabled(true);
            technicianNameLayout.setErrorTextAppearance(R.style.error);*/
            technicianName.setError("Enter first name");
            response = false;
        } else {
            //technicianNameLayout.setErrorEnabled(false);
            technicianName.setError(null);
        }


        if (technicianDob.getText().toString().trim().length() == 0) {
            technicianDob.setError("Please Select Technician DOB ");
            response = false;
        } else {
            technicianDob.setError(null);
        }

        if (technicianAddress.getText().toString().trim().length() == 0) {
            //technicianAddressLayout.setErrorEnabled(true);
            //technicianAddressLayout.setErrorTextAppearance(R.style.error);
            technicianAddress.setError("Please Enter Technician Address ");
            response = false;
        } else {
            //technicianAddressLayout.setErrorEnabled(false);
            technicianAddress.setError(null);
        }

        if(!Validations.isValidPhoneNumber(technicianMobileNo.getText().toString()))
        {
            //technicianMobileLayout.setErrorEnabled(true);
            //technicianMobileLayout.setErrorTextAppearance(R.style.error);
            technicianMobileNo.setError("Please Enter Technician Valid Mobile Number");
            response = false;
        }

        else
        {
            //technicianMobileLayout.setErrorEnabled(false);
            technicianMobileNo.setError(null);
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

    public void displayDatePicker() {
        Calendar mcurrentDate = Calendar.getInstance();
        year = mcurrentDate.get(Calendar.YEAR);
        month = mcurrentDate.get(Calendar.MONTH);
        day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                technicianDob.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                technicianDob.setError(null);
            }
        }, year, month, day);
        //final Calendar calender1 = Calendar.getInstance();
        //calender1.set(1980, Calendar.JANUARY, 1);
        //dpd.getDatePicker().setMinDate(calender1.getTimeInMillis());
        //dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }

    private void getTechnicianDataFromServer()
    {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTechnicianData();
                if(checkValidation())
                {
                    sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE).setTitleText("Please wait");;
                    sweetAlertDialog.show();


                    Technician_ApiHelper.technicianAPi(getActivity(),technician, new ApiResultListener() {
                        @Override
                        public void onSuccess(String message) {
                            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText(message);
                            if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                                sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                            }
                            else
                            {
                                sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                            }


                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    technicianName.setText("");
                                    technicianAddress.setText("");
                                    technicianMobileNo.setText("");
                                    technicianDob.setText("");
                                    male.setChecked(false);
                                    female.setChecked(false);

                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    TeamDetailsFragment teamDetailsFragment = TeamDetailsFragment.newInstance(technician);
                                    fragmentTransaction.replace(R.id.frameLayout, teamDetailsFragment).addToBackStack(null).commit();

                                    sweetAlertDialog.dismissWithAnimation();

                                }
                            });
                        }

                        @Override
                        public void onError(String message) {
                            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitleText(""+message);
                            if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                                sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                            }
                            else
                            {
                                sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                            }
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            });
                        }
                    });

                }
            }
        });

            }

    private void setTechnicianData()
    {
       /* {"status":"success","count":1,"type":"addPaymentDetails","result":
       {"id":"1","name":"name",
       "mobile":"9975294783",
       "dob":"0000-00-00",
       "gender":"M","address":"sadasda",
       "addedby":"723hsgd",
       "addeddate":"2018-02-13 19:42:01",
       "villageid":"1"},"message":"Technition Details added successfully"}*/
        technician.setTech_id(generateUniqueId());
        technician.setTechName(technicianName.getText().toString());
        technician.setMobile(new PrefManager(getActivity()).getMobile());
        technician.setDob(technicianDob.getText().toString());
        technician.setAddress(technicianAddress.getText().toString());
        technician.setGender(selectedGender);
        technician.setAddedby(new PrefManager(getActivity()).getUserId());
        technician.setAddeddate(getCurrentDateTime());
        technician.setTmobile(technicianMobileNo.getText().toString());
        technician.setVillageid(villageId);
    }


    private String generateUniqueId()
    {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        String randomNumber= String.valueOf(Utility.generateRandomNumber(getActivity()));
        String uniqueIdTxt=AppConstants.TECHNICIAN_PREFIX+datetime+"_"+randomNumber;

        return uniqueIdTxt;
    }

    private void setStateSpinnerList()
    {
        stateArrayList = StateTableHelper.getStateDataList(getContext());
        ArrayAdapter<StateTable> adapter =
                new ArrayAdapter<StateTable>(getActivity(), R.layout.spinner_item2, stateArrayList);
        adapter.setDropDownViewResource(R.layout.spinner_item2);
        stateSpinner.setAdapter(adapter);
    }


    private void stateSelectedListener()
    {
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StateTable state=stateArrayList.get(i);

                stateId=state.getStateId();
                stateSpinner.setSelection(i);

                setCitySpinnerList();
                citySelectedListner();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setCitySpinnerList()
    {
        cityArrayList= CityTableHelper.getCityDataList(getContext(),stateId);
        ArrayAdapter<CityTable> adapter =
                new ArrayAdapter<CityTable>(getActivity(), R.layout.spinner_item2, cityArrayList);
        adapter.setDropDownViewResource(R.layout.spinner_item2);
        citySpinner.setAdapter(adapter);

    }

    private void citySelectedListner()
    {
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CityTable city = cityArrayList.get(position);
                citySpinner.setSelection(position);
                cityid=city.getId();

                setVillageSpinnerList();
                villageSelectedListener();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setVillageSpinnerList()
    {
        villageArrayList= VillageTableHelper.getVillageDataList(getContext());
        ArrayAdapter<VillageTable> adapter = new ArrayAdapter<VillageTable>(getActivity(), R.layout.spinner_item2, villageArrayList);
        adapter.setDropDownViewResource(R.layout.spinner_item2);
        villageNameSpinner.setAdapter(adapter);
    }

    private void villageSelectedListener()
    {
        villageNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VillageTable village=villageArrayList.get(i);
                villageId=village.getId();
                villageNameSpinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}


