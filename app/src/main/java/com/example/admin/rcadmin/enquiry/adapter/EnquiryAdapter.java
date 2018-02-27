package com.example.admin.rcadmin.enquiry.adapter;


import android.content.Context;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.rcadmin.R;

import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.enquiry.EnquiryDetailsFragment;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Enquiry> enquiryArrayList;
    private FragmentTransaction fragmentTransaction;
    private Enquiry enquiry;
    private EnquiryAdapter enquiryListAdapter;
    private PrefManager prefManager;


    public EnquiryAdapter(Context context, ArrayList<Enquiry> enquiryArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.enquiryArrayList = enquiryArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_customer, viewGroup, false);
        EnquiryAdapter.ViewHolder viewHolder = new EnquiryAdapter.ViewHolder(v);
        context = viewGroup.getContext();
        prefManager=new PrefManager(context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Enquiry enquiry = enquiryArrayList.get(position);

        holder.customer_name.setText(String.valueOf(enquiry.getName() + ""));
        holder.address.setText(String.valueOf(enquiry.getAddress() + ""));

        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            holder.mobile.setText(String.valueOf(context.getResources().getString(R.string.mobile_number_marathi) +enquiry.getMobile() + ""));
            holder.age.setText(String.valueOf(context.getResources().getString(R.string.age_marathi) + enquiry.getAge() + ""));
        }
        else
        {
            holder.mobile.setText(String.valueOf(context.getResources().getString(R.string.mobile_number_english) +enquiry.getMobile() + ""));
            holder.age.setText(String.valueOf(context.getResources().getString(R.string.age_english) + enquiry.getAge() + ""));
        }


       Glide.with(context).load(AppConstants.HOST_NAME+enquiry.getImagepath())
               .error(R.drawable.ic_user)
               .into(holder.customerImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction =((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                EnquiryDetailsFragment enquiryDetailsFragment =EnquiryDetailsFragment.getInstance(enquiry);
                fragmentTransaction.replace(R.id.frameLayout, enquiryDetailsFragment).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        try {
            return enquiryArrayList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView customer_name,address,mobile,age;
        CircleImageView customerImage;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);

            customer_name = (TextView) itemView.findViewById(R.id.customer_name);
            address = (TextView) itemView.findViewById(R.id.customer_address);
            mobile = (TextView) itemView.findViewById(R.id.customer_mobile);
            age = (TextView) itemView.findViewById(R.id.customer_age);
            customerImage=(CircleImageView) itemView.findViewById(R.id.customer_img);

            this.itemView = itemView;
        }
    }
}