package com.example.admin.rcadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class EnquiryActivity extends AppCompatActivity {

    private Toolbar toolbarenq;
    String[] name = new String[] { "Prasad Gote",
            "Vishal Landepatil", "Poonam Deshmukh", "Priyanka Beldar" };

    String[] mobile = new String[] {
            "9860402800",
            "9975294782", "9922436485",
            "9156957198" };

    String[] village = new String[] {
            "Aurangabad",
            "Aurangabad", "Aurangabad",
            "Aurangabad" };

    String[] age = new String[] {
            "27 Yrs",
            "25 Yrs", "27 Yrs",
            "25 Yrs" };

    Integer[] images = { R.drawable.ic_user,
            R.drawable.ic_user, R.drawable.ic_user, R.drawable.ic_user };

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);

        toolbarenq = (Toolbar) findViewById(R.id.toolbar_enquiry);
        setSupportActionBar(toolbarenq);
        toolbarenq.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AdapterCustomer adapter = new
                AdapterCustomer(EnquiryActivity.this, name, mobile, village, age, images);
        listView=(ListView)findViewById(R.id.list_customers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*Toast.makeText(EnquiryActivity.this, "You Clicked at " +name[+ position], Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(EnquiryActivity.this,EnquiryDetails.class);
                startActivity(intent);

            }
        });

    }
}
