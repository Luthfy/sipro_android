package com.sipro.mysipro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Beranda extends Activity {

    private static int WELCOME_TIMEOUT = 850;
    Button btlogin, btdata, btjadwal, btpresensi, btkehadiran, btlogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    private void delToken(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("LoginToken");
        editor.remove("logMasuk");
        editor.remove("logKeluar");
        editor.apply();


    }

    private boolean checkToken() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);

        if (!sharedPref.contains("LoginToken"))
        {
            Intent inToLogin = new Intent(Beranda.this, Login.class);
            startActivity(inToLogin);
        }
        return sharedPref.contains("LoginToken");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        setUpFcmToken();
        checkToken();

        btlogout = findViewById(R.id.logout);
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delToken();
                Intent inToLogin = new Intent(Beranda.this, Login.class);
                startActivity(inToLogin);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //intent data page
                btdata = findViewById(R.id.data);
                btdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent1 = new Intent(Beranda.this, Data.class);
                        Intent intent1 = new Intent(Beranda.this, ListDewanActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });

                //intent jadwal page
                btjadwal = findViewById(R.id.jadwal);
                btjadwal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(Beranda.this, Test_jadwal.class);
                        startActivity(intent2);
                        // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


                //intent kehadiran page
                btkehadiran = findViewById(R.id.kehadiran);
                btkehadiran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent(Beranda.this, Kehadiran.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });

                //intent presensi paga
                btpresensi = findViewById(R.id.presensi);
                btpresensi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent4 = new Intent(Beranda.this, Presensi.class);
                        startActivity(intent4);
                        // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });

            }
        }, WELCOME_TIMEOUT);


    }


    private void setUpFcmToken() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                final String newToken = instanceIdResult.getToken();

                JSONObject param = new JSONObject();

                try {
                    param.put("fcm", newToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestQueue requstQueue = Volley.newRequestQueue(Beranda.this);

                JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, "http://siprobalangankab.com/public/api/v1/fcm",param,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("newToken", newToken);

                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Beranda.this, error.toString(), Toast.LENGTH_SHORT).show();
                                //
                            }
                        }
                ){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        return params;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer "+GlobalVar.jwt_token );
                        return params;
                    }
                };
                requstQueue.add(jsonobj);

            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
