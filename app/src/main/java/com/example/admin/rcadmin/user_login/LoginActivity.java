package com.example.admin.rcadmin.user_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.rcadmin.activity.MainActivity;
import com.example.admin.rcadmin.R;
import com.example.admin.rcadmin.constants.AppConstants;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.user_login.apihelper.Login_ApiHelper;

import com.example.admin.rcadmin.user_login.listener.LoginListner;
import com.example.admin.rcadmin.user_login.model.LoginUser;
import com.example.admin.rcadmin.utils.Utility;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.admin.rcadmin.constants.AppConstants.MARATHI;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText edtName, edtPassword;
    private PrefManager prefManager;
    private SweetAlertDialog sweetAlertDialog;
    private SweetAlertDialog localityDialog;
    private ApiResultListener listener;
    private TextView text_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();
        setLanguageToUI();
        clickListners();

    }

    private void initialization() {
        prefManager = new PrefManager(this);
        edtName = (EditText) findViewById(R.id.input_username);
        edtPassword = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.loginbtn);
        text_login=(TextView)findViewById(R.id.textLogin);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }
    }

    private void setLanguageToUI() {
        if (prefManager.getLanguage().equalsIgnoreCase(MARATHI)) {

            text_login.setText(getResources().getString(R.string.login_marathi));
            text_login.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 12));

            edtName.setHint(getResources().getString(R.string.username_marathi));
            edtName.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 8));
            edtName.setHintTextColor(getResources().getColor(R.color.DarkGrey));

            edtPassword.setHint(getResources().getString(R.string.password_marathi));
            edtPassword.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 8));
            edtPassword.setHintTextColor(getResources().getColor(R.color.DarkGrey));

            login.setText(getResources().getString(R.string.login_marathi));
            login.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 12));

        } else {
            text_login.setText(getResources().getString(R.string.login_english));
            text_login.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 12));

            edtName.setHint(getResources().getString(R.string.username_english));
            edtName.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 8));
            edtName.setHintTextColor(getResources().getColor(R.color.DarkGrey));

            edtPassword.setHint(getResources().getString(R.string.password_english));
            edtPassword.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 8));
            edtPassword.setHintTextColor(getResources().getColor(R.color.DarkGrey));

            login.setText(getResources().getString(R.string.login_english));
            login.setTextSize(Utility.getConvertFloatToDP(LoginActivity.this, 10));

        }

    }

    private void clickListners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    setUserInfo();
                    sweetAlertDialog =new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                        sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_marathi));
                    }
                    else
                    {
                        sweetAlertDialog.setTitleText(getResources().getString(R.string.please_wait_english));
                    }

                    sweetAlertDialog.show();
                    LoginUser loginUser = new LoginUser();
                    Login_ApiHelper.userLoginApi(LoginActivity.this, loginUser);
                    getloginEvent(loginUser);
                }
            }
        });

    }

    private void getloginEvent(LoginUser loginUser)
    {
        loginUser.setOnLoginEvent(new LoginListner() {
            @Override
            public void onLogin_Success()
            {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_successfully_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_successfully_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        localityDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                            localityDialog.setTitleText(getResources().getString(R.string.please_wait_marathi));
                        }
                        else
                        {
                            localityDialog.setTitleText(getResources().getString(R.string.please_wait_english));
                        }
                        localityDialog.show();
                        localityDialog.dismissWithAnimation();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        sweetAlertDialog.dismissWithAnimation();



                    }
                });

            }

            @Override
            public void onLogin_Failed() {
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI))
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_failed_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else{
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_failed_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }

                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_Response_Failed() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI))
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_failed_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else{
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_failed_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }

                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_Json_Error() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_error_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.login_error_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));

                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_No_Connection_Error() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.check_internet_connection_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.check_internet_connection_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_Server_Error() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.server_error_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.server_error_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_Network_Error() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.network_error_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.network_error_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_Parse_Error() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.parse_error_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.parse_error_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
                }
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });

            }

            @Override
            public void onLogin_Unknown_Error() {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.unknown_error_marathi));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_marathi));
                }
                else
                {
                    sweetAlertDialog.setTitleText(getResources().getString(R.string.unknown_error_english));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.ok_english));
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

    private void setUserInfo() {
        prefManager.setUserName(edtName.getText().toString());
        prefManager.setPassword(edtPassword.getText().toString());
    }

    private boolean checkValidation() {
        if (edtName.getText().toString().equalsIgnoreCase("")) {

            if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_username_marathi), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_username_english), Toast.LENGTH_LONG).show();
            }

            return false;
        } else if (edtPassword.getText().toString().equalsIgnoreCase("")) {
            if(prefManager.getLanguage().equalsIgnoreCase(AppConstants.MARATHI)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_password_marathi), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_password_english), Toast.LENGTH_LONG).show();
            }
            return false;
        } else {
            return true;
        }
    }




}