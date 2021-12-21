package com.sipro.mysipro;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;


//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class Login extends Activity {

    private JSONObject jObject;
    private String jsonResult ="";
    ProgressDialog pd;
    String your_JWT_Token="";

    EditText emailId, password;
    Button btnLogin;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private void saveToken(String newToken) {
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LoginToken", newToken);
        editor.apply();
    }

    private boolean checkToken() {
        //SharedPreferences sharedPref = getApplicationContext().getSharedPreferences();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);

       if (sharedPref.contains("LoginToken"))
       {

           String jwt = sharedPref.getString("LoginToken", "");
           Log.d ("param1", "token"+jwt);
           GlobalVar.jwt_token = jwt;

       }
        return sharedPref.contains("LoginToken");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (checkToken()) {
            Intent i = new Intent(Login.this, Beranda.class);
            startActivity(i);
        }

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {

            Intent berandaIntent = new Intent(Login.this, Beranda.class);
            startActivity(berandaIntent);
            finish();
        }

        emailId = findViewById(R.id.loginNik);
        password = findViewById(R.id.loginPass);
        btnLogin = findViewById(R.id.btlogin);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(Login.this, "Berhasil login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Beranda.class);
                    startActivity(i);

                } else {
                    Toast.makeText(Login.this, "Silahkan login", Toast.LENGTH_SHORT).show();

                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();

                JSONObject params = null;
                params = new JSONObject();
                try {
                    params.put("email", email);
                    params.put("password", pwd);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                cek_login(params);

                if (email.isEmpty()) {
                    emailId.setError("Isi email terlebih dahulu");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()){
                    password.setError("Isi password terlebih dahulu");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(Login.this, "Cek kembali email dan password", Toast.LENGTH_SHORT).show();
                }

                else {
                    //Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    public void cek_login(JSONObject data){
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, "http://siprobalangankab.com/public/api/v1/login",data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(Login.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            //JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String message = response.getString("message");
                            String jwt = response.getJSONObject("data").getString("access_token");
                            your_JWT_Token = jwt;
                            GlobalVar.jwt_token = jwt;
                            saveToken(jwt);


                           // Toast.makeText(Login.this, your_JWT_Token, Toast.LENGTH_SHORT).show();
                            Toast.makeText(Login.this, "Berhasil login", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Login.this, Beranda.class);
                            startActivity(i);

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, "Cek kembali email dan password", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requstQueue.add(jsonobj);
    }
}

