package com.example.admin.rcadmin.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.pref_manager.PrefManager;


public class AboutFragment extends Fragment {

    private PrefManager prefManager;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        prefManager=new PrefManager(getActivity());
        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI))
        {
            view= inflater.inflate(R.layout.fragment_about_marathi, container, false);
        }
        else
        {
            view= inflater.inflate(R.layout.fragment_about_english, container, false);
        }

        return view;
    }

}
