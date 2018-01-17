package com.example.admin.rcadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class EnquiryDetails extends AppCompatActivity {

    private RecyclerView team_view;
    private ArrayList<ModelConstructionTeam> teamList;
    private AdapterConstructionTeam adapterConstructionTeam;

    static String[] nameArray = {"Prasad Gote", "Vishal Patil", "Poonam Deshmukh", "Priyanka Beldar"};
    static Integer[] drawableArray = {R.drawable.ic_user, R.drawable.ic_user, R.drawable.ic_user, R.drawable.ic_user};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_details);
        team_view = (RecyclerView) findViewById(R.id.construction_team);

        teamList = new ArrayList<ModelConstructionTeam>();
        for (int i = 0; i < nameArray.length; i++) {
            teamList.add(new ModelConstructionTeam(
                    nameArray[i],
                    drawableArray[i]
            ));
        }
        adapterConstructionTeam=new AdapterConstructionTeam(teamList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(EnquiryDetails.this, LinearLayoutManager.HORIZONTAL, false);
        team_view.setLayoutManager(horizontalLayoutManagaer);
        team_view.setAdapter(adapterConstructionTeam);
    }

}
