package com.example.admin.rcadmin.enquiry.apihelper;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.rcadmin.app.MyApplication;
import com.example.admin.rcadmin.constants.WebServiceUrls;
import com.example.admin.rcadmin.enquiry.listener.ApiResultListener;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Admin_WebApiHelper {


    public static boolean customerEnquiryAPi(final Activity activity, final ArrayList<Enquiry> enquirieslist, final String enquirType, final ApiResultListener listner)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceUrls.urlCustomerEnquiry, new Response.Listener<String>() {
            @Override
            public void onResponse(String apiresult)
            {

                try {

                    JSONObject response = new JSONObject(apiresult);
                    String message=response.getString("message");

                    if (response.getString("status").equalsIgnoreCase("Success"))
                    {
                        if(response.getString("message").equalsIgnoreCase("Result foud"))
                        {

                            JSONArray result = response.getJSONArray("result");

                            if(result.length()>0) {
                            for (int i = 0; i < result.length(); i++) {

                                Enquiry enquiry=new Enquiry();

                                JSONObject jsonObject = result.getJSONObject(i);

                                enquiry.setId(jsonObject.getString("id"));
                                enquiry.setKitchen_id(jsonObject.getString("kitchen_id"));
                                enquiry.setRoofType(jsonObject.getString("roofType"));
                                enquiry.setHousetype(jsonObject.getString("housetype"));
                                enquiry.setHieght(jsonObject.getString("hieght"));
                                enquiry.setLongitude(jsonObject.getString("longitude"));
                                enquiry.setLatitude(jsonObject.getString("latitude"));
                                enquiry.setGeoaddress(jsonObject.getString("geoaddress"));
                                enquiry.setPlace_image(jsonObject.getString("place_image"));
                                enquiry.setAddeddate(jsonObject.getString("addeddate"));
                                enquiry.setCostofculha(jsonObject.getString("costofculha"));
                                enquiry.setCustomerid(jsonObject.getString("customer_id"));
                                enquiry.setState(jsonObject.getString("state"));
                                enquiry.setStep1image(jsonObject.getString("step1image"));
                                enquiry.setStep2image(jsonObject.getString("step2image"));
                                enquiry.setUpdatedate(jsonObject.getString("updatedate"));
                                enquiry.setAddedbyid(jsonObject.getString("addedbyid"));
                                enquiry.setStime(jsonObject.getString("stime"));
                                enquiry.setEndtime(jsonObject.getString("endtime"));
                                enquiry.setAdminactiondate(jsonObject.getString("adminactiondate"));
                                enquiry.setComment(jsonObject.getString("comment"));
                                enquiry.setCustomerid(jsonObject.getString("customerid"));
                                enquiry.setAdharid(jsonObject.getString("adharid"));
                                enquiry.setName(jsonObject.getString("name"));
                                enquiry.setAddress(jsonObject.getString("address"));
                                enquiry.setAge(jsonObject.getString("age"));
                                enquiry.setGender(jsonObject.getString("gender"));
                                enquiry.setMobile(jsonObject.getString("mobile"));
                                enquiry.setVillageid(jsonObject.getString("villageid"));
                                enquiry.setCiti_id(jsonObject.getString("citi_id"));
                                enquiry.setUploaddate(jsonObject.getString("uploaddate"));
                                enquiry.setImagepath(jsonObject.getString("imagepath"));

                                enquirieslist.add(enquiry);
                            }
                            listner.onSuccess(message);

                        }

                            else
                            {
                                    listner.onError(message);
                            }
                        }
                        else
                        {
                            listner.onError(message);
                        }

                    }
                    else
                    {
                        listner.onError(message);
                        //sweetAlertDialog.dismissWithAnimation();
                    }
                } catch (JSONException e) {
                    //sweetAlertDialog.dismissWithAnimation();
                    listner.onError("JSON Error");
                }
            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //sweetAlertDialog.dismissWithAnimation();

                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                {
                    listner.onError("Timeout Error");

                }
                else if (error instanceof ServerError)
                {
                    listner.onError("Server Error");

                }
                else if (error instanceof NetworkError)
                {
                    listner.onError("Network Error");
                }
                else if (error instanceof ParseError)
                {
                    listner.onError("Parse Error");
                }
                else
                {
                    listner.onError("Error");
                }
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                //returning parameters

                params.put("format","json");
                params.put("mobile",new PrefManager(activity).getMobile());
                params.put("password",new PrefManager(activity).getPassword());
                if(enquirType!=null)
                {
                    params.put("state", enquirType);
                }
                else
                {
                    params.put("state","");
                }
                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(strReq);
        return true;
    }

}

