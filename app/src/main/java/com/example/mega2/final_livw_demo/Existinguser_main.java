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

import java.util.ArrayList;

public class Existinguser_main extends AppCompatActivity {
   Db_Helper_main helper;
    RecyclerView recyclerView;
    EditText editText;
    ModifyAdapter_main MAdap;
    java.util.ArrayList ArrayList= new ArrayList();
    String value=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_existinguser_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        recyclerView.requestFocus();
        helper = new Db_Helper_main(Existinguser_main.this);
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        value = extras.getString("Report");
        ArrayList = helper.getAllData("");
        MAdap = new ModifyAdapter_main(this, ArrayList,value);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(MAdap);


        editText = (EditText) findViewById(R.id.search_bar11);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Existinguser_main.this.MAdap.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        assert getSupportActionBar()!=null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Bundle extras = getIntent().getExtras();
            if(extras !=null)
            {
                startActivity(new Intent(this, Report.class));
            }
            else
                {
                    startActivity(new Intent(this, Detail.class));
                }
        }
        return super.onOptionsItemSelected(item);
    }

}