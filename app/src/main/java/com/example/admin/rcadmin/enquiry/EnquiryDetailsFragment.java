package com.example.admin.rcadmin.enquiry;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.rcadmin.AdapterConstructionTeam;
import com.example.admin.rcadmin.ModelConstructionTeam;
import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.enquiry.model.Enquiry;


import java.util.ArrayList;


public class EnquiryDetailsFragment extends Fragment {

    private RecyclerView team_view;
    private ArrayList<ModelConstructionTeam> teamList;
    private AdapterConstructionTeam adapterConstructionTeam;

    private Enquiry enquiry;

    public static EnquiryDetailsFragment getInstance(Enquiry enquiry)
    {
        EnquiryDetailsFragment fragment = new EnquiryDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Enquiry" , enquiry);
        fragment.setArguments(args);
        return fragment;

    }
    public EnquiryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enquiry_details, container, false);

        adapterConstructionTeam=new AdapterConstructionTeam(teamList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        team_view.setLayoutManager(horizontalLayoutManagaer);
        team_view.setAdapter(adapterConstructionTeam);

        return  view;
    }


}
