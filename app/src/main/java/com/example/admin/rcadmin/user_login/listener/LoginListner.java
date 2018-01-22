package com.example.admin.rcadmin.user_login.listener;


public interface LoginListner
{
    void onLogin_Success();

    void onLogin_Failed();

    void onLogin_Response_Failed();

    void onLogin_Json_Error();

    void onLogin_No_Connection_Error();

    void onLogin_Server_Error();

    void onLogin_Network_Error();

    void onLogin_Parse_Error();

    void onLogin_Unknown_Error();
}
