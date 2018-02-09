package com.example.admin.rcadmin.enquiry;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.construction_team.AdapterConstructionTeam;
import com.example.admin.rcadmin.construction_team.ModelConstructionTeam;
import com.example.admin.rcadmin.enquiry.model.Enquiry;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class EnquiryDetailsFragment extends Fragment {

    private RecyclerView team_view;
    private ArrayList<ModelConstructionTeam> teamList;
    private AdapterConstructionTeam adapterConstructionTeam;
    private TextView customer_name,mobile_number,village_name,aadhar_number,
                     customer_age,roof_type,house_type,kitchen_height,enquiry_date,enquiry_by;
    private CircleImageView profile_image;
    private ImageView place_image;
    private Enquiry enquiry;


    public static EnquiryDetailsFragment getInstance(Enquiry enquiry)
    {
        EnquiryDetailsFragment fragment = new EnquiryDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Enquiry" , enquiry);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Enquiry Details");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
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
        View view= inflater.inflate(R.layout.fragment_enquiry_details, container, false);

        initalizations(view);
        setCustomerData();
       /* adapterConstructionTeam=new AdapterConstructionTeam(teamList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        team_view.setLayoutManager(horizontalLayoutManagaer);
        team_view.setAdapter(adapterConstructionTeam);
*/

        return  view;
    }

    private void initalizations(View view)
    {
        profile_image=(CircleImageView)view.findViewById(R.id.profileImage);
        place_image=(ImageView)view.findViewById(R.id.placeImage);
        customer_name=(TextView)view.findViewById(R.id.customerName);
        mobile_number=(TextView)view.findViewById(R.id.mobileNumber);
        village_name=(TextView)view.findViewById(R.id.villageName);
        customer_age=(TextView)view.findViewById(R.id.customerAge);
        aadhar_number=(TextView)view.findViewById(R.id.aadharNumber);
        place_image=(ImageView)view.findViewById(R.id.placeImage);
        roof_type=(TextView)view.findViewById(R.id.roofType);
        house_type=(TextView)view.findViewById(R.id.houseType);
        kitchen_height=(TextView)view.findViewById(R.id.kitchenHeight);
        enquiry_date=(TextView)view.findViewById(R.id.enquiryDate);
        enquiry_by=(TextView)view.findViewById(R.id.enquiryBy);
    }

    private void setCustomerData()
    {
        customer_name.setText(String.valueOf(enquiry.getName()));
        mobile_number.setText(String.valueOf(enquiry.getMobile()));
        village_name.setText("Village Name");
        customer_age.setText(String.valueOf("Age: "+enquiry.getAge()));
        aadhar_number.setText(String.valueOf(enquiry.getAdharid()));
        roof_type.setText(String.valueOf(enquiry.getRoofType()));
        house_type.setText(String.valueOf(enquiry.getHousetype()));
        kitchen_height.setText(String.valueOf(enquiry.getHieght()+" Ft"));
        enquiry_date.setText(String.valueOf(enquiry.getAddeddate()));
        enquiry_by.setText(String.valueOf(enquiry.getName()));

        Glide.with(getActivity())
                .load(enquiry.getImagepath())
                .error(R.drawable.profile)
                .into(profile_image);

        Glide.with(getActivity())
                .load(enquiry.getPlace_image())
                .error(R.drawable.plannedarea)
                .into(place_image);



        if(enquiry.getState().equalsIgnoreCase(Enquiry.CONSTRUCTION_COMPLITED))
        {

        }
    }
}
