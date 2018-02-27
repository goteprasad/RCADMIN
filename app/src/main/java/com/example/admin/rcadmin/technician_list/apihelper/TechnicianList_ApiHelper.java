package com.example.admin.rcadmin.technician_list.apihelper;

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
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.pref_manager.PrefManager;
import com.example.admin.rcadmin.add_technician.model.Technician;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Nikam on 15/02/2018.
 */

public class TechnicianList_ApiHelper {

    public static boolean technicianListApi(final Activity activity, final ArrayList<Technician> technicianListArrayList, final ApiResultListener listner)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceUrls.urlTechnicianList, new Response.Listener<String>() {
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

                                    Technician technicianList=new Technician();

                                    JSONObject jsonObject = result.getJSONObject(i);

/*
                                    {
                                        "status": "Success",
                                            "count": 1,
                                            "type": "getTeamList",
                                            "result": [
                                        {
                                            "tech_id": "1",
                                                "techName": "name",
                                                "mobile": "9975294783",
                                                "dob": "0000-00-00",
                                                "gender": "M",
                                                "address": "sadasda",
                                                "addedby": "723hsgd",
                                                "addeddate": "2018-02-13 19:42:01",
                                                "id": "1",
                                                "name": "abad",
                                                "city_id": "1",
                                                "lang": "0",
                                                "lat": "0"
                                        }
  ],
                                        "message": "Result foud"
                                    }*/
                                    technicianList.setTech_id(jsonObject.getString("tech_id"));
                                    technicianList.setTechName(jsonObject.getString("techName"));
                                    technicianList.setMobile(jsonObject.getString("mobile"));
                                    technicianList.setDob(jsonObject.getString("dob"));
                                    technicianList.setGender(jsonObject.getString("gender"));
                                    technicianList.setAddress(jsonObject.getString("address"));
                                    technicianList.setAddedby(jsonObject.getString("addedby"));
                                    technicianList.setAddeddate(jsonObject.getString("addeddate"));
                                    technicianList.setCity_id(jsonObject.getString("city_id"));
                                    technicianList.setLang(jsonObject.getString("lang"));
                                    technicianList.setLat(jsonObject.getString("lat"));

                                    technicianListArrayList.add(technicianList);
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

                /*customerid=adfdfafddsd
                &format=json
                &mobile=9975294782
                &password=user@123
                &limit=7
                &start=0*/

                params.put("format","json");
                params.put("mobile",new PrefManager(activity).getMobile());
                params.put("password",new PrefManager(activity).getPassword());
                params.put("customerid",new PrefManager(activity).getUserId());
                params.put("limit","");
                params.put("start","");


                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(strReq);
        return true;
    }
}
