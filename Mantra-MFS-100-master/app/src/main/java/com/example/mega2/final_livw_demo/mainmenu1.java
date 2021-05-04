package com.example.mega2.final_livw_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mainmenu1 extends AppCompatActivity {
    private SessionHandler session;
    Db_Helper_main db=new Db_Helper_main(mainmenu1.this);
    MFS100_final_main mfs = new MFS100_final_main();
    public void clkEnroll(View view) {
        Intent intent = new Intent(mainmenu1.this, NewUserMain.class);
        startActivity(intent);

    }
    public void clkMarkAttendance(View view) {
        Intent intent = new Intent(mainmenu1.this, Class_selector.class);
        startActivity(intent);

    }
    public void clkAbout(View view) {
        Intent intent = new Intent(mainmenu1.this, SubActivity_Main.class);
        //Intent intent = new Intent(mainmenu1.this, FBUpload.class);
        startActivity(intent);

        ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_new);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        TextView welcomeText = (TextView) findViewById(R.id.welcomeText);


        welcomeText.setText("Welcome "+user.getFullName()+", your session will expire on "+user.getSessionExpiryDate());

        Button logoutBtn = (Button) findViewById(R.id.buttonLogout);
        Button blobFunc = (Button) findViewById(R.id.buttonFunc);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(mainmenu1.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        blobFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                byte[] buffer = db.retrieveISO(6,"19","fs","fgd");
                mfs.WriteFile("Translate.iso1",buffer);
                */
            }
        });
    }

}
