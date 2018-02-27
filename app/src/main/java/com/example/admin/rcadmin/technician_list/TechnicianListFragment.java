package com.example.admin.rcadmin.technician_list;

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

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.technician_list.adapter.TechnicianListAdapter;
import com.example.admin.rcadmin.technician_list.apihelper.TechnicianList_ApiHelper;
import com.example.admin.rcadmin.add_technician.model.Technician;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class TechnicianListFragment extends Fragment {

    private RecyclerView technicianRecyclerView;
    private TechnicianListAdapter technicianListAdapter;
    private ArrayList<Technician> technicianListArrayList;
    private SweetAlertDialog sweetAlertDialog;
    private PrefManager prefManager;

    public TechnicianListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_technician_list, container, false);
        initializations(view);
        setEnquiryArrayList();
        return view;
    }

    private void initializations(View view)
    {
        prefManager=new PrefManager(getActivity());
        technicianRecyclerView=(RecyclerView)view.findViewById(R.id.technician_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        technicianRecyclerView.setLayoutManager(layoutManager);

        technicianRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        technicianRecyclerView.setItemAnimator(new DefaultItemAnimator());
        technicianRecyclerView.setAdapter(technicianListAdapter);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    private void getTechnicianListFromServer()
    {
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_marathi));
            sweetAlertDialog.setTitleText(getResources().getString(R.string.ok_marathi));
        }
        else
        {
            sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_english));
            sweetAlertDialog.setTitleText(getResources().getString(R.string.ok_english));
        }
        sweetAlertDialog.show();


        TechnicianList_ApiHelper.technicianListApi(getActivity(), technicianListArrayList, new ApiResultListener() {
            @Override
            public void onSuccess(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.dismissWithAnimation();
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                technicianListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_english));
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


    private  void setEnquiryArrayList()
    {
        technicianListArrayList=new ArrayList<Technician>();
        getTechnicianListFromServer();
        technicianListAdapter = new TechnicianListAdapter(getContext(),technicianListArrayList,technicianRecyclerView);
        technicianRecyclerView.setAdapter(technicianListAdapter);
    }
}
