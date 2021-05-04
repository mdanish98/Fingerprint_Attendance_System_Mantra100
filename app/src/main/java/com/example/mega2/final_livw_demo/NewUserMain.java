package com.example.mega2.final_livw_demo;

import android.Manifest;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.*;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.*;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import android.view.View.OnClickListener;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewUserMain  extends AppCompatActivity {
    EditText enrollmentno, studentname,mobile,email,rollno,studentid,address,password;
    private ArrayList<String> session1,dept1,class1,sub1;
    private JSONArray result,resultdept,resultclass,resultsub;
    Spinner spsession, spdept,spclass,spgender,spsubject;
    String session[] = {"19", "20"};
    String department[] = {"Civil","Mechanical","Electrical","Electronics","Computer"};
    String clas[] = {"BCS1","BCS2","BCS3","BCS4","BCS5"};
    String gender[] = {"Male","Female"};
    String sess,dept,clss,gendr;
    String gendersel,gendermain;
    LinearLayout linearLayout;
    ArrayAdapter<String> aasession;
    ArrayAdapter<String> AAdepartment;
    ArrayAdapter<String> AAclass;
    ArrayAdapter<String> AAgender;
    MyPojo_main pojo=new MyPojo_main();
    String subj,userid,JURLFetchSess = pojo.JURLFetchSess.toString(),JURLFetchDept = pojo.JURLFetchDept.toString(),JURLFetchClass = pojo.JURLFetchClass.toString(),JURLFetchSub = pojo.JURLFetchSub.toString(),JURLInsertNew = pojo.JURLInsertNew.toString(),JURLgetBlobData = pojo.JURLgetBlobData.toString();
    Db_Helper_main helper1=new Db_Helper_main(this);
    sync_data_main syncData=new sync_data_main();
    Button btnSbmt,btnCaptr,btnDel,dob,camera_1;
    int year_x,month_x,day_x;
    static final int dialod_id =0;
    ImageView preview_final;
    private int MY_REQUEST_CODE=10;
    android.support.v7.app.AlertDialog dialog;
    String path = Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics";
    File file;
    MFS100_final_main logFile = new MFS100_final_main();
    Db_Helper_main db=new Db_Helper_main(NewUserMain.this);
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Session = "sessKey";
    public static final String Dept = "deptKey";
    public static final String Class = "classKey";
    public static final String Subject = "subjectKey";
    SharedPreferences sharedpreferences;
    Class_selector classSel = new Class_selector();
    MFS100_final_main mfs = new MFS100_final_main();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_newusermain);
        Log.i("Info", "in Bundle!!!");
        final View v = LayoutInflater.from(this).inflate(R.layout.camera, null);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(NewUserMain.this);
        builder.setView(v);
        dialog = builder.create();
        final Button gallery, camera;
        gallery = (Button) v.findViewById(R.id.gallery);
        camera = (Button) v.findViewById(R.id.camera);
        session1 = new ArrayList<String>();
        dept1 = new ArrayList<String>();
        class1 = new ArrayList<String>();
        sub1 = new ArrayList<String>();
        spsession = (Spinner) findViewById(R.id.spinnerSession);
        spdept = (Spinner) findViewById(R.id.spinnerDept);
        spclass = (Spinner) findViewById(R.id.spinnerClass);
        spgender = (Spinner) findViewById(R.id.spinnerGender);
        password = (EditText) findViewById(R.id.editTextPassword);
        btnSbmt = (Button) findViewById(R.id.buttonsubmit);
        dob = (Button) findViewById(R.id.dob);
        enrollmentno = (EditText) findViewById(R.id.editTextEnroll);
        studentname = (EditText) findViewById(R.id.editTextStuName);
        mobile = (EditText) findViewById(R.id.editTextmobile);
        email = (EditText) findViewById(R.id.editTextEmail);
        rollno = (EditText) findViewById(R.id.editTextRoll);
        address = (EditText) findViewById(R.id.editTextAddress);
        studentid = (EditText) findViewById(R.id.editText1StudentID);
        btnDel = (Button) findViewById(R.id.buttonDelete);
        btnDel.setVisibility(View.INVISIBLE);
        btnCaptr = (Button) findViewById(R.id.btnSyncCapture);
        preview_final = (ImageView) findViewById(R.id.preview_finalimage);
        btnCaptr.setVisibility(View.VISIBLE);
        camera_1 = (Button) findViewById(R.id.image_pic);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CODE);
            }
        }
        linearLayout = (LinearLayout) findViewById(R.id.rl1);
        spsession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Long.toString(id);
                sess = spsession.getSelectedItem().toString();
                getDept();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        getData();
        spdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                dept = spdept.getSelectedItem().toString();
                class1.clear();
                getClassMain(dept);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /*
        AAdepartment = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, department);
        spdept.setAdapter(AAdepartment);
        ArrayAdapter<String> departmentArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_department, department);
        spdept.setAdapter(departmentArrayAdapter);
        spdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                dept = department[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        AAclass = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, clas);
        spclass.setAdapter(AAclass);
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_class, clas);
        spclass.setAdapter(classArrayAdapter);
        spclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                clss = clas[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        */
        AAgender = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, gender);
        spgender.setAdapter(AAgender);
        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_gender, gender);
        spgender.setAdapter(genderArrayAdapter);
        spgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                gendr = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        gendermain ="M";
        gendersel = spgender.getSelectedItem().toString();
        if(spgender.getSelectedItem().toString().toUpperCase().equals("MALE")){gendermain="M";}
        else if(spgender.getSelectedItem().toString().toUpperCase().equals("FEMALE")){gendermain="F";}
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialod_id);
            }
        });
        camera_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enrollmentno.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                } else {
                    dialog.show();
                    camera_1.setText(String.format("%s.png", enrollmentno.getText().toString()));
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 1);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        btnSbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;
                if (unique()) b = true;
                else b = false;
                if (b) {
                    final String enrollno = enrollmentno.getText().toString();
                    final String stuname = studentname.getText().toString();
                    //vmode ="OF";
                    sess = spsession.getSelectedItem().toString();
                    dept = spdept.getSelectedItem().toString();
                    clss = spclass.getSelectedItem().toString();
                    gendr = spgender.getSelectedItem().toString();
                    String pass = password.getText().toString();
                    String mob = mobile.getText().toString();
                    String email1 = email.getText().toString();
                    String rollnum = rollno.getText().toString();
                    String stuid = studentid.getText().toString();
                    String addr = address.getText().toString();
                    String date = dob.getText().toString();
                    String path1 = Environment.getExternalStorageDirectory() + "//Megamind//Finger_data";
                    File file1 = new File(path1);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    file = new File(path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    path = path + "//" + enrollno + ".png";
                    String filename = enrollno + ".png";
                    file = new File(path);
                    try {
                        Bitmap bitmap = pojo.getPic();
                        int size = bitmap.getByteCount();
                        while (size > (300 * 8 * 1024)) {
                            int height = (int) (bitmap.getHeight() * 0.1);
                            int width = (int) (bitmap.getWidth() * 0.1);
                            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                            size = bitmap.getByteCount();
                        }
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        FileOutputStream fos = new FileOutputStream(path);
                        fos.write(bitmapdata);
                        String encodedString = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
                        syncData.setImageData(encodedString);
                        syncData.setImageDataBlob(bitmapdata);
                        fos.flush();
                        fos.close();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Image not selected", Toast.LENGTH_LONG).show();
                        filename = null;
                    }
                    Toast.makeText(getApplicationContext(), "before on submit", Toast.LENGTH_LONG).show();
                    try {

                        final boolean result = helper1.insert_USER_Data(enrollno, stuname, sess, dept, clss, pass, mob, email1, rollnum, stuid, addr, gendr, date, filename,syncData.getImageDataBlob());
                        syncData.setuser(enrollno);
                        syncData.setStuName(stuname);
                        syncData.setSession2(sess);
                        syncData.setDepartment2(dept);
                        syncData.setClass2(clss);
                        syncData.setPWD(pass);
                        syncData.setMobNo(mob);
                        syncData.setEmail(email1);
                        syncData.setRollNo(rollnum);
                        syncData.setStudentID(stuid);
                        syncData.setAddress(addr);
                        syncData.setGender(gendr);
                        syncData.setDob(date);
                        syncData.setPhotofilename(filename);

                        //***********************************
                        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Session, sess);
                        editor.putString(Dept, dept);
                        editor.putString(Class, clss);
                        editor.commit();

                        new getBlobData1().execute(sess,dept,clss);
                        new updation().execute();
                        final AlertDialog.Builder ab = new AlertDialog.Builder(NewUserMain.this);
                        ab.setMessage("Do you want to register finger ?");
                        ab.setTitle("Alert!!! - Add User ");
                        ab.setIcon(R.drawable.h);
                        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                if (result) {
                                    Intent intent = new Intent(NewUserMain.this, Detail.class);
                                    Toast.makeText(getApplicationContext(), "Inserted without fingerPrint !!!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                        });
                        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    final Intent mf = new Intent(getApplicationContext(), MFS100_final_main.class);
                                    pojo = new MyPojo_main();
                                    pojo.setEnrollment(enrollno);
                                    pojo.setName(stuname);
                                    mf.putExtra("fname1", pojo);
                                    mf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(mf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        AlertDialog ada = ab.create();
                        ada.show();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Problem", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (getIntent().hasExtra("data")) {
            pojo = (MyPojo_main) getIntent().getExtras().get("data");
            assert pojo != null;
            enrollmentno.setText(pojo.getEnrollment());
            enrollmentno.setEnabled(false);
            studentname.setText(pojo.getName());
            studentname.requestFocus();
            password.setText(pojo.getPassword());
            final File imgFile = new File(Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics//" + pojo.getFile());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                preview_final.setImageBitmap(myBitmap);
                camera_1.setText(pojo.getEnrollment() + ".png");
                pojo.setPic(myBitmap);
            } else {
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.rsz_blank);
                preview_final.setImageBitmap(icon);
                camera_1.setText("No image found");
                pojo.setPic(null);
            }
            btnDel.setVisibility(View.VISIBLE);

            /*
            vmode = pojo.getVerifyMode();

            if(vmode.toUpperCase().trim().equals("OF"))
            {
                sp.setSelection(aa.getPosition("Only Finger"));
            }else  if(vmode.toUpperCase().trim().equals("UF"))
            {
                sp.setSelection(aa.getPosition("Uid With Finger"));
            } else  if(vmode.toUpperCase().trim().equals("OU"))
            {
                sp.setSelection(aa.getPosition("Only Uid"));
            }
            */



            /*
            sess = pojo.getSession();
            //utype =pojo.getUserType();
            if (sess.toUpperCase().trim().equals("19")) {
                spsession.setSelection(aasession.getPosition("19"));
            } else {
                spsession.setSelection(aasession.getPosition("20"));
            }

            dept = pojo.getDepartment();
            //utype =pojo.getUserType();
            if (dept.toUpperCase().trim().equals("Civil")) {
                spdept.setSelection(AAdepartment.getPosition("Civil"));
            } else if (dept.toUpperCase().trim().equals("Mechanical")) {
                spdept.setSelection(AAdepartment.getPosition("Mechanical"));
            } else if (dept.toUpperCase().trim().equals("Electrical")) {
                spdept.setSelection(AAdepartment.getPosition("Electrical"));
            } else if (dept.toUpperCase().trim().equals("Electronics")) {
                spdept.setSelection(AAdepartment.getPosition("Electronics"));
            } else if (dept.toUpperCase().trim().equals("Computer")) {
                spdept.setSelection(AAdepartment.getPosition("Computer"));
            }

            clss = pojo.getClass1();
            //utype =pojo.getUserType();
            if (clss.toUpperCase().trim().equals("BCS1")) {
                spclass.setSelection(AAclass.getPosition("BCS1"));
            } else if (clss.toUpperCase().trim().equals("BCS2")) {
                spclass.setSelection(AAclass.getPosition("BCS2"));
            } else if (clss.toUpperCase().trim().equals("BCS3")) {
                spclass.setSelection(AAclass.getPosition("BCS3"));
            } else if (clss.toUpperCase().trim().equals("BCS4")) {
                spclass.setSelection(AAclass.getPosition("BCS4"));
            } else if (clss.toUpperCase().trim().equals("BCS5")) {
                spclass.setSelection(AAclass.getPosition("BCS5"));
            }
               */
            gendermain = pojo.getGender();
            //utype =pojo.getUserType();
            if (gendermain.toUpperCase().trim().equals("M")) {
                spgender.setSelection(AAgender.getPosition("Male"));
            } else if (gendermain.toUpperCase().trim().equals("F")) {
                spgender.setSelection(AAgender.getPosition("Male"));
            }
            mobile.setText(pojo.getMobile());
            email.setText(pojo.getEmail());
            rollno.setText(pojo.getRoll());
            studentid.setText(pojo.getStudentid());
            address.setText(pojo.getAddress());
            dob.setText(pojo.getDate());
            final String id = String.valueOf(pojo.getCid());


            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(NewUserMain.this, Existinguser.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    final AlertDialog.Builder ab = new AlertDialog.Builder(NewUserMain.this);
                    ab.setMessage("Do you want to delete this user???");
                    ab.setTitle("Alert!!! - Delete User");
                    ab.setIcon(R.drawable.rszdelete);
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                imgFile.delete();
                                Cursor filename = helper1.getfile(enrollmentno.getText().toString());
                                Boolean deleted = true;
                                while (filename.moveToNext()) {
                                    String file = filename.getString(0);
                                    File f = new File(Environment.getExternalStorageDirectory() + "//Megamind//Finger_data//" + file);
                                    deleted = f.delete();
                                }
                                long l1 = helper1.deleteDetails_UD(enrollmentno.getText().toString());
                                long l2 = helper1.deleteDetails_UM(enrollmentno.getText().toString());
                                if ((l1 <= 0) && (l2 <= 0) && (!deleted)) {
                                    Toast.makeText(getApplicationContext(), "not Success", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                }
                                startActivity(intent);
                            } catch (Exception e) {
                                Log.e("Delete_data", e.getMessage());
                            }
                        }

                    });
                    AlertDialog ada = ab.create();
                    ada.show();
                }
            });

            btnSbmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String enroll = enrollmentno.getText().toString();
                    final String stuname = studentname.getText().toString();
                    //vmode ="OF";
                    //utype = sp2.getSelectedItem().toString();


                    sess = spsession.getSelectedItem().toString();
                    dept = spdept.getSelectedItem().toString();
                    clss = spclass.getSelectedItem().toString();
                    gendr = spgender.getSelectedItem().toString();

                    String mob = mobile.getText().toString();

                    String email1 = email.getText().toString();
                    String roll = rollno.getText().toString();
                    String stuid = studentid.getText().toString();
                    String address1 = address.getText().toString();

                    String date = dob.getText().toString();
                    String pass=password.getText().toString();

                    file = new File(path);
                    if (!file.exists()) { file.mkdirs();}
                    path = path + "//" +enroll+".png";
                    String filename=enroll+".png";
                    file = new File(path);
                    try
                    {
                        Bitmap bitmap = pojo.getPic();
                        int size=bitmap.getByteCount();
                        while (size>(300*8*1024)) {
                            int height = (int) (bitmap.getHeight() * 0.30);
                            int width = (int) (bitmap.getWidth() * 0.30);
                            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                            size = bitmap.getByteCount();
                        }
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos);
                        byte[] bitmapdata = bos.toByteArray();
                        try {
                            FileOutputStream fos = new FileOutputStream(path);
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"No image selected!!",Toast.LENGTH_LONG).show();
                        filename=null;
                    }
                    final long l = helper1.modifyDetails(id, enroll, stuname,sess,dept,clss,pass,mob,email1,roll,stuid,address1,gendr,date,filename);
                    final Intent mf = new Intent(getApplicationContext(), MFS100_final_main.class);
                    AlertDialog.Builder ab = new AlertDialog.Builder(NewUserMain.this);
                    ab.setMessage("Do you want to modify finger data ?");
                    ab.setTitle("Alert!!! - Modify User");
                    ab.setIcon(R.drawable.h);
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (l > 0) {
                                Intent intent = new Intent(NewUserMain.this, Existinguser.class);
                                Toast.makeText(getApplicationContext(), "Sucessfully Modified", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                    });
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pojo = new MyPojo_main();
                            pojo.setEnrollment(enroll);
                            pojo.setName(stuname);
                            mf.putExtra("fname1", pojo);
                            mf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(mf);
                        }
                    });
                    AlertDialog ada = ab.create();
                    ada.show();
                }
            });


        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v= NewUserMain.this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_GRANTED:
                    break;
                default:break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if(null != data) {
                switch (requestCode) {
                    case 0:
                        Bitmap thumbNail;
                        if (resultCode == RESULT_OK) {
                            thumbNail = (Bitmap) data.getExtras().get("data");
                            pojo.setPic(thumbNail);
                            preview_final.setImageBitmap(pojo.getPic());
                            dialog.dismiss();
                        }
                        break;
                    case 1:
                        if (resultCode == RESULT_OK) {
                            Uri cameraUri = data.getData();
                            try {
                                thumbNail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cameraUri);
                                pojo.setPic(thumbNail);
                                preview_final.setImageBitmap(pojo.getPic());
                                dialog.dismiss();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }else{Log.e("Cancelled-Message","No pic selected..");}
        }catch (Exception e)
        {MFS100_final_main mfs100Test=new MFS100_final_main();
            mfs100Test.WriteLog(e.getMessage());}


    }


    @Override
    protected Dialog onCreateDialog(int id){
        if (id==dialod_id) {
            try {
                if ((dob.getText().toString().equals("")) || (dob.getText().equals(null) )) {
                    final Calendar calendar = Calendar.getInstance();
                    year_x = calendar.get(Calendar.YEAR);
                    month_x = calendar.get(Calendar.MONTH);
                    day_x = calendar.get(Calendar.DAY_OF_MONTH);
                } else {
                    String date = String.valueOf(dob.getText());
                    String[] final_date = date.split("-");
                    year_x = Integer.parseInt(final_date[0]);
                    month_x = Integer.parseInt(final_date[1])-1;
                    day_x = Integer.parseInt(final_date[2]);

                }
            }catch (Exception e) { Log.e("Date_picker",e.getMessage());}
            return new DatePickerDialog(this, dpickerlistener, year_x, month_x, day_x);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Date d = new Date(year-1900, month, dayOfMonth);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(d);
            dob.setText(date);
        }
    };

    public boolean unique() {
        if (studentname.getText().toString().equals("") || enrollmentno.getText().toString().equals("")) {
            Toast.makeText(this,"Field Vacant",Toast.LENGTH_LONG).show();

            //Toast.makeText(this,password.getText().toString(),Toast.LENGTH_LONG).show();
            //Toast.makeText(this,enrollmentno.getText().toString(),Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            String ss = helper1.getallData(enrollmentno.getText().toString());
            if (ss.equals("")) {
                return true;
            } else {
                enrollmentno.setText("");
                enrollmentno.setHint("This userid is already exist");
                return false;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent i=new Intent(getApplicationContext(),Detail.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(JURLFetchSess+"?qid=1",
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
        spsession.setAdapter(new ArrayAdapter<String>(NewUserMain.this, R.layout.spinner_item_session, session1));
    }


    //Department
    private void getDept(){
        StringRequest stringRequest = new StringRequest(JURLFetchDept+"?qid=1",
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
        spdept.setAdapter(new ArrayAdapter<String>(NewUserMain.this, R.layout.spinner_item_department, dept1));
    }
    //Class
    private void getClassMain(String dept){
        StringRequest stringRequest = new StringRequest(JURLFetchClass+"?qid=1&dept="+dept,
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
        spclass.setAdapter(new ArrayAdapter<String>(NewUserMain.this, R.layout.spinner_item_class, class1));
    }

    private class updation extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {

                final JSONObject pm = new JSONObject();
                JSONObject params_final = new JSONObject();
                params_final.put("EnrollmentNo", syncData.getUser());
                params_final.put("StudentName", syncData.getStuName());
                params_final.put("Session", syncData.getSession2());
                params_final.put("Department", syncData.getDepartment2());
                params_final.put("Class", syncData.getClass2());
                params_final.put("Password", syncData.getPWD());
                params_final.put("MobileNo", syncData.getMobNo());
                params_final.put("Email", syncData.getEmail());
                params_final.put("RollNo", syncData.getRollNo());
                params_final.put("StudentID", syncData.getStudentID());
                params_final.put("Address", syncData.getAddress());
                params_final.put("Gender", syncData.getGender());
                params_final.put("Expiry", syncData.getExpiry());
                params_final.put("Date_of_birth", syncData.getDob());
                params_final.put("Is_sync", "Y");
                params_final.put("PhotoFileName", syncData.getPhotofilename());
                params_final.put("ImageData", syncData.getImageData());
                logFile.WriteLog("Inside updation part********* ");
                logFile.WriteLog(syncData.getUser()+syncData.getStuName()+syncData.getSession2()+syncData.getDepartment2()+syncData.getClass2()+syncData.getPWD()+syncData.getMobNo()+syncData.getEmail()+syncData.getRollNo()+syncData.getStudentID()+syncData.getAddress()+syncData.getGender()+syncData.getDob()+syncData.getPhotofilename()+syncData.getImageData());
                pm.put("punchinfo", params_final);
                logFile.WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLInsertNew, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    logFile.WriteLog("Inside Response**//**/ ");
                                    try {
                                        if (response.getInt("status")==0)
                                        {
                                            db.updateMasterDb(syncData.getUser().trim(),"Y");
                                            logFile.WriteLog("Response Received from JSON - "+response.toString());
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
                            logFile.WriteLog(error.toString());
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    req.setRetryPolicy(policy);
                    requestQueue.add(req);
                }else logFile.WriteLog("Please connect to internet");
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    private class getBlobData1 extends AsyncTask<String, String, String>
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
                logFile.WriteLog("sess dept class = "+sess1 + dept1 + class1);
                logFile.WriteLog("Inside getBlobData********* ");
                logFile.WriteLog(sess1 + dept1 + class1);
                pm.put("punchinfo", params_final);
                logFile.WriteLog("Inside Internet ON**//**/ ");
                if (isInternetOn()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, JURLgetBlobData, params_final,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int len = (String.valueOf(response)).length();
                                    logFile.WriteLog("Inside Response of getBlobData**//**/ ");
                                    try {
                                        JSONArray result = response.getJSONArray("result");

                                        for(i=0;i<result.length();i++)
                                        {
                                            JSONObject c = result.getJSONObject(i);
                                            //logFile.WriteLog("Received ID = "+id[i]);
                                            //final MyPojo_main pojo = db.retrieveISO(id[i],sess,dept,clss);
                                            //byte[] FData = new byte[1000];
                                            byte[] decodedString = Base64.decode(c.getString("blobdata"), Base64.DEFAULT);
                                            String filename = c.getString("blobname");
                                            String str = new String(decodedString);
                                            logFile.WriteLog("Iteration - "+i+" byte - "+str);
                                            logFile.WriteLog("Iteration - "+i+" filename - "+filename);
                                            /*
                                            String FName;
                                            FData = pojo.getFData();
                                            FName = pojo.getFileName();
                                            */
                                            classSel.WriteFile(filename,decodedString);

                                            logFile.WriteLog("All Data Restored from MySQL DB");
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
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            logFile.WriteLog(error.toString());
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    req.setRetryPolicy(policy);
                    requestQueue.add(req);
                }else logFile.WriteLog("Please connect to internet");
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
