package com.example.mega2.final_livw_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Session = "sessKey";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etSaveIp;
    private String username;
    private String password;
    private String ipsave;
    private ProgressDialog pDialog;
    MyPojo_main pojo = new MyPojo_main();
    private String login_url = pojo.JURLLogin.toString();
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etLoginUsername);
        etPassword = (EditText) findViewById(R.id.etLoginPassword);
        etSaveIp = (EditText) findViewById(R.id.eTsaveip);

        Button register = (Button) findViewById(R.id.btnLoginRegister);
        Button login = (Button) findViewById(R.id.btnLogin);
        Button saveip = (Button) findViewById(R.id.btnsaveip);

        //Launch Registration screen when Register Button is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                ipsave = etSaveIp.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });
        /*
        saveip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etSaveIp.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });
        */
    }

    /**
     * Launch Dashboard Activity on Successful Login
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), mainmenu1.class);
        startActivity(i);
        finish();

    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username,response.getString(KEY_FULL_NAME),ipsave);
                                loadDashboard();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}
