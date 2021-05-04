package com.example.mega2.final_livw_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Class_selector extends AppCompatActivity {
    private SessionHandler sessionmain;
    private ArrayList<String> session1,dept1,class1,sub1;
    private JSONArray result,resultdept,resultclass,resultsub,blobcount;
    Spinner spsession, spdept, spclass, spsubject;
    MyPojo_main pojo = new MyPojo_main();
    String sess, dept, clss,subj,userid,JURLFetchSess = pojo.JURLFetchSess.toString(),JURLFetchDept = pojo.JURLFetchDept.toString(),JURLFetchClass = pojo.JURLFetchClass.toString(),JURLFetchSub = pojo.JURLFetchSub.toString(),JURLgetBCount = pojo.JURLgetBCount.toString(),JURLgetBlobData = pojo.JURLgetBlobData.toString(),JURLsetUserDetails = pojo.JURLsetUserDetails.toString(),JURLsetMasterDetails = pojo.JURLsetMasterDetails.toString(),JURLsetPunchDetails = pojo.JURLsetPunchDetails.toString();
    Button btnNext;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Session = "sessKey";
    public static final String Dept = "deptKey";
    public static final String Class = "classKey";
    public static final String Subject = "subjectKey";
    SharedPreferences sharedpreferences;
    FBUpload upload1 = new FBUpload();
    Db_Helper_main db = new Db_Helper_main(Class_selector.this);
    DB_MySQL_Helper dbm = new DB_MySQL_Helper();
    MFS100_final_main mfs = new MFS100_final_main();
    ProgressDialog progressDialog;
    private ProgressBar pgsBar;
    int bcount,bcount1,count1,count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //JURL2 = pojo.getURL();
        Toast.makeText(getApplicationContext(),JURLFetchSess,Toast.LENGTH_LONG).show();
        sessionmain = new SessionHandler(getApplicationContext());
        User user = sessionmain.getUserDetails();
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_class_selector);
        session1 = new ArrayList<String>();
        dept1 = new ArrayList<String>();
        class1 = new ArrayList<String>();
        sub1 = new ArrayList<String>();
        spsession = (Spinner) findViewById(R.id.spinnerSession);
        spdept = (Spinner) findViewById(R.id.spinnerDept);
        spclass = (Spinner) findViewById(R.id.spinnerClass);
        spsubject = (Spinner) findViewById(R.id.spinnerSubject);
        btnNext = (Button) findViewById(R.id.buttonMarkAttendance);
        userid = user.getUsername();
        spsession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Long.toString(id);
                sess = spsession.getSelectedItem().toString();
                dept1.clear();
                getDept(userid,sess);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        getData(userid);
        spdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                dept = spdept.getSelectedItem().toString();
                class1.clear();
                getClassMain(userid,sess,dept);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //getDept(userid,sess);
        spclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                clss = spclass.getSelectedItem().toString();
                sub1.clear();
                getSubject(userid,sess,dept,clss);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spsubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                subj = spclass.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vmode ="OF";
                sess = spsession.getSelectedItem().toString();
                dept = spdept.getSelectedItem().toString();
                clss = spclass.getSelectedItem().toString();
                subj = spsubject.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),"sess - "+ sess + " dept - "+ dept +" class -" +clss+" subj -" +subj,Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Session, sess);
                editor.putString(Dept, dept);
                editor.putString(Class, clss);
                editor.putString(Subject, subj);
                editor.commit();
                //Toast.makeText(getApplicationContext(),"Getting - sess - "+ GD.getmSession() + " dept - "+ GD.getmDept() +" class -" +GD.getmClass(),Toast.LENGTH_LONG).show();
                //My Implementation Starts Here
                try {
                    //Thread.sleep(10000);
                                /*
                                mfs.WriteLog("Going to Execute MySQL");
                                int count1 = dbm.getBlobCount(sess,dept,clss);
                                if(count1==0)
                                {
                                    mfs.WriteLog("DBM Zero Count = "+count1);
                                }
                                else {
                                    mfs.WriteLog("DBM No Zero Count = "+count1);
                                }
                                */

                    //new getBlobCount().execute(sess,dept,clss);
                    //My New Implementation starts
                    new setUserDetail().execute(clss);
                    new setMasterDetail().execute(clss);
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(new Date());
                    new setPunchDetail().execute(subj,date);
                    //My New Implementation ends
                    mfs.WriteLog("Going to Execute getBlobData");
                    new getBlobData().execute(sess,dept,clss);
                                /*

                                int count = db.getBlobCount(sess,dept,clss);
                                if(count==0)
                                {
                                    mfs.WriteLog("Zero Count = "+count);
                                }
                                else
                                {
                                    mfs.WriteLog("No Zero Count = "+count);
                                    mfs.WriteLog("Receive ID");
                                    int[] id = db.getBlobID(sess,dept,clss);
                                    for (int i=0;i<count; i++)
                                    {
                                        mfs.WriteLog("Received ID = "+id[i]);
                                        final MyPojo_main pojo = db.retrieveISO(id[i],sess,dept,clss);
                                        byte[] FData = new byte[1000];
                                        String FName;
                                        FData = pojo.getFData();
                                        FName = pojo.getFileName();
                                        WriteFile(FName,FData);
                                        mfs.WriteLog("All Data Restored");
                                    }
                                }
                                */
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Class_selector.this, Mark_Attendance.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

                    /*
            int count = db.getBlobCount(sess,dept,clss);
                    if(count==0)
                    {
                        mfs.WriteLog("Zero Count = "+count);
                    }
                    else
                    {
                        mfs.WriteLog("No Zero Count = "+count);
                        mfs.WriteLog("Receive ID");
                        int[] id = db.getBlobID(sess,dept,clss);
                        for (int nID : id)
                        {
                            mfs.WriteLog("Received ID = "+nID);
                        }
                    }

                    progressDialog.dismiss();
*/
                //My Implementation Ends Here



            }

        });
    }

    public void WriteFile(String filename, byte[] bytes) {
        try {
            mfs.WriteLog("getblobdata WriteFile Class Selector--17");
            String path = Environment.getExternalStorageDirectory() + "//Megamind//Finger_data";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + "//" + filename;
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(bytes);
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void WriteImage(String filename, byte[] imagedata) {
        try {
            mfs.WriteLog("Inside WriteImage Starts");
            String path = Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + "//" + filename;
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            mfs.WriteLog("ImagePath = " +path);
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(imagedata);
            fos.flush();
            fos.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    //getBloBCount
    /*
    private boolean mHasReceivedData = false;
    public int getBlobCount(String sess,String dept,String class1){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,JURLgetBCount+"?sess="+ sess +"&dept="+dept+"&class="+class1,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            blobcount = j.getJSONArray(Config.JSON_ARRAY);
                            bcount = getBCount(blobcount);

                            mfs.WriteLog("inside response bcount = "+bcount);
                            mHasReceivedData = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        mfs.WriteLog("Returning bcount from getBlobCount = "+bcount);
        return bcount;
    }

    public int getBCount(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                //session1.add(json.getString(Config.TAG_BLOBCOUNT));
                bcount1 = json.getInt(Config.TAG_BLOBCOUNT);
                mfs.WriteLog("inside response bcount1 = "+bcount1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //spsession.setAdapter(new ArrayAdapter<String>(Class_selector.this, R.layout.spinner_item_session, session1));

        mfs.WriteLog("Returning bcount from getBCount = "+bcount1);
        pojo.setBlobCount(bcount1);
        return bcount1;
    }
*/
    private void getData(String teacherid){
        StringRequest stringRequest = new StringRequest(JURLFetchSess+"?qid=0&tid="+teacherid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(Config.JSON_ARRAY);
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                session1.add(json.getString(Config.TAG_SESSIONNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spsession.setAdapter(new ArrayAdapter<String>(Class_selector.this, R.layout.spinner_item_session, session1));
    }


    //Department
    private void getDept(String teacherid,String sess){
        StringRequest stringRequest = new StringRequest(JURLFetchDept+"?qid=0&tid="+teacherid+"&sess="+sess,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultdept = j.getJSONArray(Config.JSON_ARRAY_DEPT);
                            getDepartments(resultdept);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDepartments(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                dept1.add(json.getString(Config.TAG_DEPARTMENTNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spdept.setAdapter(new ArrayAdapter<String>(Class_selector.this, R.layout.spinner_item_department, dept1));
    }
    //Class
    private void getClassMain(String teacherid,String sess,String dept){
        StringRequest stringRequest = new StringRequest(JURLFetchClass+"?qid=0&tid="+teacherid+"&sess="+sess+"&dept="+dept,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultclass = j.getJSONArray(Config.JSON_ARRAY_CLASS);
                            getClassSub(resultclass);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getClassSub(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                class1.add(json.getString(Config.TAG_CLASSNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spclass.setAdapter(new ArrayAdapter<String>(Class_selector.this, R.layout.spinner_item_class, class1));
    }

    //Subject
    private void getSubject(String teacherid,String sess,String dept,String Class){
        StringRequest stringRequest = new StringRequest(JURLFetchSub+"?qid=0&tid="+teacherid+"&sess="+sess+"&dept="+dept+"&class="+Class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultsub= j.getJSONArray(Config.JSON_ARRAY_SUBJECT);
                            getSubjectSub(resultsub);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getSubjectSub(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                sub1.add(json.getString(Config.TAG_SUBNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spsubject.setAdapter(new ArrayAdapter<String>(Class_selector.this, R.layout.spinner_item_subject, sub1));
    }

    private class getBlobData extends AsyncTask<String, String, String>
    {
        Integer i=0;
        @Override
        protected String doInBackground(String... params) {
            try {
                final String sess1 = params[0];
                final String dept1 = params[1];
                final String class1 = params[2];



                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();

                params_final.put("Session", sess1);
                params_final.put("Department", dept1);
                params_final.put("Class", class1);

                mfs.WriteLog("Inside getBlobData********* ");
                mfs.WriteLog(sess1 + dept1 + class1);
                pm.put("punchinfo", params_final);
                mfs.WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLgetBlobData, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    mfs.WriteLog("Inside Response of getBlobData**//**/ ");
                                    try {
                                        JSONArray result = response.getJSONArray("result");

                                        for(i=0;i<result.length();i++)
                                        {
                                            JSONObject c = result.getJSONObject(i);
                                            //mfs.WriteLog("Received ID = "+id[i]);
                                            //final MyPojo_main pojo = db.retrieveISO(id[i],sess,dept,clss);
                                            //byte[] FData = new byte[1000];
                                            byte[] decodedString = Base64.decode(c.getString("blobdata"), Base64.DEFAULT);
                                            //byte[] decodedString =  c.getString("blobdata").getBytes(Charset.forName("UTF-8"));
                                            String filename = c.getString("blobname");
                                            String str = new String(decodedString);
                                            mfs.WriteLog("Iteration - "+i+" byte - "+str);
                                            mfs.WriteLog("Iteration - "+i+" filename - "+filename);
                                            /*
                                            String FName;
                                            FData = pojo.getFData();
                                            FName = pojo.getFileName();
                                            */
                                            WriteFile(filename,decodedString);

                                            mfs.WriteLog("All Data Restored from MySQL DB");
                                        }

                                        /*
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateDbPunch(syncData.getUser().trim(),syncData.getDate(),syncData.getTime(),"Y");
                                            mfs.WriteLog("Responce Received from JSON PUNCH - "+response.toString());
                                        }
                                        else {
                                            mfs.WriteLog("Inside Else Responce Received from JSON PUNCH - " + response.toString());
                                        }
                                        */
                                    }
                                    catch (Exception e)
                                    {
                                        mfs.WriteLog("Decode exception thrown");
                                        e.printStackTrace();
                                    }

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
            return null;

        }
    }

    private class setUserDetail extends AsyncTask<String, String, String>
    {
        Integer i=0;
        @Override
        protected String doInBackground(String... params) {
            try {
                final String Class = params[0];
                //final String dept1 = params[1];
                //final String class1 = params[2];



                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();

                params_final.put("Class", Class);
                //params_final.put("Department", dept1);
                //params_final.put("Class", class1);

                mfs.WriteLog("Inside setFilename********* ");
                mfs.WriteLog(Class);
                pm.put("punchinfo", params_final);
                mfs.WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLsetUserDetails, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    mfs.WriteLog("Inside Response of setUserDetails**//**/ ");
                                    try {
                                        JSONArray result = response.getJSONArray("result");

                                        for(i=0;i<result.length();i++)
                                        {
                                            JSONObject c = result.getJSONObject(i);
                                            String enrollment = c.getString("enrollment");
                                            String hand = c.getString("hand");
                                            String finger = c.getString("finger");
                                            String filename = c.getString("file");
                                            String fingerquality = c.getString("fingerquality");
                                            String Class = c.getString("Class");
                                            db.insData(enrollment,hand,finger,filename,fingerquality,Class);
                                            //mfs.WriteLog("Received ID = "+id[i]);
                                            //final MyPojo_main pojo = db.retrieveISO(id[i],sess,dept,clss);
                                            //byte[] FData = new byte[1000];
                                            //byte[] decodedString = Base64.decode(c.getString("blobdata"), Base64.DEFAULT);
                                            //byte[] decodedString =  c.getString("blobdata").getBytes(Charset.forName("UTF-8"));
                                            //String str = new String(decodedString);
                                            //WriteLog("Iteration - "+i+" byte - "+str);
                                            mfs.WriteLog("Iteration - "+i+" filename - "+filename+" enroll - "+enrollment+" hand - "+hand+" finger - "+finger+" fingerQual - "+fingerquality);
                                            /*
                                            String FName;
                                            FData = pojo.getFData();
                                            FName = pojo.getFileName();
                                            */
                                            //WriteFile(filename,decodedString);

                                            mfs.WriteLog("All Data Restored from MySQL DB");
                                        }

                                        /*
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateDbPunch(syncData.getUser().trim(),syncData.getDate(),syncData.getTime(),"Y");
                                            mfs.WriteLog("Responce Received from JSON PUNCH - "+response.toString());
                                        }
                                        else {
                                            mfs.WriteLog("Inside Else Responce Received from JSON PUNCH - " + response.toString());
                                        }
                                        */
                                    }
                                    catch (Exception e)
                                    {
                                        mfs.WriteLog("Decode exception thrown");
                                        e.printStackTrace();
                                    }

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
            return null;

        }
    }

    private class setMasterDetail extends AsyncTask<String, String, String>
    {
        Integer i=0;
        @Override
        protected String doInBackground(String... params) {
            try {
                final String Class = params[0];
                //final String dept1 = params[1];
                //final String class1 = params[2];



                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();

                params_final.put("Class", Class);
                //params_final.put("Department", dept1);
                //params_final.put("Class", class1);

                mfs.WriteLog("Inside setFilename********* ");
                mfs.WriteLog(Class);
                pm.put("punchinfo", params_final);
                mfs.WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLsetMasterDetails, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    mfs.WriteLog("Inside Response of setMasterDetails**//**/ ");
                                    try {
                                        JSONArray result = response.getJSONArray("result");

                                        for(i=0;i<result.length();i++)
                                        {
                                            JSONObject c = result.getJSONObject(i);
                                            String enrollment = c.getString("EnrollmentNo");
                                            String StudentName = c.getString("StudentName");
                                            String Session = c.getString("Session");
                                            String Department = c.getString("Department");
                                            String Class = c.getString("Class");
                                            String Subject = c.getString("Subject");
                                            String Password = c.getString("Password");
                                            String MobileNo = c.getString("MobileNo");
                                            String Email = c.getString("Email");
                                            String RollNo = c.getString("RollNo");
                                            String StudentID = c.getString("StudentID");
                                            String Address = c.getString("Address");
                                            String Gender = c.getString("Gender");
                                            String Expiry = c.getString("Expiry");
                                            String Date_of_birth = c.getString("Date_of_birth");
                                            String Is_sync = c.getString("Is_sync");
                                            String Image = c.getString("Image");
                                            byte[] decodedString = Base64.decode(c.getString("ImageBlob"), Base64.DEFAULT);
                                            if(c.getString("ImageBlob")!=null) {
                                                WriteImage(Image, decodedString);
                                            }
                                            db.insert_USER_Data(enrollment, StudentName, Session, Department, Class, Password, MobileNo, Email, RollNo, StudentID, Address, Gender, Date_of_birth, Image, decodedString);
                                            //mfs.WriteLog("Received ID = "+id[i]);
                                            //final MyPojo_main pojo = db.retrieveISO(id[i],sess,dept,clss);
                                            //byte[] FData = new byte[1000];
                                            //byte[] decodedString = Base64.decode(c.getString("blobdata"), Base64.DEFAULT);
                                            //byte[] decodedString =  c.getString("blobdata").getBytes(Charset.forName("UTF-8"));
                                            //String str = new String(decodedString);
                                            //WriteLog("Iteration - "+i+" byte - "+str);
                                            //mfs.WriteLog("Iteration - "+i+" filename - "+filename+" enroll - "+enrollment+" hand - "+hand+" finger - "+finger+" fingerQual - "+fingerquality);
                                            /*
                                            String FName;
                                            FData = pojo.getFData();
                                            FName = pojo.getFileName();
                                            */
                                            //WriteFile(filename,decodedString);

                                            mfs.WriteLog("Image Written Succesfully");
                                        }

                                        /*
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateDbPunch(syncData.getUser().trim(),syncData.getDate(),syncData.getTime(),"Y");
                                            mfs.WriteLog("Responce Received from JSON PUNCH - "+response.toString());
                                        }
                                        else {
                                            mfs.WriteLog("Inside Else Responce Received from JSON PUNCH - " + response.toString());
                                        }
                                        */
                                    }
                                    catch (Exception e)
                                    {
                                        mfs.WriteLog("Decode exception thrown");
                                        e.printStackTrace();
                                    }

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
            return null;

        }
    }

    private class setPunchDetail extends AsyncTask<String, String, String>
    {
        Integer i=0;
        @Override
        protected String doInBackground(String... params) {
            try {
                final String Subject = params[0];
                final String Date = params[1];
                //final String class1 = params[2];
                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();

                params_final.put("Subject", Subject);
                params_final.put("Date", Date);
                //params_final.put("Department", dept1);
                //params_final.put("Class", class1);

                mfs.WriteLog("Inside setFilename********* ");
                mfs.WriteLog(Subject);
                pm.put("punchinfo", params_final);
                mfs.WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLsetPunchDetails, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    mfs.WriteLog("Inside Response of setMasterDetails**//**/ ");
                                    try {
                                        JSONArray result = response.getJSONArray("result");

                                        for(i=0;i<result.length();i++)
                                        {
                                            JSONObject c = result.getJSONObject(i);
                                            String enrollment = c.getString("EnrollmentNo");
                                            String Session = c.getString("Session");
                                            String Department = c.getString("Department");
                                            String Class = c.getString("Class");
                                            String Subject = c.getString("Subject");
                                            String Device_info = c.getString("Device_info");
                                            String Date = c.getString("Date");
                                            String Time = c.getString("Time");
                                            String FILE = c.getString("FILE");
                                            String Is_Sync = c.getString("Is_Sync");
                                            db.insertData(enrollment,Session,Department,Class,Subject,Device_info,Date,Time,FILE,Is_Sync);
                                            //mfs.WriteLog("Received ID = "+id[i]);
                                            //final MyPojo_main pojo = db.retrieveISO(id[i],sess,dept,clss);
                                            //byte[] FData = new byte[1000];
                                            //byte[] decodedString = Base64.decode(c.getString("blobdata"), Base64.DEFAULT);
                                            //byte[] decodedString =  c.getString("blobdata").getBytes(Charset.forName("UTF-8"));
                                            //String str = new String(decodedString);
                                            //WriteLog("Iteration - "+i+" byte - "+str);
                                            //mfs.WriteLog("Iteration - "+i+" filename - "+filename+" enroll - "+enrollment+" hand - "+hand+" finger - "+finger+" fingerQual - "+fingerquality);
                                            /*
                                            String FName;
                                            FData = pojo.getFData();
                                            FName = pojo.getFileName();
                                            */
                                            //WriteFile(filename,decodedString);

                                            mfs.WriteLog("All Data Restored from MySQL DB");
                                        }

                                        /*
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateDbPunch(syncData.getUser().trim(),syncData.getDate(),syncData.getTime(),"Y");
                                            mfs.WriteLog("Responce Received from JSON PUNCH - "+response.toString());
                                        }
                                        else {
                                            mfs.WriteLog("Inside Else Responce Received from JSON PUNCH - " + response.toString());
                                        }
                                        */
                                    }
                                    catch (Exception e)
                                    {
                                        mfs.WriteLog("Decode exception thrown");
                                        e.printStackTrace();
                                    }

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
            return null;

        }
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