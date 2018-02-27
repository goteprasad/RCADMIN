package com.example.admin.rcadmin.enquiry.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.enquiry.model.Team;

import java.util.ArrayList;

/**
 * Created by Nikam on 24/02/2018.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{


    private Context context;
    private ArrayList<Team> teamArrayList;
    private TeamAdapter teamAdapter;

    public TeamAdapter(Context context, ArrayList<Team> teamArrayList, RecyclerView recyclerView) {
        this.context = context;
        this.teamArrayList = teamArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_technician, viewGroup, false);
        TeamAdapter.ViewHolder viewHolder = new TeamAdapter.ViewHolder(v);
        context = viewGroup.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Team team = teamArrayList.get(position);

        holder.txtTechnicianName.setText(String.valueOf(team.getName() + ""));
        holder.txtTechnicianMobile.setText(String.valueOf(team.getMobile() + ""));
        holder.txtTechnicianAddress.setText(String.valueOf(team.getAddress() + ""));

    }

    @Override
    public int getItemCount() {
        try {
            return teamArrayList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtTechnicianName,txtTechnicianMobile,txtTechnicianAddress;

        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);

            txtTechnicianName = (TextView) itemView.findViewById(R.id.technician_name);
            txtTechnicianMobile = (TextView) itemView.findViewById(R.id.technician_mobile);
            txtTechnicianAddress = (TextView) itemView.findViewById(R.id.technician_address);

            this.itemView = itemView;

        }
    }
}
