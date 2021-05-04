package com.example.mega2.final_livw_demo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SubActivity extends AppCompatActivity {
    ImageView imageView1, imageView2, imageView3, imageview4;
    TextView Title, textView1, textView2, textView3, textview4;
    CardView cardView1, cardView2, cardView3, cardView4;
    Animation animation1, animation2, animation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_sub);
        imageView1 = (ImageView) findViewById(R.id.user);
        imageView2 = (ImageView) findViewById(R.id.set);
        imageView3 = (ImageView) findViewById(R.id.report);
        imageview4 = (ImageView) findViewById(R.id.maintenance);

        textView1 = (TextView) findViewById(R.id.textView1a);
        textView2 = (TextView) findViewById(R.id.textView2a);
        textView3 = (TextView) findViewById(R.id.textView3a);
        textview4 = (TextView) findViewById(R.id.textview4a);
        Title = (TextView) findViewById(R.id.heading);

        cardView1 = (CardView) findViewById(R.id.card1);
        cardView2 = (CardView) findViewById(R.id.card2);
        cardView3 = (CardView) findViewById(R.id.card3);
        cardView4 = (CardView) findViewById(R.id.card4);

        animation1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.itranslate);
        animation3 = AnimationUtils.loadAnimation(this, R.anim.rtranslate);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(1);
        rotate.setInterpolator(new LinearInterpolator());

        imageView1.startAnimation(rotate);
        imageView2.startAnimation(rotate);
        imageView3.startAnimation(rotate);
        imageview4.startAnimation(rotate);

        Title.startAnimation(animation1);

        textView1.startAnimation(animation1);
        textView2.startAnimation(animation1);
        textView3.startAnimation(animation1);
        textview4.startAnimation(animation1);

        cardView1.startAnimation(animation2);
        cardView2.startAnimation(animation3);
        cardView3.startAnimation(animation2);
        cardView4.startAnimation(animation3);

        assert getSupportActionBar()!=null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void user(View V) {
        Intent i = new Intent(this, Detail.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    public void Setting(View view) {
        Intent i = new Intent(this, Settings.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    public void Report(View view) {
        Intent i = new Intent(this, Report.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    public void preferances(View view) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.preferances_option);
            dialog.setTitle("Maintenance:-");
            final RadioGroup radio_g=(RadioGroup) dialog.findViewById(R.id.radio_g);
            TextView textView1=(TextView) dialog.findViewById(R.id.ok);
            TextView textView2=(TextView) dialog.findViewById(R.id.cancel);
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int selected_id = radio_g.getCheckedRadioButtonId();
                        RadioButton button = (RadioButton) dialog.findViewById(selected_id);
                        String text = button.getText().toString();
                        if (text.toUpperCase().trim().equals("EXPORT DB")) {
                            exportDB();
                            dialog.dismiss();
                        } else if (text.toUpperCase().trim().equals("IMPORT DB")) {
                            new FileChooser(SubActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
                                @Override
                                public void fileSelected(final File file) {
                                    String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(file.getName());
                                    if (ext.toUpperCase().trim().equals("")) {
                                        importDB(file);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please select a valid file !!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).showDialog();
                            dialog.dismiss();
                        }
                    }catch (Exception e)
                    {dialog.dismiss();}

                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }catch (Exception e)
        {
           Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    private void importDB(File file) {
        FileChannel source,destination;
        File backupDB = getDatabasePath("Megamind.db");
        try {
            source = new FileInputStream(file).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Imported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) { Toast.makeText(this, "DB Exported exception! " + e.getMessage(), Toast.LENGTH_LONG).show(); }
    }

    private void exportDB() {
        String date = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss", Locale.UK).format(new Date());
        FileChannel source,destination;
        String path = Environment.getExternalStorageDirectory() + "//Megamind//BackUp//";
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        String backupDBPath = path+"BCKUP-"+date+".db";
        File currentDB = getDatabasePath("Megamind.db");
        File backupDB = new File(backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) { Toast.makeText(this, "DB Exported exception! " + e.getMessage(), Toast.LENGTH_LONG).show(); }
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
            startActivity(new Intent(this, Main_screen.class));
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this, Detail.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
