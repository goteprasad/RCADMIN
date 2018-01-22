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

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.enquiry.adapter.EnquiryListAdapter;
import com.example.admin.rcadmin.enquiry.apihelper.Admin_WebApiHelper;
import com.example.admin.rcadmin.enquiry.listener.ApiResultListener;
import com.example.admin.rcadmin.enquiry.model.Enquiry;

import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class EnquiryFragment extends Fragment {

    private RecyclerView list_CustomersRecyclerView;
    private EnquiryListAdapter enquiryListAdapter;
    private ArrayList<Enquiry> enquiryArrayList;
    private SweetAlertDialog sweetAlertDialog;

    String  enquiryType=Enquiry.NEW;

    public void setEnquiryType(String enquiryType)
    {
        this.enquiryType=enquiryType;
        setEnquiryArrayList();
        getEnquiryListFromServer();
    }

    public EnquiryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enquiry, container, false);

        initiaizations(view);
        setEnquiryArrayList();

        return view;
    }

    private void initiaizations(View view)
    {
        list_CustomersRecyclerView=(RecyclerView)view.findViewById(R.id.listCustomers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list_CustomersRecyclerView.setLayoutManager(layoutManager);

        list_CustomersRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        list_CustomersRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void getEnquiryListFromServer()
    {
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Please wait");

        sweetAlertDialog.show();


        Admin_WebApiHelper.customerEnquiryAPi(getActivity(), enquiryArrayList,enquiryType, new ApiResultListener() {
            @Override
            public void onSuccess(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText(message);
                sweetAlertDialog.setConfirmText("Ok");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                enquiryListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText(""+message);
                sweetAlertDialog.setConfirmText("Ok");
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
        enquiryArrayList=new ArrayList<Enquiry>();
        enquiryListAdapter = new EnquiryListAdapter(getContext(),enquiryArrayList);
        list_CustomersRecyclerView.setAdapter(enquiryListAdapter);

    }

}
