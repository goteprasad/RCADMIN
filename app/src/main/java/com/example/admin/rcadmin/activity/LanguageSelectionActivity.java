package com.example.admin.rcadmin.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.user_login.LoginActivity;


public class LanguageSelectionActivity extends AppCompatActivity
{
    private TextView englishTxt, marathiTxt;
    private PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        initializations();

        englishSelectedListener();

        marathiSelectedListener();

    }

    private void initializations()
    {
        prefManager=new PrefManager(LanguageSelectionActivity.this);
        englishTxt=(TextView)findViewById(R.id.english);
        marathiTxt=(TextView)findViewById(R.id.marathi);
    }

    private void englishSelectedListener()
    {
       englishTxt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
               prefManager.setLanguageSelectedTrue();
               prefManager.setLanguage(AppConstants.ENGLISH);
               checkIsLoggenIn();
           }
       });
    }

    private void marathiSelectedListener()
    {
        marathiTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                prefManager.setLanguageSelectedTrue();
                prefManager.setLanguage(AppConstants.MARATHI);
                checkIsLoggenIn();
            }
        });
    }

    private void checkIsLoggenIn()
    {
        Intent intent;
        if (prefManager.isLoggedIn())
        {
            intent=new Intent(LanguageSelectionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            intent=new Intent(LanguageSelectionActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
