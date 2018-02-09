package com.example.admin.rcadmin.enquiry.apihelper;

import android.app.Activity;

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

/**
 * Created by Nikam on 08/02/2018.
 */

public class UpdateEnquiry_ApiHelper {

    public static boolean updateCustomerEnquiryAPi(final Activity activity, final ArrayList<Enquiry> enquirieslist, final String enquirType, final ApiResultListener listner)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceUrls.urlUpdateEnquiry, new Response.Listener<String>() {
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

                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(strReq);
        return true;
    }
}
