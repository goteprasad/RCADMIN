package com.example.admin.rcadmin.enquiry.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.activity.MainActivity;
import com.example.admin.rcadmin.enquiry.EnquiryDetailsFragment;
import com.example.admin.rcadmin.enquiry.OnLoadMoreListener;
import com.example.admin.rcadmin.enquiry.model.Enquiry;

import java.util.ArrayList;


public class EnquiryListAdapter extends RecyclerView.Adapter{


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private Context context;
    private ArrayList<Enquiry> enquiryArrayList;
    private FragmentTransaction fragmentTransaction;
    Enquiry enquiry;


    public EnquiryListAdapter(Context context,ArrayList<Enquiry> enquiryArrayList,RecyclerView recyclerView) {
        this.context = context;
        this.enquiryArrayList = enquiryArrayList;


        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }


    @Override
    public int getItemViewType(int position) {
        return enquiryArrayList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.row_customer, viewGroup, false);

            vh = new EnquiryViewHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar_item, viewGroup, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EnquiryViewHolder) {
            final Enquiry enquiry = enquiryArrayList.get(position);
            ((EnquiryViewHolder) holder).customerName.setText(String.valueOf(enquiry.getName() + ""));
            ((EnquiryViewHolder) holder).customerMobile.setText(String.valueOf(enquiry.getMobile() + ""));
            ((EnquiryViewHolder) holder).customerVillage.setText("Village Name");
            ((EnquiryViewHolder) holder).customerAge.setText(String.valueOf("Age: " + enquiry.getAge() + ""));


            ((EnquiryViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*Intent intent=new Intent(context, MainActivity.class);
                intent.putExtra("Enquiry",enquiry);
                context.startActivity(intent);
*/
                    fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    EnquiryDetailsFragment enquiryDetailsFragment = EnquiryDetailsFragment.getInstance(enquiry);
                    fragmentTransaction.replace(R.id.frameLayout, enquiryDetailsFragment).addToBackStack(null).commit();

                }
            });

        }

        else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }


    public void setLoaded() {
        loading = false;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
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


     class EnquiryViewHolder extends RecyclerView.ViewHolder
     {

        TextView customerName,customerMobile,customerVillage,customerAge;

        View view;

        public EnquiryViewHolder(View view)
        {
            super(view);

            customerName = (TextView) itemView.findViewById(R.id.customer_name);
            customerMobile = (TextView) itemView.findViewById(R.id.customer_mobile);
            customerVillage = (TextView) itemView.findViewById(R.id.customer_village);
            customerAge = (TextView) itemView.findViewById(R.id.customer_age);

            this.view = view;
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


}