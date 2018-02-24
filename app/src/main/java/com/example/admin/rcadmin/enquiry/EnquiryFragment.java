package com.example.admin.rcadmin.enquiry;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.enquiry.adapter.EnquiryAdapter;
import com.example.admin.rcadmin.enquiry.apihelper.Admin_WebApiHelper;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class EnquiryFragment extends Fragment {

    private RecyclerView list_CustomersRecyclerView;
    private EnquiryAdapter enquiryListAdapter;
    private ArrayList<Enquiry> enquiryArrayList;
    private SweetAlertDialog sweetAlertDialog;
    private PrefManager prefManager;


    protected Handler handler;

    String  enquiryType=Enquiry.NEW;

    public static EnquiryFragment getInstance(String enquiry)
    {
        EnquiryFragment fragment = new EnquiryFragment();
        Bundle args = new Bundle();
        args.putString("type" , enquiry);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            this.enquiryType=getArguments().getString("type");

        }
    }



    public EnquiryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enquiry, container, false);

        initializations(view);
        setEnquiryArrayList();

        return view;
    }

    private void initializations(View view)
    {
        list_CustomersRecyclerView=(RecyclerView)view.findViewById(R.id.listCustomers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list_CustomersRecyclerView.setLayoutManager(layoutManager);

        list_CustomersRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        list_CustomersRecyclerView.setItemAnimator(new DefaultItemAnimator());

        prefManager=new PrefManager(getActivity());

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    private void getEnquiryListFromServer()
    {
        sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            sweetAlertDialog.setConfirmText(getResources().getString(R.string.please_wait_marathi));
        }
        else
        {
            sweetAlertDialog.setConfirmText(getResources().getString(R.string.please_wait_english));
        }
        sweetAlertDialog.show();

        Admin_WebApiHelper.customerEnquiryAPi(getActivity(), enquiryArrayList,enquiryType, new ApiResultListener() {
            @Override
            public void onSuccess(String message) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText(message);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.please_wait_marathi));
                }
                else
                {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.please_wait_english));
                }
                sweetAlertDialog.dismissWithAnimation();
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
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.please_wait_marathi));
                }
                else
                {
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.please_wait_english));
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
        enquiryArrayList=new ArrayList<Enquiry>();
        getEnquiryListFromServer();
        enquiryListAdapter = new EnquiryAdapter(getContext(),enquiryArrayList,list_CustomersRecyclerView);
        list_CustomersRecyclerView.setAdapter(enquiryListAdapter);

    }

}