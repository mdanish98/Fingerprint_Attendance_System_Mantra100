package com.example.mega2.final_livw_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MFS100_final_main extends AppCompatActivity implements MFS100Event{

    Button btnInit, btnUninit, btnStartCapture, btnStopCapture, btnSyncCapture, btnMatchISOTemplate, btnClearLog;
    TextView lblMessage, txtEventLog12, t;
    EditText txtEventLog;
    ImageView imgFinger;
    MyPojo_main pojo = new MyPojo_main();
    MyPojo_main pojo1 = new MyPojo_main();
    String Enroll_file2 = "hello";
    boolean b = false;
    byte[] Enroll_Template;
    byte[] Verify_Template;
    SharedPreferences settings;
    CommonMethod.ScannerAction scannerAction = CommonMethod.ScannerAction.Capture;
    int fin_cnt = 1, mfsVer = 41, minQuality = 50, timeout = 10000;
    String Key = "",sess,dept,class1,Subj;
    int Flag_NewFingerVarify=0;
    FingerData fingerData_tmp;
    int Quality_tmp=0;
    MFS100 mfs100 = null;
    sync_data_main syncData=new sync_data_main();
    String url_4= pojo.JURLDoBG.toString(),JURLInsertUser=pojo.JURLInsertUser.toString(),JURLInsertPunch=pojo.JURLInsertPunch.toString(),JURLInsBlobData=pojo.JURLInsBlobData.toString();
    Db_Helper_main db=new Db_Helper_main(MFS100_final_main.this);
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Session = "sessKey";
    public static final String Dept = "deptKey";
    public static final String Class = "classKey";
    public static final String Subject = "subjectKey";
    public static final String blobcount = "blobKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            this.setFinishOnTouchOutside(false);
            setContentView(R.layout.activity_mfs100_final);
            WriteLog("Oncreate");
            settings = PreferenceManager.getDefaultSharedPreferences(this);
            mfsVer = Integer.parseInt(settings.getString("MFSVer", String.valueOf(mfsVer)));
            FindFormControls();
            mfs100 = new MFS100(this, mfsVer, Key);
            mfs100.SetApplicationContext(MFS100_final_main.this);
            try {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                if (sharedpreferences.contains(Session)) {
                    sess = sharedpreferences.getString(Session, "");
                }
                if (sharedpreferences.contains(Dept)) {
                    dept = sharedpreferences.getString(Dept, "");
                }
                if (sharedpreferences.contains(Class)) {
                    class1 = sharedpreferences.getString(Class, "");
                }
                if (sharedpreferences.contains(Subject)) {
                    Subj = sharedpreferences.getString(Subject, "");
                }
                WriteLog(sess + " " + dept + " " + class1 + "");
                WriteLog("Inside Write File Data Base Insertion");
            }
            catch (Exception e)
            {
                WriteLog(e.getMessage());
            }

        }catch (Exception e)
        {
            WriteLog(e.getMessage());
        }

    }

    protected void onStop() {
        WriteLog("onStop--1");
        UnInitScanner();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        WriteLog("onDestroy--2");
        if (mfs100 != null) {
            mfs100.Dispose();
        }
        super.onDestroy();
    }

    public void FindFormControls() {
        WriteLog("FindFormControls--3");
        btnInit = (Button) findViewById(R.id.btnInit);
        btnUninit = (Button) findViewById(R.id.btnUninit);
        btnStartCapture = (Button) findViewById(R.id.btnStartCapture);
        btnStopCapture = (Button) findViewById(R.id.btnStopCapture);
        btnMatchISOTemplate = (Button) findViewById(R.id.btnMatchISOTemplate);
        btnClearLog = (Button) findViewById(R.id.btnClearLog);
        lblMessage = (TextView) findViewById(R.id.lblMessage);
        txtEventLog = (EditText) findViewById(R.id.txtEventLog);
        txtEventLog12 = (TextView) findViewById(R.id.txtEventLog12);
        imgFinger = (ImageView) findViewById(R.id.imgFinger);
        btnSyncCapture = (Button) findViewById(R.id.btnSyncCapture);
        t = (TextView) findViewById(R.id.detail);
    }

    public void onControlClicked(View v) {
        WriteLog("onControlClicked--4");
        switch (v.getId()) {
            case R.id.btnInit:
                WriteLog("onControlClicked--btnInit-5");
                InitScanner();
                break;
            case R.id.btnUninit:
                WriteLog("onControlClicked--btnUninit-6");
                UnInitScanner();
                break;
            case R.id.btnStartCapture:
                WriteLog("onControlClicked--btnStartCapture-7");
                scannerAction = CommonMethod.ScannerAction.Capture;
                StartAsyncCapture();
                break;
            case R.id.btnStopCapture:
                WriteLog("onControlClicked--btnStopCapture-8");
                StopAsynCapture();
                break;
            case R.id.btnSyncCapture:
                WriteLog("onControlClicked--btnSyncCapture-9");
                scannerAction = CommonMethod.ScannerAction.Capture;
                StartSyncCapture();
                break;
            case R.id.btnMatchISOTemplate:
                WriteLog("onControlClicked--btnMatchISOTemplate-10");
                scannerAction = CommonMethod.ScannerAction.Verify;
                StartAsyncCapture();
                break;
            case R.id.btnClearLog:
                WriteLog("onControlClicked--btnClearLog-11");
                ClearLog();
                break;
            default:
                WriteLog("onControlClicked--default-12");
                break;
        }
    }


    public void InitScanner() {
        try {
            WriteLog("InitScanner--13");
            int ret = mfs100.Init();
            if (ret != 0) {
                SetTextonuiThread(mfs100.GetErrorMsg(ret));
            } else {
                SetTextonuiThread("Init success");
                String info = "Serial: " + mfs100.GetDeviceInfo().SerialNo() + " Make: " + mfs100.GetDeviceInfo().Make() + " Model: " + mfs100.GetDeviceInfo().Model();
                SetLogOnUIThread(info);
            }
        } catch (Exception ex) {
            MyCustomToast.makeToast_mfs(getApplicationContext(),"Init failed ,unhandled exception");
            SetTextonuiThread("Init failed, unhandled exception");
        }
    }
    public void StartAsyncCapture() {
        WriteLog("StartAsyncCapture--14");
        SetTextonuiThread("");
        try {
            int ret = mfs100.StartCapture(minQuality, timeout, true);
            if (ret != 0) {
                SetTextonuiThread(mfs100.GetErrorMsg(ret));
            }
        } catch (Exception ex) {
            SetTextonuiThread("Error");
        }
    }

    public void StopAsynCapture() {
        mfs100.StopCapture();
    }

    public void StartSyncCapture() {
        WriteLog("StartSyncCapture--15");
        new Thread(new Runnable() {

            @Override
            public void run() {
                SetTextonuiThread("");
                try {
                    FingerData fingerData = new FingerData();
                    int ret = mfs100.AutoCapture(fingerData, minQuality, true,true);
                    if (ret != 0) {
                        SetTextonuiThread(mfs100.GetErrorMsg(ret));
                    } else {
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);
                        imgFinger.post(new Runnable() {
                            @Override
                            public void run() {
                                imgFinger.setImageBitmap(bitmap);
                                imgFinger.refreshDrawableState();
                            }
                        });
                        SetTextonuiThread("Quality: " + fingerData.Quality() + " NFIQ: " + fingerData.Nfiq());
                        SetLogOnUIThread("ISOTemplate size: " + String.valueOf(fingerData.ISOTemplate().length));
                        SetData(fingerData);
                    }
                } catch (Exception ex) {
                    SetTextonuiThread("Error");
                }
            }
        }).start();
    }

    public void UnInitScanner() {
        WriteLog("UnInitScanner--16");
        int ret = mfs100.UnInit();
        if (ret != 0) {
            SetTextonuiThread(mfs100.GetErrorMsg(ret));
        } else {
            SetTextonuiThread("Uninit Success");
        }
    }

    public void WriteFile(String filename, byte[] bytes) {
        try {
            WriteLog("SKIPPED WriteFile--17");
            String FileExt;
            String path = Environment.getExternalStorageDirectory() + "//Megamind//Finger_data";
            FileExt = filename.substring(filename.length() - 5);

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            WriteLog("bytes = " + bytes.toString());
            if(!FileExt.equals(".iso1")) {
                path = path + "//" + filename;

                file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream stream = new FileOutputStream(path);
                stream.write(bytes);
                stream.close();
            }
            /*
            path = path + "//" + filename;

            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(bytes);
            stream.close();
            */
            try {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                if (sharedpreferences.contains(Session)) {
                    sess = sharedpreferences.getString(Session, "");
                }
                if (sharedpreferences.contains(Dept)) {
                    dept = sharedpreferences.getString(Dept, "");
                }
                if (sharedpreferences.contains(Class)) {
                    class1 = sharedpreferences.getString(Class, "");
                }
                if (sharedpreferences.contains(Subject)) {
                    Subj = sharedpreferences.getString(Subject, "");
                }
                WriteLog(sess+" "+ dept +" "+ class1+"");
                WriteLog("Inside Write File Data Base Insertion");

                WriteLog("File Extension = "+FileExt+" File Name = "+filename);

                //new insBlobData().execute(bytes,sess,dept,class1);
                if(FileExt.equals(".iso1"))
                {
                    //byte[] encodedString = Base64.encode(bytes.toString().getBytes(), Base64.DEFAULT);
                    String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                    //String encodedString = new String(bytes, "UTF-8");
                    WriteLog("encoded string = "+encodedString);
                    pojo1.setFData1(encodedString);
                    pojo1.setSession(sess);
                    pojo1.setDepartment(dept);
                    pojo1.setClass1(class1);
                    pojo1.setFileName(filename);
                    new insBlobData().execute();

                long id = db.insBlobData(bytes,filename,sess,dept,class1);
                if (id < 0) {
                    WriteLog("BLOB WRITE UNSUCCESSFUL");
                } else {
                    WriteLog("BLOB WRITE SUCCESSFUL");
                }

                }
                else
                {
                    WriteLog("iso1 didnt matched");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                //Toast.makeText(getApplicationContext(),e1.toString(),Toast.LENGTH_LONG).show();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            //Toast.makeText(getApplicationContext(),e1.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void WriteLog(String data) {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.UK);
            String formattedDate = df.format(c.getTime());
            String date = new SimpleDateFormat("yyyyMMdd",Locale.UK).format(new Date());
            data = formattedDate + "->" + data + "\n";
            byte[] bytes = data.getBytes("UTF-8");
            String path = Environment.getExternalStorageDirectory() + "//Megamind//Logs";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + "//" + "Log_" + date + ".txt";
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path, true);
            stream.write(bytes);
            stream.close();
        } catch (Exception e1) {
            MyCustomToast.makeToast_mfs(MFS100_final_main.this, "Give Permission to Storage and Phone");
            e1.printStackTrace();
        }
    }

    protected void ClearLog() {
        WriteLog("ClearLog--18");
        txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.setText("", BufferType.EDITABLE);
            }
        });
    }

    protected void SetTextonuiThread(final String str) {
        lblMessage.post(new Runnable() {
            public void run() {
                lblMessage.setText(str, BufferType.EDITABLE);
            }
        });
    }
    protected void SetLogOnUIThread(final String str) {
        WriteLog("SetLogOnUIThread--20");
        txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.setText(txtEventLog.getText().toString() + "\n" + str, BufferType.EDITABLE);
            }
        });
    }


    @Override
    public void OnPreview(FingerData fingerData) {
        final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);
        imgFinger.post(new Runnable() {
            @Override
            public void run() {
                imgFinger.setImageBitmap(bitmap);
                imgFinger.refreshDrawableState();
            }
        });
        SetTextonuiThread("Quality: " + fingerData.Quality());
    }

    @Override
    public void OnCaptureCompleted(boolean status, int errorCode, String errorMsg, FingerData fingerData) {
        WriteLog("OnCaptureCompleted--23");
        try {
            if (status) {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0, fingerData.FingerImage().length);
                imgFinger.post(new Runnable() {
                    @Override
                    public void run() {
                        imgFinger.setImageBitmap(bitmap);
                        imgFinger.refreshDrawableState();
                    }
                });
                SetTextonuiThread("Quality: " + fingerData.Quality() + " NFIQ: " + fingerData.Nfiq());
                SetLogOnUIThread("ISOTemplate size1: " + String.valueOf(fingerData.ISOTemplate().length));
                Intent intent1=getIntent();
                if (intent1.hasExtra("main1") || intent1.hasExtra("login"))
                {
                    SetData(fingerData);
                }
                else
                {
                    if(Quality_tmp>0)
                    {
                        boolean match = match_2template(fingerData.ISOTemplate(),fingerData_tmp.ISOTemplate() );
                        if (!match)
                        {
                            fingerData =null;
                            fingerData_tmp =null ;
                            Quality_tmp =0;
                            t.post(new Runnable() {
                                @Override
                                public void run() {
                                    MyCustomToast.makeToast_mfs(getApplicationContext(),"Please ! put same finger..");
                                    text_to_speech.speech(getApplicationContext(),"Try again");
                                    dialogbox_opener();
                                }
                            });
                            return;
                        }
                    }
                    if (Flag_NewFingerVarify<=2 ) {
                        Flag_NewFingerVarify = Flag_NewFingerVarify + 1;
                        if (Quality_tmp < fingerData.Quality()) {
                            Quality_tmp = fingerData.Quality();
                            fingerData_tmp = null;
                            fingerData_tmp = fingerData;
                        }
                        if (Flag_NewFingerVarify > 2) {
                            fingerData = fingerData_tmp;
                            SetData(fingerData);
                        } else
                            t.post(new Runnable() {
                                @Override
                                public void run() {
                                    text_to_speech.speech(getApplicationContext(),"Put again");
                                    StartAsyncCapture();
                                }
                            });
                    }
                }
            } else {
                SetTextonuiThread(errorCode + "(" + errorMsg + ")");
                try {
                    Intent intent1=getIntent();
                    if(intent1.hasExtra("fname1"))
                    {
                        t.post(new Runnable() {
                            @Override
                            public void run() {
                                dialogbox_opener();
                            }
                        });
                    }
                    else {
                        Intent intent = new Intent(MFS100_final_main.this, Mark_Attendance.class);
                        finish();
                        startActivity(intent);
                    }
                }catch (Exception e)
                {
                    MyCustomToast.makeToast_mfs(getApplicationContext(),e.getMessage());
                }
            }
        }catch (Exception e)
        { MyCustomToast.makeToast_mfs(getApplicationContext(),e.getMessage());}

    }

    public void SetData(final FingerData fingerData) {
        WriteLog("SetData--24");
        try {
            final int quality = fingerData.Quality();
            if (scannerAction.equals(CommonMethod.ScannerAction.Capture)) {
                Varify_FileTemplate(fingerData.ISOTemplate() );
                if(!b) {
                    WriteLog("Inside Capture");
                    WriteLog("Capture done" + "25");
                    final String finger;
                    final Intent i1 = new Intent(this, Detail_main.class);
                    Enroll_Template = new byte[fingerData.ISOTemplate().length];
                    System.arraycopy(fingerData.ISOTemplate(), 0, Enroll_Template, 0, fingerData.ISOTemplate().length);
                    final String EnrollId;
                    finger = pojo.getFinger();
                    String Enroll_file = "";
                    Intent intent = getIntent();
                    WriteLog("SetData2" + "27");
                    if (intent.hasExtra("fname1") && fin_cnt < 10) {
                        pojo = (MyPojo_main) intent.getExtras().get("fname1");
                        assert pojo != null;
                        if (finger.equals("null")) {
                            Enroll_file = pojo.getEnrollment() + "_" + pojo.getHand() + "_" + fin_cnt;
                            pojo.setFinger(String.valueOf(fin_cnt));
                            fin_cnt++;
                        } else
                            Enroll_file = pojo.getEnrollment() + "_" + pojo.getHand() + "_" + pojo.getFinger();
                    }
                    EnrollId = Enroll_file + ".iso1";

                    WriteLog("SetData3" + "31");
                    t.post(new Runnable() {
                        @Override
                        public void run() {
                            WriteLog("SetData3" + "32");
                            long id = db.insData(pojo.getEnrollment(), pojo.getHand(), pojo.getFinger(), EnrollId, String.valueOf(quality),class1);
                            syncData.setuser(pojo.getEnrollment());
                            syncData.setHand(pojo.getHand());
                            syncData.setFinger(pojo.getFinger());
                            syncData.setFile(EnrollId);
                            syncData.setFingerquality(String.valueOf(quality));
                            syncData.setClass2(class1);
                            new updation_det().execute();
                            if (id < 0) {
                                MyCustomToast.makeToast_mfs(getApplicationContext(),"Unsuccessful");
                                text_to_speech.speech(getApplicationContext(), "Try again");
                            } else {
                                MyCustomToast.makeToast_mfs(getApplicationContext(),"Successfully Registered!!!!!");
                                text_to_speech.speech(getApplicationContext(), "Successfully, Registered");
                                if (fin_cnt == 10) {
                                    WriteLog("SETDATA" + "455");
                                    finish();
                                    startActivity(i1);
                                } else {
                                    WriteLog("SETDATA" + "451");
                                    dialogbox_opener();
                                }

                            }
                        }
                    });
                    WriteFile(EnrollId, fingerData.ISOTemplate());
                }
                else
                {
                    try {
                        t.post(new Runnable() {
                            @Override
                            public void run() {
                                MyCustomToast.makeToast_mfs(getApplicationContext(),"Finger already registered...");
                                text_to_speech.speech(getApplicationContext(), "Finger already registered");
                                dialogbox_opener();
                            }
                        });

                    }catch (Exception e)
                    {WriteLog(e.getMessage());
                    WriteLog("Exception void run part");}
                }
            } else if (scannerAction.equals(CommonMethod.ScannerAction.Verify)) {
                Verify_Template = new byte[fingerData.ISOTemplate().length];
                System.arraycopy(fingerData.ISOTemplate(), 0, Verify_Template, 0, fingerData.ISOTemplate().length);
                Varify_FileTemplate(Verify_Template);
                txtEventLog12.post(new Runnable() {
                    @Override
                    public void run() {
                        if (b) {
                            Intent in = new Intent(MFS100_final_main.this, Mark_Attendance.class);
                            if (getIntent().hasExtra("main1")) {
                                try {
                                    WriteLog("Inside Verify");
                                    //Toast.makeText(getApplicationContext(),"We are in try",Toast.LENGTH_LONG).show();
                                    final MyPojo_main pojo = db.getUserDatabyDetails(Enroll_file2);
                                    String deviceid = "MM" + mfs100.GetDeviceInfo().SerialNo();
                                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(new Date());
                                    String Time = new SimpleDateFormat("HH:mm:ss", Locale.UK).format(new Date());
                                    if (pojo.getEnrollment() != null) {
                                        //My code for valid class starts
                                        Intent ics = getIntent();
                                        String sess = ics.getStringExtra("sess3");
                                        String dept = ics.getStringExtra("dept3");
                                        String clss = ics.getStringExtra("clss3");
                                        String subj = ics.getStringExtra("subj3");
                                        WriteLog("Session = "+sess);
                                        WriteLog("Department = "+dept);
                                        WriteLog("Class = "+clss);
                                        WriteLog("Subject = "+subj);
                                        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                                            if (sharedpreferences.contains(Subject)) {
                                                Subj = sharedpreferences.getString(Subject, "");
                                        }
                                        final MyPojo_main pojo1 = db.getClassStatus(pojo.getEnrollment(),sess,dept,clss,subj);
                                        if (pojo1.getStatus() == 1) {
                                            final MyPojo_main pojo2 = db.getEnrollID(pojo.getEnrollment());
                                            //Toast.makeText(getApplicationContext(),"We are in try"+pojo.getEnrollment(),Toast.LENGTH_LONG).show();

                                            db.insertData(pojo.getEnrollment(), pojo.getSession(), pojo.getDepartment(), pojo.getClass1(),Subj, deviceid, date, Time,pojo2.getFile() ,"N");
                                            in.putExtra("mainActivity_mfs100Test_dialog_ok_main", pojo);
                                            syncData.setuser(pojo.getEnrollment());
                                            syncData.setDate_time(date + " " + Time);
                                            syncData.setDeviceId_HR(deviceid);
                                            syncData.setSession2(pojo.getSession());
                                            syncData.setDepartment2(pojo.getDepartment());
                                            syncData.setClass2(pojo.getClass1());
                                            syncData.setSubject2(Subj);
                                            syncData.setDeviceId_Unique("DVID1 ");
                                            syncData.setPunchmode("OF");
                                            syncData.setLatitude("LAT");
                                            syncData.setLongitude("LONG");
                                            syncData.setMatchscore("MTSC");
                                            syncData.setFingerquality(String.valueOf(quality));
                                            syncData.setPhotofilename(pojo.getFile());
                                            syncData.setFile(pojo2.getFile());
                                            syncData.setDate(date);
                                            syncData.setTime(Time);
                                            syncData.setSyncStat("Y");
                                            WriteLog("Entering Inside updation part********* ");
                                            new updation().execute();
                                            sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                                            String bcount = sharedpreferences.getString(blobcount, "");
                                            WriteLog("Bcount Data from SYNC"+bcount);
                                            new updation_punch().execute();
                                            finish();

                                        }//My status ends
                                        else if(pojo1.getStatus()==0)
                                        {
                                            in.putExtra("ClassCheck", pojo1);
                                        }
                                        else if(pojo1.getStatus()==2)
                                        {
                                            in.putExtra("AttendanceAlreadyMarked", pojo1);
                                        }

                                    }
                                        else {
                                            WriteLog("id null");
                                        }
                                        startActivity(in);
                                    } catch(Exception e){
                                        //Toast.makeText(getApplicationContext(),"We are here",Toast.LENGTH_LONG).show();
                                        MyCustomToast.makeToast_mfs(getApplicationContext(), e.getMessage());
                                    }
                            }
                            if (getIntent().hasExtra("login")) {
                                try {
                                    String n = db.getVData(Enroll_file2);
                                    if (n.toUpperCase().trim().equals("ADMIN")) {
                                        Intent I1 = new Intent(MFS100_final_main.this, SubActivity_Main.class);
                                        finish();
                                        startActivity(I1);
                                    } else {
                                        MyCustomToast.makeToast_mfs(getApplicationContext(), "Access Denied!!!!");
                                        finish();
                                        startActivity(in);
                                    }
                                } catch (Exception e) {
                                    MyCustomToast.makeToast_mfs(getApplicationContext(),e.getMessage());
                                }
                            }
                        }       else {
                            Intent intent = new Intent(MFS100_final_main.this, Mark_Attendance.class);
                            intent.putExtra("mainActivity_mfs100Test_dialog_main", "mainActivity_mfs100Test_dialog_main");
                            finish();
                            startActivity(intent);
                        }
                    }
                });

            }
        } catch (Exception e) {
            MyCustomToast.makeToast_mfs(getApplicationContext(),e.getMessage());
            WriteLog("Inside Exception ");
            e.printStackTrace();
        }

        WriteFile("Raw.raw", fingerData.RawData());
        WriteFile("Bitmap.bmp", fingerData.FingerImage());
        WriteFile("ISOTemplate.iso", fingerData.ISOTemplate());
        WriteFile("ISOImage.iso", fingerData.ISOImage());
        WriteFile("WSQ.wsq", fingerData.WSQImage());

    }


    public void dialogbox_opener() {
        try{

        WriteLog("dialogbox_opener");
        final ArrayList<String> theList1 = new ArrayList<>();
        WriteLog("dialogbox_opener-1");
        if (scannerAction.equals(CommonMethod.ScannerAction.Capture)) {
            WriteLog("dialogbox_opener-2");
            final View v = LayoutInflater.from(this).inflate(R.layout.finger_selector, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(MFS100_final_main.this);
            builder.setView(v);
            WriteLog("dialogbox_opener-3");
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
            WriteLog("dialogbox_opener-3-1");
            final RadioGroup finger_name, hand_type;
            final RadioButton hand_none, left_hand, right_hand;
            TextView ok, cancel, cancel_finger, id;
            GridView gridView;
            finger_name = (RadioGroup) v.findViewById(R.id.finger_type);
            hand_type = (RadioGroup) v.findViewById(R.id.linearfinger);
            hand_none = (RadioButton) v.findViewById(R.id.btnNA);
            left_hand = (RadioButton) v.findViewById(R.id.btnFF);
            ok = (TextView) v.findViewById(R.id.ok);
            cancel = (TextView) v.findViewById(R.id.cancel_finger);
            right_hand = (RadioButton) v.findViewById(R.id.right_hand);
            gridView = (GridView) v.findViewById(R.id.grid1);
            cancel_finger = (TextView) v.findViewById(R.id.textView);
            id = (TextView) v.findViewById(R.id.tv2);
            WriteLog("dialogbox_opener-4");
            Intent intent = getIntent();
            if (intent.hasExtra("fname1")) {
                pojo = (MyPojo_main) intent.getExtras().get("fname1");
                assert pojo != null;
                String uid = pojo.getEnrollment();
                id.setText("UID -" + uid + " & Name -" + pojo.getName());
                ArrayList<String> theList = new ArrayList<>();
                Cursor fin_data = db.getFinger_data(uid);
                fin_cnt = 0;
                if (fin_data.getCount() != 0) {
                    int i = 0;
                    fin_cnt = fin_data.getCount();
                    cancel_finger.setText(String.format("%s fingers Registered!!!!", String.valueOf(fin_cnt)));
                    if (fin_cnt >= 10) {
                        cancel_finger.setTextColor(Color.RED);
                        ok.setEnabled(false);
                    }
                    theList1.clear();
                    while (fin_data.moveToNext()) {
                        theList.add(fin_data.getString(0));
                        theList.add(fin_data.getString(1));
                        theList1.add(fin_data.getString(0) + "-" + fin_data.getString(1));
                        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                        gridView.setAdapter(listAdapter);
                        if (i == 0) {
                            i = 1;
                            if (fin_data.getString(0).equals("NONE")) {
                                left_hand.setEnabled(false);
                                right_hand.setEnabled(false);
                                hand_none.setChecked(true);
                                pojo.setHand("N");
                                for (int j = 0; j < finger_name.getChildCount(); j++) {
                                    finger_name.getChildAt(j).setEnabled(false);
                                }
                                pojo.setFinger("null");
                            } else {
                                for (int j = 0; j < finger_name.getChildCount(); j++) {
                                    finger_name.getChildAt(j).setEnabled(false);
                                }
                                hand_none.setEnabled(false);
                            }

                        }
                    }
                } else {
                    hand_none.setChecked(true);
                    pojo.setHand("N");
                    for (int j = 0; j < finger_name.getChildCount(); j++) {
                        finger_name.getChildAt(j).setEnabled(false);
                    }
                    pojo.setFinger("null");
                }

            }
            WriteLog("dialogbox_opener-5");
            hand_none.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < finger_name.getChildCount(); i++) {
                        finger_name.getChildAt(i).setEnabled(false);
                    }
                    pojo.setFinger("null");
                }
            });

            left_hand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < finger_name.getChildCount(); i++) {
                        finger_name.getChildAt(i).setEnabled(true);
                    }
                    for (int j = 0; j < theList1.size(); j++) {
                        if (theList1.get(j).equals("LEFT-THUMB")) {
                            finger_name.getChildAt(1).setEnabled(false);
                        } else if (theList1.get(j).equals("LEFT-INDEX")) {
                            finger_name.getChildAt(2).setEnabled(false);
                        } else if (theList1.get(j).equals("LEFT-MIDDLE")) {
                            finger_name.getChildAt(3).setEnabled(false);
                        } else if (theList1.get(j).equals("LEFT-RING")) {
                            finger_name.getChildAt(4).setEnabled(false);
                        } else if (theList1.get(j).equals("LEFT-LITTLE")) {
                            finger_name.getChildAt(5).setEnabled(false);
                        }
                    }
                }
            });

            right_hand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < finger_name.getChildCount(); i++) {
                        finger_name.getChildAt(i).setEnabled(true);
                    }
                    for (int j = 0; j < theList1.size(); j++) {
                        if (theList1.get(j).equals("RIGHT-THUMB")) {
                            finger_name.getChildAt(1).setEnabled(false);
                        } else if (theList1.get(j).equals("RIGHT-INDEX")) {
                            finger_name.getChildAt(2).setEnabled(false);
                        } else if (theList1.get(j).equals("RIGHT-MIDDLE")) {
                            finger_name.getChildAt(3).setEnabled(false);
                        } else if (theList1.get(j).equals("RIGHT-RING")) {
                            finger_name.getChildAt(4).setEnabled(false);
                        } else if (theList1.get(j).equals("RIGHT-LITTLE")) {
                            finger_name.getChildAt(5).setEnabled(false);
                        }
                    }
                }
            });
            hand_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    RadioButton btn = (RadioButton) v.findViewById(group.getCheckedRadioButtonId());
                    pojo.setHand(String.valueOf(btn.getText().charAt(0)));
                }
            });
            finger_name.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    RadioButton btn = (RadioButton) v.findViewById(group.getCheckedRadioButtonId());
                    pojo.setFinger(String.valueOf(btn.getText().charAt(0)));
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton btn = (RadioButton) dialog.findViewById(hand_type.getCheckedRadioButtonId());
                    assert btn != null;
                    try {
                        if ((btn.getText().equals("None")) || (finger_name.getCheckedRadioButtonId() > 0) && (hand_type.getCheckedRadioButtonId() > 0)) {
                            scannerAction = CommonMethod.ScannerAction.Capture;
                            Flag_NewFingerVarify=0;
                            Quality_tmp=0;
                            WriteLog("Ok clicked");
                            dialog.dismiss();
                            StartAsyncCapture();
                        } else {
                            MyCustomToast.makeToast_mfs(getApplicationContext(),"Select hand and finger both..");
                        }
                    }catch (Exception e)
                    {
                        MyCustomToast.makeToast_mfs(getApplicationContext(),"Select hand and finger..");
                        Log.e("Message",e.getMessage());
                    }

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    finish();
                    startActivity(new Intent(v.getContext(), Detail_main.class));
                }
            });


        } else {StartAsyncCapture();}
        }catch (Exception e)
        {
            WriteLog(e.toString());
        }
    }

    public void Varify_FileTemplate(byte[] Curr_Template) throws IOException {
        String path = Environment.getExternalStorageDirectory() + "//Megamind//Finger_data";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        b=false;
        Enroll_file2="hello";
        if (Arrays.toString(listOfFiles).equals("null"))
        {
            b=true;
        }else
            {
            for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".iso1")) {
                String path1 = path + "//" + file.getName();
                File file1 = new File(path1);
                int size = (int) file1.length();
                byte[] Enroll_Template = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file1));
                    buf.read(Enroll_Template, 0, Enroll_Template.length);
                    buf.close();
                    int ret = mfs100.MatchISO(Enroll_Template, Curr_Template);
                    if (ret < 0) {
                        WriteLog("Error: " + ret + "(" + mfs100.GetErrorMsg(ret) + ")");
                    } else {
                        if (ret >= 1400)
                            {
                                Enroll_file2 = file.getName();
                                b = true;
                                break;
                            }
                        else if (i == listOfFiles.length - 1)
                            {
                                WriteLog("Finger not matched");
                            }
                        else
                            {
                                WriteLog("Exception occured");
                            }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*
        WriteLog("######MATCHING STARTS WITH NEW IMPLEMENTATION##########");
        WriteLog("Problem After This");
        Db_Helper_main dbm = new Db_Helper_main(MFS100_final_main.this);
        //int count = dbm.getBlobCount();
        int count = 11;
        WriteLog("******************Count = "+count);
        b=false;
        Enroll_file2="hello";
        if (count==0)
        {
            b=true;
        }else
        {
            for (int i = 0; i < count; i++) {
                WriteLog("*******About to receive ISO");
                byte[] Enroll_Template = db.retrieveISO(i);
                WriteLog("*******About to receive ISO23");
                String filename = pojo.getFileName();
                String Extn;
                if(filename.length()>4)
                {
                    Extn = filename.substring(filename.length() - 5);
                    WriteLog("******************EXTN ISO = "+Extn);
                    WriteLog("******************Filename = "+filename);
                }
                else
                {
                    Extn= filename;
                }
                if (Extn.equals(".iso1")) {
                    try {
                        int ret = mfs100.MatchISO(Enroll_Template, Curr_Template);
                        if (ret < 0) {
                            WriteLog("Error: " + ret + "(" + mfs100.GetErrorMsg(ret) + ")");
                        } else {
                            if (ret >= 1400)
                            {
                                Enroll_file2 = filename;
                                b = true;
                                break;
                            }
                            else if (i == count-1)
                            {
                                WriteLog("Finger not matched");
                            }
                            else
                            {
                                WriteLog("Exception occured");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
*/

    }

    public boolean match_2template(byte[] Curr_Template,byte[] Enroll_Template){
        int ret = mfs100.MatchISO(Enroll_Template, Curr_Template);
        if (ret < 0) {
            return false;
        } else {
            return ret >= 1400;
        }
    }
    @Override
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        WriteLog("OnDeviceAttached--32");
        int ret ;
        if (!hasPermission) {
            SetTextonuiThread("Permission denied");
            return;
        }
        if (vid == 1204 || vid == 11279) {

            if (pid == 34323) {
                ret = mfs100.LoadFirmware();
                if (ret != 0) {
                    SetTextonuiThread(mfs100.GetErrorMsg(ret));
                } else {
                    SetTextonuiThread("Loadfirmware success");
                }
            } else if (pid == 4101) {
                ret = mfs100.Init();
                if (ret != 0) {
                    SetTextonuiThread(mfs100.GetErrorMsg(ret));
                } else {
                    SetTextonuiThread("Init success");
                    String info = "Serial: " + mfs100.GetDeviceInfo().SerialNo() + " Make: " + mfs100.GetDeviceInfo().Make() + " Model: " + mfs100.GetDeviceInfo().Model();
                    SetLogOnUIThread(info);
                    Intent i1 = getIntent();
                    if (i1.hasExtra("main1") || i1.hasExtra("login")) {
                        scannerAction = CommonMethod.ScannerAction.Verify;
                    } else if (i1.hasExtra("fname1")) {

                        scannerAction = CommonMethod.ScannerAction.Capture;
                    }
                    dialogbox_opener();
                }
            }
        }
    }

    @Override
    public void OnDeviceDetached() {
        WriteLog("OnDeviceDetached--33");
        UnInitScanner();
        SetTextonuiThread("Device removed");
    }

    @Override
    public void OnHostCheckFailed(String err) {
        try {
            WriteLog("OnHostCheckFailed--34");
            SetLogOnUIThread(err);
            MyCustomToast.makeToast_mfs(getApplicationContext(),err);
        } catch (Exception ex) {
            Log.w("OnHostCheckFailed",ex.getMessage());
        }
    }

    private class updation extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();
                params_final.put("userid", syncData.getUser());
                params_final.put("PunchDateTime", syncData.getDate_time());
                params_final.put("DeviceID_HR", syncData.getDeviceId_HR());
                params_final.put("DeviceID_Unique", syncData.getDeviceId_Unique());
                params_final.put("PunchMode", syncData.getPunchmode());
                params_final.put("Latitude", syncData.getLatitude());
                params_final.put("Longitude", syncData.getLongitude());
                params_final.put("MatchScore", syncData.getMatchscore());
                params_final.put("FingerQuality", syncData.getFingerquality());
                params_final.put("PhotoFileName", syncData.getPhotofilename());
                WriteLog("Inside updation part********* ");
                WriteLog(syncData.getUser()+syncData.getDate_time()+syncData.getDeviceId_HR()+syncData.getDeviceId_Unique()+syncData.getPunchmode()+syncData.getLatitude()+syncData.getLongitude()+syncData.getMatchscore()+syncData.getFingerquality()+syncData.getPhotofilename());
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
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url_4, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    try {
                                        if (response.getInt("status")==0)
                                        {
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString(blobcount, "52");
                                            editor.commit();
                                            syncData.setTest("SUCCESS INSIDE UPDATION");
                                            db.updateDb(syncData.getUser().trim(), syncData.getDate().trim(), syncData.getTime(),"Y");
                                            WriteLog("Responce Received from JSON 1 Do in BG - "+response.toString());
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
                            WriteLog(error.toString());
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    req.setRetryPolicy(policy);
                    requestQueue.add(req);
                }else WriteLog("Please connect to internet");
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    private class updation_det extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {

                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();
                params_final.put("EnrollmentNo", syncData.getUser());
                params_final.put("Hand", syncData.getHand());
                params_final.put("Finger", syncData.getFinger());
                params_final.put("File", syncData.getFile());
                params_final.put("Finger_quality", syncData.getFingerquality());
                params_final.put("Is_Sync", "Y");
                params_final.put("Class", syncData.getClass2());
                WriteLog("Inside updation part USER DETAIL********* ");
                WriteLog(syncData.getUser()+syncData.getHand()+syncData.getFinger()+syncData.getFile()+syncData.getFingerquality()+syncData.getClass2());
                pm.put("punchinfo", params_final);
                WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLInsertUser, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    WriteLog("Inside Response**//**/ ");
                                    try {
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateDetail(syncData.getUser().trim(),"Y");
                                            WriteLog("Responce Received from JSON - "+response.toString());
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            WriteLog(error.toString());
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    req.setRetryPolicy(policy);
                    requestQueue.add(req);
                }else WriteLog("Please connect to internet");
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    private class insBlobData extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                WriteLog("Inside insBlobData#$#$#$");
                WriteLog("syncData = "+pojo.getFileName()+pojo.getSession()+pojo.getDepartment()+pojo.getClass1()+pojo.getFileName());

                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();
                params_final.put("BlobValue", pojo1.getFData1());
                params_final.put("Session", pojo1.getSession());
                params_final.put("Department", pojo1.getDepartment());
                params_final.put("Class", pojo1.getClass1());
                params_final.put("FileName", pojo1.getFileName());
                WriteLog("Inside InsBlobData********* ");
                //WriteLog(syncData.getUser()+syncData.getHand()+syncData.getFinger()+syncData.getFile()+syncData.getFingerquality());
                pm.put("punchinfo", params_final);
                WriteLog("Ins BLOB DATA Inside Internet ON");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLInsBlobData, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    WriteLog("Inside Response ");
                                    try {
                                        if (response.getInt("status")==0)
                                        {
                                            //db.updateDetail(syncData.getUser().trim(),"Y");
                                            WriteLog("Responce Received from JSON - "+response.toString());
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            WriteLog(error.toString());
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    req.setRetryPolicy(policy);
                    requestQueue.add(req);
                }else WriteLog("Please connect to internet");

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    private class updation_punch extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {

                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();
                params_final.put("EnrollmentNo", syncData.getUser());
                params_final.put("Session", syncData.getSession2());
                params_final.put("Department", syncData.getDepartment2());
                params_final.put("Class", syncData.getClass2());
                params_final.put("Subject", syncData.getSubject2());
                params_final.put("Device_info", syncData.getDeviceId_HR());
                params_final.put("Date", syncData.getDate());
                params_final.put("Time", syncData.getTime());
                params_final.put("File", syncData.getFile());
                params_final.put("Is_Sync", syncData.getSyncStat());
                WriteLog("Inside updation part PUNCH DATA********* ");
                WriteLog(syncData.getUser()+syncData.getSession2()+syncData.getDepartment2()+syncData.getClass2()+syncData.getSubject2()+syncData.getDeviceId_HR()+syncData.getDate()+syncData.getTime()+syncData.getFile()+syncData.getSyncStat());
                pm.put("punchinfo", params_final);
                WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLInsertPunch, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    WriteLog("Inside Response of PUNCH**//**/ ");
                                    try {
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateDbPunch(syncData.getUser().trim(),syncData.getDate(),syncData.getTime(),"Y");
                                            WriteLog("Responce Received from JSON PUNCH - "+response.toString());
                                        }
                                        else {
                                            WriteLog("Inside Else Responce Received from JSON PUNCH - " + response.toString());
                                        }
                                        }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            WriteLog(error.toString());
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    req.setRetryPolicy(policy);
                    requestQueue.add(req);
                }else WriteLog("Please connect to internet");
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