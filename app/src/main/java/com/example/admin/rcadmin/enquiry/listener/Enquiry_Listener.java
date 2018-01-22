package com.example.admin.rcadmin.enquiry.listener;



public interface Enquiry_Listener
{
    void onEnquiry_Add_Success();

    void onEnquiry_Add_Failed();

    void onEnquiry_Add_Response_Failed();

    void onEnquiry_Add_Json_Error();

    void onEnquiry_Add_No_Connection_Error();

    void onEnquiry_Add_Server_Error();

    void onEnquiry_Add_Network_Error();

    void onEnquiry_Add_Parse_Error();

    void onEnquiry_Add_Unknown_Error();

}
