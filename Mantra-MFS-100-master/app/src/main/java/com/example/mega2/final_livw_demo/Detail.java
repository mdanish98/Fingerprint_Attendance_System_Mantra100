package com.example.mega2.final_livw_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Detail extends AppCompatActivity {

    Button bt4,bt5,bt6,bt7;
    Db_Helper db_helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_detail);
        bt4=(Button)findViewById(R.id.bt4);
        bt5=(Button)findViewById(R.id.bt5);
        bt6=(Button)findViewById(R.id.bt6);
        bt7=(Button)findViewById(R.id.bt7);

        assert getSupportActionBar()!=null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void fun1(View v)
    {
        if(v.getId()==R.id.bt4)
        {
            try {
                Intent i = new Intent(this, Newuser.class);
                startActivity(i);
            }catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        if (v.getId()==R.id.bt5)
        {
            Intent i=new Intent(this,Existinguser_main.class);
            startActivity(i);
        }
        if (v.getId()==R.id.bt6) {}
        if (v.getId()==R.id.bt7) {
            new FileChooser(Detail.this).setFileListener(new FileChooser.FileSelectedListener() {
                @Override public void fileSelected(final File file) throws IOException {
                    String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(file.getName());
                    if(ext.trim().toUpperCase().equals("TXT") || (ext.trim().toUpperCase().equals("TXT"))){
                    BufferedReader reader =new BufferedReader(new FileReader(file));
                    String line;
                    String uid, uname, vmode, utype, date;
                    while((line=reader.readLine())!=null) {
                        String[] employee = line.trim().split(",");
                        if (employee.length == 5) {
                            uid = String.valueOf(employee[0]);
                            uname = String.valueOf(employee[1]);
                            vmode = String.valueOf(employee[2]);
                            utype = String.valueOf(employee[3]);
                            date = String.valueOf(employee[4]);
                            String val1=String.valueOf(employee[0]);
                            if (!val1.toUpperCase().trim().equals("CARDNO")) {
                                db_helper = new Db_Helper(Detail.this);
                                db_helper.insert_USER_Data_from_csv(uid, uname, vmode, utype, date);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error in data "+ line.trim(),Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(),"Uploaded Sucessfully!!",Toast.LENGTH_LONG).show();
                    }
                }else{  Toast.makeText(getApplicationContext(),"Not a valid file format!!",Toast.LENGTH_LONG).show();}
                }}).showDialog();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu mymenu) {
        getMenuInflater().inflate(R.menu.mymenu, mymenu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            startActivity(new Intent(this, SubActivity.class));

        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this, Newuser.class));
        }
        return super.onOptionsItemSelected(item);

    }
}
