package com.example.mega2.final_livw_demo;

import android.Manifest;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.*;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import android.view.View.OnClickListener;

public class Newuser extends AppCompatActivity {

    EditText username, userid,password;
    Spinner sp, sp2;
    String UserType[] = {"User", "Admin"};
    String VerifyMode[] = {"Only Finger","Uid With Finger","Only Uid"};
    String vmode,utype;
    LinearLayout linearLayout;
    ArrayAdapter<String> aa;
    ArrayAdapter<String> AA;
    MyPojo pojo=new MyPojo();
    Db_Helper helper1=new Db_Helper(this);
    Button btn2,btn3,dob,camera_1;
    int year_x,month_x,day_x;
    static final int dialod_id =0;
    ImageView preview_final;
    private int MY_REQUEST_CODE=10;
    android.support.v7.app.AlertDialog dialog;
    String path = Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_newuser);

        final View v = LayoutInflater.from(this).inflate(R.layout.camera, null);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Newuser.this);
        builder.setView(v);
        dialog= builder.create();
        final Button gallery,camera;
        gallery=(Button)v.findViewById(R.id.gallery);
        camera=(Button)v.findViewById(R.id.camera);
        sp = (Spinner) findViewById(R.id.spinner);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        btn2=(Button)findViewById(R.id.button2);
        dob=(Button) findViewById(R.id.dob);
        password=(EditText)findViewById(R.id.Edt_password);
        userid = (EditText) findViewById(R.id.uservalue);
        username = (EditText) findViewById(R.id.name);
        btn3=(Button)findViewById(R.id.button3);
        preview_final=(ImageView)findViewById(R.id.preview_final);
        btn3.setVisibility(View.INVISIBLE);
        camera_1=(Button) findViewById(R.id.image_pic);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CODE);
            }
        }
        linearLayout=(LinearLayout)findViewById(R.id.rl1);
        aa = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, VerifyMode);
        sp.setAdapter(aa);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_1, VerifyMode);
        sp.setAdapter(spinnerArrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                vmode=VerifyMode[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        AA = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, UserType);
        sp2.setAdapter(AA);
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(this, R.layout.spinner_item_2, UserType);
        sp2.setAdapter(spinnerArrayAdapter1);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                utype=UserType[position];
                if(utype.trim().toUpperCase().equals("USER"))
                {
                    password.setEnabled(false);
                }
                else password.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  }});

        dob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialod_id);
            }
        });
        camera_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userid.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Field Vacant",Toast.LENGTH_LONG).show();
                }
                else {
                    dialog.show();
                    camera_1.setText(String.format("%s.png", userid.getText().toString()));
                }
            }
        });
        gallery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent,1);
            }
        });
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;
                if (unique()) b = true;
                else b = false;
                if(b)
                {
                    final String uid = userid.getText().toString();
                    final String uname = username.getText().toString();
                    vmode ="OF";
                    utype = sp2.getSelectedItem().toString();
                    if(sp.getSelectedItem().toString().toUpperCase().equals("ONLY FINGER")){vmode="OF";}
                    else if(sp.getSelectedItem().toString().toUpperCase().equals("UID WITH FINGER")){vmode="UF";}
                    else if(sp.getSelectedItem().toString().toUpperCase().equals("ONLY UID")){vmode="OU";}
                    String date = dob.getText().toString();
                    String pass=password.getText().toString();
                    file = new File(path);
                    if (!file.exists()) { file.mkdirs();}
                    path = path + "//" +uid+".png";
                    String filename=uid+".png";
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
                        fos.flush();
                        fos.close();
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Image not selected",Toast.LENGTH_LONG).show();
                        filename=null;
                    }

                    final boolean result = helper1.insert_USER_Data(uid, uname, vmode, utype, date,pass,filename);
                    final AlertDialog.Builder ab = new AlertDialog.Builder(Newuser.this);
                    ab.setMessage("Do you want to register finger ?");
                    ab.setTitle("Alert!!! - Add User ");
                    ab.setIcon(R.drawable.h);
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (result) {
                                Intent intent = new Intent(Newuser.this, Detail.class);
                                Toast.makeText(getApplicationContext(), "Inserted without fingerPrint !!!" ,Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                    });
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                final Intent mf = new Intent(getApplicationContext(), MFS100_final.class);
                                pojo = new MyPojo();
                                pojo.setUserid(uid);
                                pojo.setName(uname);
                                mf.putExtra("fname", pojo);
                                mf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(mf);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                    AlertDialog ada = ab.create();
                    ada.show();
                }
            }
        });
        if(getIntent().hasExtra("data")) {
            pojo = (MyPojo) getIntent().getExtras().get("data");
            assert pojo != null;
            userid.setText(pojo.getUserid());
            userid.setEnabled(false);
            username.setText(pojo.getName());
            username.requestFocus();
            password.setText(pojo.getpass());
            final File imgFile = new  File(Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics//"+pojo.getFile());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                preview_final.setImageBitmap(myBitmap);
                camera_1.setText(pojo.getUserid()+".png");
                pojo.setPic(myBitmap);
            }
            else {
                Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.rsz_blank);
                preview_final.setImageBitmap(icon);
                camera_1.setText("No image found");
                pojo.setPic(null);
            }
            btn3.setVisibility(View.VISIBLE);
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
            utype =pojo.getUserType();
            if (utype.toUpperCase().trim().equals("U"))
            {
                sp2.setSelection(AA.getPosition("User"));
            } else {
                sp2.setSelection(AA.getPosition("Admin"));
            }
            dob.setText(pojo.getDate());
            final String id=String.valueOf(pojo.getCid());
            btn3.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(Newuser.this, Existinguser.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    final AlertDialog.Builder ab = new AlertDialog.Builder(Newuser.this);
                    ab.setMessage("Do you want to delete this user???");
                    ab.setTitle("Alert!!! - Delete User");
                    ab.setIcon(R.drawable.rszdelete);
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}});
                    ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                imgFile.delete();
                                Cursor filename=helper1.getfile(userid.getText().toString());
                                Boolean deleted=true;
                                while (filename.moveToNext()){
                                    String file=filename.getString(0);
                                    File f = new File(Environment.getExternalStorageDirectory() + "//Megamind//Finger_data//"+file);
                                    deleted = f.delete();
                                }
                                long l1 = helper1.deleteDetails_UD(userid.getText().toString());
                                long l2 = helper1.deleteDetails_UM(userid.getText().toString());
                                if ((l1 <= 0) && (l2 <= 0)&&(!deleted)){
                                    Toast.makeText(getApplicationContext(), "not Success",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
                                }
                                startActivity(intent);
                            }catch (Exception e){Log.e("Delete_data",e.getMessage());}}

                    });
                    AlertDialog ada = ab.create();
                    ada.show();
                }
            });

            btn2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String uid = userid.getText().toString();
                    final String uname = username.getText().toString();
                    vmode ="OF";
                    utype = sp2.getSelectedItem().toString();
                    if(utype.trim().toUpperCase().equals("USER"))
                    {
                        password.setText("");
                    }
                    if(sp.getSelectedItem().toString().toUpperCase().equals("ONLY FINGER")){vmode="OF";}
                    else if(sp.getSelectedItem().toString().toUpperCase().equals("UID WITH FINGER")){vmode="UF";}
                    else if(sp.getSelectedItem().toString().toUpperCase().equals("ONLY UID")){vmode="OU";}
                    String date = dob.getText().toString();
                    String pass=password.getText().toString();
                    file = new File(path);
                    if (!file.exists()) { file.mkdirs();}
                    path = path + "//" +uid+".png";
                    String filename=uid+".png";
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
                    final long l = helper1.modifyDetails(id, uid, uname, vmode, utype, date,pass,filename);
                    final Intent mf = new Intent(getApplicationContext(), MFS100_final.class);
                    AlertDialog.Builder ab = new AlertDialog.Builder(Newuser.this);
                    ab.setMessage("Do you want to modify finger data ?");
                    ab.setTitle("Alert!!! - Modify User");
                    ab.setIcon(R.drawable.h);
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (l > 0) {
                                Intent intent = new Intent(Newuser.this, Existinguser.class);
                                Toast.makeText(getApplicationContext(), "Sucessfully Modified", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                    });
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pojo = new MyPojo();
                            pojo.setUserid(uid);
                            pojo.setName(uname);
                            mf.putExtra("fname", pojo);
                            mf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(mf);
                        }
                    });
                    AlertDialog ada = ab.create();
                    ada.show();
                }
            });
        }

        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v= Newuser.this.getCurrentFocus();
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
        {MFS100_final mfs100Test=new MFS100_final();
            mfs100Test.WriteLog(e.getMessage());}}



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
            String date = new SimpleDateFormat("yyyy-MM-dd",Locale.UK).format(d);
            dob.setText(date);
        }
    };
    public boolean unique() {
        if (username.getText().toString().equals("") || userid.getText().toString().equals("")) {
            Toast.makeText(this,"Field Vacant",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            String ss = helper1.getallData(userid.getText().toString());
            if (ss.equals("")) {
                return true;
            } else {
                userid.setText("");
                userid.setHint("This userid is already exist");
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
}