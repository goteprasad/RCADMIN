package com.example.admin.rcadmin.add_technician.apihelper;

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
import com.example.admin.rcadmin.add_technician.model.Technician;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Nikam on 14/02/2018.
 */

public class Technician_ApiHelper {

    public static void technicianAPi(final Activity activity, final Technician technician, final ApiResultListener listner)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceUrls.urlAddTechnician, new Response.Listener<String>() {
            @Override
            public void onResponse(String apiresult)
            {
                try {
                    JSONObject response = new JSONObject(apiresult);
                    String message=response.getString("message");
                    if (response.getString("status").equalsIgnoreCase("success"))
                    {
                        if(response.getString("message").equalsIgnoreCase("Technition Details added successfully"))
                        {
                            JSONObject result =response.getJSONObject("result");
                           /* {
                                "status": "success",
                                    "count": 1,
                                    "type": "addPaymentDetails",
                                    "result": {
                                "id": "1",
                                        "name": "name",
                                        "mobile": "9975294783",
                                        "dob": "0000-00-00",
                                        "gender": "M",
                                        "address": "sadasda",
                                        "addedby": "723hsgd",
                                        "addeddate": "2018-02-13 19:42:01",
                                        "villageid": "1"
                            },
                                "message": "Technition Details added successfully"
                            }*/


                            //technician.setTech_id(result.getString("id"));
                            technician.setTechName(result.getString("name"));
                            technician.setMobile(result.getString("mobile"));
                            technician.setDob(result.getString("dob"));
                            technician.setGender(result.getString("gender"));
                            technician.setAddress(result.getString("address"));
                            technician.setAddedby(result.getString("addedby"));
                            technician.setAddeddate(result.getString("addeddate"));
                            technician.setVillageid(result.getString("villageid"));

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

                catch (JSONException e) {
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

                 /*http://hatchers.in/caravan/index.php/api/V1/AddCuntructionTechnition?

                format=json
                &mobile=9975294782
                &password=user@123
                &name=name
                &tmobile=9975294783
                &dob=12:12:2017
                &gender=M
                &address=sadasda
                &addedby=723hsgd
                &addeddate=5:7:2017
                &villageid=1*/


                params.put("format","json");
                params.put("mobile",new PrefManager(activity).getMobile());
                params.put("password",new PrefManager(activity).getPassword());
                params.put("tmobile",technician.getTmobile());
                params.put("name",technician.getTechName());
                params.put("dob",technician.getDob());
                params.put("gender",technician.getGender());
                params.put("address",technician.getAddress());
                params.put("addedby",technician.getAddedby());
                params.put("addeddate",technician.getAddeddate());
                params.put("villageid",technician.getVillageid());

                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(strReq);

       // return true;
    }
}
