package com.example.mega2.final_livw_demo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_settings);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        setupViewpager(viewPager);
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }
    private void setupViewpager(ViewPager viewPager)
    {
        sectionpageadapter sectionpageadapter = new sectionpageadapter(getSupportFragmentManager());
        sectionpageadapter.addfragment(new Tab1(),"ADMIN/HR CONTROL");
        sectionpageadapter.addfragment(new Tab2(),"FEATURE CONTROL");
        sectionpageadapter.addfragment(new Tab3(),"FIXED CONTROL");
        viewPager.setAdapter(sectionpageadapter);
    }

}

