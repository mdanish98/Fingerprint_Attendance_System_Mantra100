package com.example.mega2.final_livw_demo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class UserReport_Attendance extends AppCompatActivity {
    Db_Helper pdba;
    ArrayList arrayList = new ArrayList();
    RecyclerView recyclerView;
    EditText editText;
    Report_adapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_user_report);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.requestFocus();
        editText= (EditText) findViewById(R.id.search_bar1);
        try {
            pdba = new Db_Helper(this);
            arrayList = pdba.getAllData_report();
            adapter1 = new Report_adapter(this, arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter1);
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserReport_Attendance.this.adapter1.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {  }
        });
        assert getSupportActionBar()!=null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           startActivity(new Intent(UserReport_Attendance.this,Report.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
