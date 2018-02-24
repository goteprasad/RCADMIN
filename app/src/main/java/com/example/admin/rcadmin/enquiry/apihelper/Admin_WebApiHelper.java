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
import com.example.admin.rcadmin.enquiry.model.Team;
import com.example.admin.rcadmin.listener.ApiResultListener;
import com.example.admin.rcadmin.enquiry.model.Enquiry;
import com.example.admin.rcadmin.pref_manager.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;



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

                            if(result.length()>0)
                            {
                                for (int i = 0; i < result.length(); i++) {

                                Enquiry enquiry=new Enquiry();


                                JSONObject jsonObjectResult = result.getJSONObject(i);

                                enquiry.setCityname(jsonObjectResult.getString("cityname"));
                                enquiry.setId(jsonObjectResult.getString("id"));
                                enquiry.setCustomerid(jsonObjectResult.getString("customerid"));
                                enquiry.setAdharid(jsonObjectResult.getString("adharid"));
                                enquiry.setName(jsonObjectResult.getString("name"));
                                enquiry.setAddress(jsonObjectResult.getString("address"));
                                enquiry.setAge(jsonObjectResult.getString("age"));
                                enquiry.setGender(jsonObjectResult.getString("gender"));
                                enquiry.setMobile(jsonObjectResult.getString("mobile"));
                                enquiry.setVillageid(jsonObjectResult.getString("villageid"));
                                enquiry.setCiti_id(jsonObjectResult.getString("citi_id"));
                                enquiry.setAdded_date(jsonObjectResult.getString("added_date"));
                                enquiry.setUploaddate(jsonObjectResult.getString("uploaddate"));
                                enquiry.setUpdatedate(jsonObjectResult.getString("updatedate"));
                                enquiry.setAddedbyid(jsonObjectResult.getString("addedbyid"));
                                enquiry.setImagepath(jsonObjectResult.getString("imagepath"));
                                enquiry.setKitchen_id(jsonObjectResult.getString("kitchen_id"));
                                enquiry.setRoofType(jsonObjectResult.getString("roofType"));
                                enquiry.setHousetype(jsonObjectResult.getString("housetype"));
                                enquiry.setHieght(jsonObjectResult.getString("hieght"));
                                enquiry.setLongitude(jsonObjectResult.getString("longitude"));
                                enquiry.setLatitude(jsonObjectResult.getString("latitude"));
                                enquiry.setGeoaddress(jsonObjectResult.getString("geoaddress"));
                                enquiry.setPlace_image(jsonObjectResult.getString("place_image"));
                                enquiry.setAddeddate(jsonObjectResult.getString("addeddate"));
                                enquiry.setCostofculha(jsonObjectResult.getString("costofculha"));
                                enquiry.setCustomerid(jsonObjectResult.getString("customer_id"));
                                enquiry.setState(jsonObjectResult.getString("state"));
                                enquiry.setStep1image(jsonObjectResult.getString("step1image"));
                                enquiry.setStep2image(jsonObjectResult.getString("step2image"));
                                enquiry.setUpdateDate(jsonObjectResult.getString("updateDate"));
                                enquiry.setAddedby_id(jsonObjectResult.getString("addedby_id"));
                                enquiry.setStime(jsonObjectResult.getString("stime"));
                                enquiry.setEndtime(jsonObjectResult.getString("endtime"));
                                enquiry.setAdminactiondate(jsonObjectResult.getString("adminactiondate"));
                                enquiry.setComment(jsonObjectResult.getString("comment"));
                                enquiry.setVillagename(jsonObjectResult.getString("villagename"));


                                JSONArray teamArray = jsonObjectResult.getJSONArray("team");
                                ArrayList<Team> teamArrayList =new ArrayList<>();
                                for (int j = 0; j < teamArray.length(); j++) {
                                    Team team = new Team();

                                    JSONObject jsonObjectTeam=teamArray.getJSONObject(j);

                                    team.setId(jsonObjectTeam.getString("id"));
                                    team.setKechain_id(jsonObjectTeam.getString("kechain_id"));
                                    team.setTechnitionid(jsonObjectTeam.getString("technitionid"));
                                    team.setCustomerid(jsonObjectTeam.getString("customerid"));
                                    team.setStarttime(jsonObjectTeam.getString("starttime"));
                                    team.setEndtime(jsonObjectTeam.getString("endtime"));
                                    team.setAddedby_id(jsonObjectTeam.getString("addedby_id"));
                                    team.setName(jsonObjectTeam.getString("name"));
                                    team.setMobile(jsonObjectTeam.getString("mobile"));
                                    team.setDob(jsonObjectTeam.getString("dob"));
                                    team.setGender(jsonObjectTeam.getString("gender"));
                                    team.setAddress(jsonObjectTeam.getString("address"));
                                    team.setAddedby(jsonObjectTeam.getString("addedby"));
                                    team.setAddeddate(jsonObjectTeam.getString("addeddate"));
                                    team.setVillageid(jsonObjectTeam.getString("villageid"));


                                    teamArrayList.add(team);
                                }
                                enquiry.setTeamArrayList(teamArrayList);

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
                    params.put("state","N");
                }
                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(strReq);
        return true;
    }
}

