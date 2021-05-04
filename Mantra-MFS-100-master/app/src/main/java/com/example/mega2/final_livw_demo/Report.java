package com.example.mega2.final_livw_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class Report extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_report);

        assert getSupportActionBar()!=null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void detail(View view) {
        if(view.getId()==R.id.bt_att)
        {
            startActivity(new Intent(Report.this,UserReport_Attendance.class));
        }
        if(view.getId()==R.id.bt_summ)
        {
            //replace with own action

        }
        if (view.getId()==R.id.bt_usr)
        {
            Intent intent=new Intent(Report.this,Existinguser.class);
            intent.putExtra("Report","report");
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu mymenu) {
        getMenuInflater().inflate(R.menu.mymenu, mymenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
         startActivity(new Intent(Report.this,SubActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
