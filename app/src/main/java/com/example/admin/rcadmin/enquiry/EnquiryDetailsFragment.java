package com.example.admin.rcadmin.enquiry;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.add_technician.adapter.TechnicianAdapter;
import com.example.admin.rcadmin.add_technician.model.Technician;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.enquiry.adapter.EnquiryAdapter;
import com.example.admin.rcadmin.enquiry.adapter.TeamAdapter;
import com.example.admin.rcadmin.enquiry.apihelper.Admin_WebApiHelper;
import com.example.admin.rcadmin.enquiry.apihelper.UpdateEnquiry_ApiHelper;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.enquiry.model.Team;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.pref_manager.PrefManager;


import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class EnquiryDetailsFragment extends Fragment {

    private RecyclerView technician_recyclerView;
    private ArrayList<Technician> technicianArrayList;
    private TechnicianAdapter technicianAdapter;
    private TextView customer_name, mobile_number, village_name, aadhar_number,
            customer_age, roof_type, house_type, kitchen_height, enquiry_date,
            enquiry_by, technicianTeamLabel;
    private CircleImageView profile_image;
    private ImageView placeImageView;
    private Enquiry enquiry;
    private Button btnSendMaterial, btnCancelOrder;
    private ArrayList<Enquiry> enquiryArrayList;
    private SweetAlertDialog sweetAlertDialog;
    private EnquiryAdapter enquiryAdapter;
    private PrefManager prefManager;
    private TeamAdapter teamAdapter;
    private ArrayList<Team> teamArrayList;

    String enquiryType = Enquiry.NEW;


    public static EnquiryDetailsFragment getInstance(Enquiry enquiry) {
        EnquiryDetailsFragment fragment = new EnquiryDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Enquiry", enquiry);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            enquiry = getArguments().getParcelable("Enquiry");
        }
    }

    public EnquiryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        prefManager = new PrefManager(getActivity());
        if (prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            view = inflater.inflate(R.layout.fragment_enquiry_details_marathi, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_enquiry_details_english, container, false);
        }

        initializations(view);
        setCustomerData();


        teamAdapter = new TeamAdapter(getContext(), teamArrayList, technician_recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        technician_recyclerView.setLayoutManager(layoutManager);
        technician_recyclerView.setAdapter(teamAdapter);


        if (enquiry.getState().equalsIgnoreCase(Enquiry.NEW)) {
            btnSendMaterial.setVisibility(View.VISIBLE);
            btnCancelOrder.setVisibility(View.VISIBLE);
        }
        else
        {
            btnSendMaterial.setVisibility(View.INVISIBLE);
            btnCancelOrder.setVisibility(View.INVISIBLE);
            technicianTeamLabel.setVisibility(View.INVISIBLE);

        }
        btnSendMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateEnquiry(Enquiry.MATERIALSEND);
            }
        });
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateEnquiry(Enquiry.DENIED);
            }
        });
        return view;

    }

    private void initializations(View view) {
        profile_image = (CircleImageView) view.findViewById(R.id.profileImage);
        placeImageView = (ImageView) view.findViewById(R.id.place_image);
        customer_name = (TextView) view.findViewById(R.id.customerName);
        mobile_number = (TextView) view.findViewById(R.id.mobileNumber);
        village_name = (TextView) view.findViewById(R.id.villageName);
        customer_age = (TextView) view.findViewById(R.id.customerAge);
        aadhar_number = (TextView) view.findViewById(R.id.aadharNumber);
        roof_type = (TextView) view.findViewById(R.id.roofType);
        house_type = (TextView) view.findViewById(R.id.houseType);
        kitchen_height = (TextView) view.findViewById(R.id.kitchenHeight);
        enquiry_date = (TextView) view.findViewById(R.id.enquiryDate);
        enquiry_by = (TextView) view.findViewById(R.id.enquiryBy);
        btnSendMaterial = (Button) view.findViewById(R.id.btn_send_material);
        btnCancelOrder = (Button) view.findViewById(R.id.btn_cancel_order);
        technicianTeamLabel = (TextView) view.findViewById(R.id.technician_team_label);
        technician_recyclerView = (RecyclerView) view.findViewById(R.id.technicianRecyclerView);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void setCustomerData() {
        customer_name.setText(String.valueOf(enquiry.getName()));
        mobile_number.setText(String.valueOf(enquiry.getMobile()));
        village_name.setText(String.valueOf(enquiry.getVillagename()));
        if (prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            customer_age.setText(String.valueOf(getResources().getString(R.string.age_marathi) + enquiry.getAge()));
        } else {
            customer_age.setText(String.valueOf(getResources().getString(R.string.age_english) + enquiry.getAge()));
        }
        aadhar_number.setText(String.valueOf(enquiry.getAdharid()));
        roof_type.setText(String.valueOf(enquiry.getRoofType()));
        house_type.setText(String.valueOf(enquiry.getHousetype()));
        kitchen_height.setText(String.valueOf(enquiry.getHieght() + " Ft"));
        enquiry_date.setText(String.valueOf(enquiry.getAddeddate()));
        enquiry_by.setText(String.valueOf(enquiry.getName()));

    }

    private void setUpdateEnquiry(String state) {

        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        if (prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_marathi));
        } else {
            sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_english));
        }
        sweetAlertDialog.show();

        UpdateEnquiry_ApiHelper.updateTechnicianAPi(getActivity(), enquiry, new ApiResultListener() {
            @Override
            public void onSuccess(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText(message);
                if (prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                } else {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        btnSendMaterial.setVisibility(View.GONE);
                        btnCancelOrder.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onError(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("" + message);
                if (prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                } else {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
            }
        }, state);
    }




   }