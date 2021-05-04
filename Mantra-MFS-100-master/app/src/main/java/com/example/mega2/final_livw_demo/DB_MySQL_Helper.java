package com.example.mega2.final_livw_demo;

import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DB_MySQL_Helper extends AppCompatActivity {
    MFS100_final_main mfs = new MFS100_final_main();
    MyPojo_main pojo = new MyPojo_main();
    String JURLgetBCount= pojo.JURLgetBCount.toString();
    int count =0 ;


    Integer getBlobCount(String sess,String Dept,String Class) {
        try {

            final JSONObject pm = new JSONObject();

            JSONObject params_final = new JSONObject();
            params_final.put("sess", sess);
            params_final.put("Dept", Dept);
            params_final.put("Class", Class);
            /*
            params_final.put("DeviceID_Unique", syncData.getDeviceId_Unique());
            params_final.put("PunchMode", syncData.getPunchmode());
            params_final.put("Latitude", syncData.getLatitude());
            params_final.put("Longitude", syncData.getLongitude());
            params_final.put("MatchScore", syncData.getMatchscore());
            params_final.put("FingerQuality", syncData.getFingerquality());
            params_final.put("PhotoFileName", syncData.getPhotofilename());
            */
            mfs.WriteLog("########Inside DB MySQL HELPER##########");
            mfs.WriteLog(sess+Dept+Class);
                /*
                params_final.put("Field1", "vikash");
                params_final.put("Field2", "1111");
                params_final.put("Field3", "vikash");
                params_final.put("Field4", "1111");
                params_final.put("Field5", "vikash");
                */
            pm.put("punchinfo", params_final);

            if (isInternetOn()){
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLgetBCount , params_final,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                int len = (String.valueOf(response)).length();

                                try {
                                    if (response.getInt("status")==0)
                                    {
                                        //db.updateDb(syncData.getUser().trim(), syncData.getDate().trim(), syncData.getTime(),"Y");
                                        mfs.WriteLog("Response Received from JSON 1 Do in BG - "+response.toString());
                                        count = response.getInt("blob_count");
                                        mfs.WriteLog("BlobCount Returned - "+response.getInt("blob_count"));
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                    /*
                                    if ((String.valueOf((response.toString()).charAt(len - 3))).trim().toUpperCase().equals("Y")) {
                                        db.updateDb(syncData.getUser().trim(), syncData.getDate().trim(), syncData.getTime());
                                        WriteLog(response.toString());
                                    }*/
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mfs.WriteLog(error.toString());
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                req.setRetryPolicy(policy);
                requestQueue.add(req);
            }else mfs.WriteLog("Please connect to internet");
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return count;

    }
    public boolean isInternetOn() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }
}
