package com.example.mega2.final_livw_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;

public class Main_screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE=1;
    Db_Helper db_helper;
    AnalogClock analogClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main);
        db_helper=new Db_Helper(this);
        analogClock =(AnalogClock)findViewById(R.id.analogClock1);
        analogClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), MFS100_final.class);
                    i.putExtra("main", "main");
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                }catch(Exception e)
                {Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();}
            }
        });
        Intent intent=getIntent();
        if (intent.hasExtra("mainActivity_mfs100Test_dialog_ok")) {
            MyPojo pojo = (MyPojo) intent.getExtras().get("mainActivity_mfs100Test_dialog");
            assert pojo != null;
            String val1 = pojo.getUserid();
            String val2 = pojo.getName();
            String val3 = pojo.getHand();
            String val4 = pojo.getFinger();
            String val5 = pojo.getFile();
            launchDismissDlg(val1, val2, val3, val4, val5);
        }
        if (getIntent().hasExtra("mainActivity_mfs100Test_dialog")) {
            String s = "NA";
            launchDismissDlg( s,s,s,s,s);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       //hide Hardware buttons
      //View decorView = getWindow().getDecorView();
      //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
      //decorView.setSystemUiVisibility(uiOptions);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {}
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {}
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {}
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)) {}
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {}
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }

    }
    private void launchDismissDlg(String val1,String val2,String val3,String val4,String val5) {
        View v = LayoutInflater.from(this).inflate(R.layout.attendance_result, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_screen.this);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        TextView id,name,hand,heading;
        ImageView img1,pic;
        LinearLayout linearLayout=(LinearLayout)v.findViewById(R.id.fial);
        RelativeLayout relativeLayout=(RelativeLayout)v.findViewById(R.id.rv1);
        img1=(ImageView)v.findViewById(R.id.user);
        id=(TextView)v.findViewById(R.id.id1);
        name=(TextView)v.findViewById(R.id.name);
        hand=(TextView)v.findViewById(R.id.hand);
        heading=(TextView)v.findViewById(R.id.textView9);
        pic=(ImageView)v.findViewById(R.id.user_pic);
        try {
            if(val1.equals("NA"))
            {
                relativeLayout.setBackground(new ColorDrawable(Color.RED));
                heading.setText(R.string.not_res);
                text_to_speech.speech(v.getContext(),"Try Again");
                id.setText(R.string.invalid);
                id.setTextColor(Color.RED);
                id.setTextSize(36 );
                id.setGravity(Gravity.CENTER_HORIZONTAL);
                name.setVisibility(View.INVISIBLE);
                hand.setVisibility(View.INVISIBLE);
                img1.setImageResource(R.drawable.rszunnamed);
                linearLayout.setBackground(new ColorDrawable(Color.parseColor("#EF9A9A")));

            }else {
                text_to_speech.speech(v.getContext(),"Welcome, "+val2);
                id.setText(String.format("User Id : %s", val1));
                name.setText(String.format("Name    : %s", val2));
                hand.setText(val3 + " , " + val4);
                if (val5!=null) {
                    File imgFile = new File(Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics//" + val5);
                    try {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        pic.setImageBitmap(myBitmap);
                    } catch (Exception e) {
                        Log.e("pic not found", e.getMessage());
                    }
                }else {pic.setImageResource(R.drawable.rsz_blank);}
            }
            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.BOTTOM ;
            wmlp.x = 10;
            wmlp.y = 10;
            dialog.show();
        }catch (Exception e)
        {
            MyCustomToast.makeToast_mfs(v.getContext(),"NO DATA PRESENT");
        }
        Timer T=new Timer();
        T.schedule(new CloseDialogTimerTask(dialog),3000);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int finger_cnt=db_helper.login_auto();
        if (id == R.id.pass)
        {   if(finger_cnt <= 0)
                {
                    startActivity(new Intent(Main_screen.this, SubActivity.class));
                }
            else
        {
                final View v = LayoutInflater.from(this).inflate(R.layout.signup, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_screen.this);
                builder.setView(v);
                final AlertDialog dialog = builder.create();
                final EditText ed1 = (EditText) v.findViewById(R.id.editTextUserName);
                final EditText ed2 = (EditText) v.findViewById(R.id.editTextPassword);
                ed2.requestFocus();
                Button bt = (Button) v.findViewById(R.id.buttonLogin);
                bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                  db_helper = new Db_Helper(v.getContext());
                            String username = ed1.getText().toString();
                            String password_db = db_helper.getpassword(username.trim());
                            String password = ed2.getText().toString();
                            if (username.equals("") || password.equals("")) {
                                Toast.makeText(Main_screen.this, "Field Vacant", Toast.LENGTH_SHORT).show();
                            } else if (username.equals("Admin") && (password.equals("1234"))) {
                                startActivity(new Intent(Main_screen.this, SubActivity.class));
                                dialog.dismiss();
                            } else if (username.equals(username) && (password.equals(password_db.trim()))) {
                                startActivity(new Intent(Main_screen.this, SubActivity.class));
                                dialog.dismiss();
                            } else {
                                Toast.makeText(Main_screen.this, "Invalid UserId or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                    return true;
                }
            }
        else if (id == R.id.finger)
        {
            Intent i=new Intent(Main_screen.this,MFS100_final.class);
            i.putExtra("login", "login");
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }
        else if (id == R.id.mainmenu)
        {
            Intent intent = new Intent(this, mainmenu1.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
