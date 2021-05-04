package com.example.mega2.final_livw_demo;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class FBUpload {

    StorageReference storageReference;

    public void onCreate(Bundle savedInstanceState) {

        uploadFile();
        /*
        File file1 = null;
        try {
            file1 = File.createTempFile("test2", "txt");
        } catch( IOException e ) {

        }
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file1));
        */


    }
    public void uploadFile() {
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl("gs://aasb-abe4c.appspot.com/").child("test2.txt");

            File file = null;
            try {
                file = File.createTempFile("test2", "txt");
            } catch( IOException e ) {

            }
            UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));
        }
        catch (Exception e2) {
            e2.printStackTrace();
            //Toast.makeText(getApplicationContext(),e1.toString(),Toast.LENGTH_LONG).show();
        }


    }





}
