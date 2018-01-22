package com.example.admin.rcadmin.enquiry.adapter;


import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.enquiry.EnquiryDetailsFragment;
import com.example.admin.rcadmin.enquiry.model.Enquiry;

import java.util.ArrayList;


public class EnquiryListAdapter extends RecyclerView.Adapter<EnquiryListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Enquiry> enquiryArrayList;
    FragmentTransaction fragmentTransaction;

    public EnquiryListAdapter(Context context,ArrayList<Enquiry> enquiryArrayList) {
        this.context = context;
        this.enquiryArrayList = enquiryArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_customer, viewGroup, false);
        EnquiryListAdapter.ViewHolder viewHolder = new EnquiryListAdapter.ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EnquiryListAdapter.ViewHolder holder, int position) {
        final Enquiry enquiry = enquiryArrayList.get(position);
        holder.customerName.setText(String.valueOf(enquiry.getName() + ""));
        holder.customerMobile.setText(String.valueOf(enquiry.getMobile() + ""));
        holder.customerVillage.setText("Village Name");
        holder.customerAge.setText(String.valueOf(enquiry.getAge()+ ""));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction =((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                EnquiryDetailsFragment enquiryDetailsFragment =EnquiryDetailsFragment.getInstance(enquiry);
                fragmentTransaction.replace(R.id.frameLayout, enquiryDetailsFragment).commit();

            }
        });

    }

    @Override
    public int getItemCount()
    {
        try {
            return enquiryArrayList.size();
        } catch (Exception e) {
            return 0;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView customerName,customerMobile,customerVillage,customerAge;

        View view;

        public ViewHolder(View view)
        {
            super(view);

            customerName = (TextView) itemView.findViewById(R.id.customer_name);
            customerMobile = (TextView) itemView.findViewById(R.id.customer_mobile);
            customerVillage = (TextView) itemView.findViewById(R.id.customer_village);
            customerAge = (TextView) itemView.findViewById(R.id.customer_age);

            this.view = view;
        }
    }
}