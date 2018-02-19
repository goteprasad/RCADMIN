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
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Nikam on 08/02/2018.
 */

public class UpdateEnquiry_ApiHelper {

    public static void updateTechnicianAPi(final Activity activity, final Enquiry enquiry, final ApiResultListener listner)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceUrls.urlUpdateEnquiry, new Response.Listener<String>() {
            @Override
            public void onResponse(String apiresult)
            {
                try {
                    JSONObject response = new JSONObject(apiresult);
                    String message=response.getString("message");

                    if (response.getString("status").equalsIgnoreCase("success"))
                    {
                        if(response.getString("message").equalsIgnoreCase("Enqury updated successfully"))
                        {
                            JSONObject result =response.getJSONObject("result");

                            /*{
                                "status": "success",
                                    "count": 1,
                                    "type": "updateInvity",
                                    "result": {
                                "id": "3",
                                        "kitchen_id": "KIT_180211095238238_2",
                                        "roofType": "5?>(@ ,>'2G2G",
                                        "housetype": "5?>(@ ,>'2G2G",
                                        "hieght": "55",
                                        "longitude": "75.3607941",
                                        "latitude": "19.8838358",
                                        "geoaddress": "Gulmohar Colony Rd, SBI Quarters, Savarkar Nagar, N 5, Cidco, Aurangabad, Maharashtra 431003, India\nMaharashtra\nAurangabad\nAurangabad\nnull",
                                        "place_image": "ed5afad95615e91179b83641120f8125/KIT_KIT_180211095238238_2.jpg",
                                        "addeddate": "2018-02-11 21:53:02",
                                        "costofculha": "",
                                        "customer_id": "CU_18021109520121_2",
                                        "state": "N",
                                        "step1image": "",
                                        "step2image": "",
                                        "updatedate": "2018-02-11 21:52:54",
                                        "addedbyid": "9975294782",
                                        "stime": "0000-00-00 00:00:00",
                                        "endtime": "0000-00-00 00:00:00",
                                        "adminactiondate": "2018-02-15 13:52:43",
                                        "comment": "sadsdasdad"
                            },
                                "message": "Enqury updated successfully"
                            }*/

                            enquiry.setId(result.getString("id"));
                            enquiry.setKitchen_id(result.getString("kitchen_id"));
                            enquiry.setRoofType(result.getString("roofType"));
                            enquiry.setHousetype(result.getString("housetype"));
                            enquiry.setHieght(result.getString("hieght"));
                            enquiry.setLongitude(result.getString("longitude"));
                            enquiry.setLatitude(result.getString("latitude"));
                            enquiry.setGeoaddress(result.getString("geoaddress"));
                            enquiry.setPlace_image(result.getString("place_image"));
                            enquiry.setAddeddate(result.getString("addeddate"));
                            enquiry.setCostofculha(result.getString("costofculha"));
                            enquiry.setCustomerid(result.getString("customer_id"));
                            enquiry.setState(result.getString("state"));
                            enquiry.setStep1image(result.getString("step1image"));
                            enquiry.setStep2image(result.getString("step2image"));
                            enquiry.setUpdatedate(result.getString("updatedate"));
                            enquiry.setAddedbyid(result.getString("addedbyid"));
                            enquiry.setStime(result.getString("stime"));
                            enquiry.setEndtime(result.getString("endtime"));
                            enquiry.setAdminactiondate(result.getString("adminactiondate"));
                            enquiry.setComment(result.getString("comment"));

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

                /*kitchen_id=KIT_180211095238238_2
                &state=N
                &format=json
                &mobile=9975294782
                &password=user@123
                &comment=sadsdasdad
                &customerid=CU_18021109520121_2*/

                params.put("kitchen_id",enquiry.getKitchen_id());
                params.put("state", "N");
                params.put("format","json");
                params.put("mobile",new PrefManager(activity).getMobile());
                params.put("password",new PrefManager(activity).getPassword());
                params.put("comment",enquiry.getComment());
                params.put("customerid",enquiry.getCustomerid());


                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(strReq);


    }
}
