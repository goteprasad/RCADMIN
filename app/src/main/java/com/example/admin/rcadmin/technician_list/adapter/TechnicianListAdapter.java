package com.example.admin.rcadmin.technician_list.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.add_technician.TeamDetailsFragment;
import com.example.admin.rcadmin.add_technician.model.Technician;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import java.util.ArrayList;

/**
 * Created by Nikam on 15/02/2018.
 */

public class TechnicianListAdapter extends RecyclerView.Adapter<TechnicianListAdapter.ViewHolder>{


    private Context context;
    private ArrayList<Technician> technicianListArrayList;
    private Technician technicianList;
    private TechnicianListAdapter technicianListAdapter;
    private PrefManager prefManager;

    public TechnicianListAdapter(Context context, ArrayList<Technician> technicianListArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.technicianListArrayList = technicianListArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_technician, viewGroup, false);
        TechnicianListAdapter.ViewHolder viewHolder = new TechnicianListAdapter.ViewHolder(v);
        context = viewGroup.getContext();

        prefManager=new PrefManager(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TechnicianListAdapter.ViewHolder holder, int position) {
        final Technician technicianList = technicianListArrayList.get(position);

        holder.technicianName.setText(String.valueOf(technicianList.getTechName() + ""));
        holder.technicianAddress.setText(String.valueOf(technicianList.getAddress() + ""));

        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
            holder.technicianMobile.setText(String.valueOf(context.getResources().getString(R.string.mobile_number_marathi) + technicianList.getMobile() + ""));
        }
        else
        {
            holder.technicianMobile.setText(String.valueOf(context.getResources().getString(R.string.mobile_number_english) + technicianList.getMobile() + ""));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                TeamDetailsFragment teamDetailsFragment = TeamDetailsFragment.newInstance(technicianList);
                fragmentTransaction.replace(R.id.frameLayout, teamDetailsFragment).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return technicianListArrayList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView technicianName,technicianMobile,technicianAddress;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);

            technicianName = (TextView) itemView.findViewById(R.id.technician_name);
            technicianAddress = (TextView) itemView.findViewById(R.id.technician_address);
            technicianMobile = (TextView) itemView.findViewById(R.id.technician_mobile);

            this.itemView = itemView;
        }
    }
}

