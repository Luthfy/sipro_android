package com.sipro.mysipro;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Kehadiran extends Activity  {

    ProgressDialog pd;
    String your_JWT_Token="";

    private Button btIn, btOut, btBackKehadiran;
    private TextView waktu, tanggalIn, tampilClockIn, tampilClockOut, tUi, attTypeCi, attTypeCo, txTodayDate;

    TextView txMm, txYy;

    private void saveKehadiran(String key, String status) {
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, status);
        editor.apply();
    }

    private String getKehadiran(String key){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);

        if (sharedPref.contains(key))
        {
            String status = sharedPref.getString(key, "");
            Log.d ("param1", "token"+status);
            return status;
        }

        return null;
    }

    private boolean getLog(String key) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("APPS", Context.MODE_PRIVATE);
        return sharedPref.contains(key);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);

        txMm = findViewById(R.id.tMm);
        txYy = findViewById(R.id.tYy);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        txTodayDate = (TextView) findViewById(R.id.todaydate);
        txTodayDate.setText(date);

        //get date
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        TextView textView1 = findViewById(R.id.tanggal);
        textView1.setText(formattedDate);

        btIn = (Button) findViewById(R.id.in);
        btOut = (Button) findViewById(R.id.out);
        tampilClockIn=(TextView)  findViewById(R.id.showClockIn);
        tampilClockOut=(TextView) findViewById(R.id.showClockOut);

        if (getLog("logMasuk")) {
            tampilClockIn.setText(getKehadiran("logMasuk"));
        }

        if (getLog("logKeluar")) {
            tampilClockOut.setText(getKehadiran("logKeluar"));
        }

        if ("masuk".equals(getKehadiran("statusKehadiran"))) {
            btIn.setEnabled(false);
        } else {
            btOut.setEnabled(false);
        }


//        mInputData = (EditText) findViewById(R.id.inputData);

        btBackKehadiran = findViewById(R.id.backKehadiran);
        btBackKehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iKehadiranBack = new Intent(Kehadiran.this, Beranda.class);
                startActivity(iKehadiranBack);

            }
        });

        waktu =(TextView) findViewById(R.id.time);
        tanggalIn = (TextView) findViewById(R.id.tanggal);
        attTypeCi = (TextView) findViewById(R.id.att_type_in);
        attTypeCo = (TextView) findViewById(R.id.att_type_out);

        //clock in
        btIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get date
                SimpleDateFormat fulldate = new SimpleDateFormat("yyyy-MM-dd");
                String fd = fulldate.format(new Date());
                txYy = (TextView) findViewById(R.id.tYy);
                txYy.setText(fd);
                Log.d("param1","tanggal"+fd);

                //get time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time1 = format.format(calendar.getTime());
                final TextView textView = findViewById(R.id.time);
                textView.setText(time1);
                Log.d("param1","jam"+time1);

                String inputWaktuCi = textView.getText().toString();
                String fulld = txYy.getText().toString();

                inputJamMasuk(fd,time1,"4321","4321");
                String logMasuk = inputWaktuCi+"                                                      Jam Masuk";
                saveKehadiran("logMasuk", logMasuk);
                tampilClockIn.setText(logMasuk);

                btIn.setEnabled(false);
                btOut.setEnabled(true);

                saveKehadiran("statusKehadiran", "masuk");
            }

        });


        //clock out
        btOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get date
                SimpleDateFormat fulldateo = new SimpleDateFormat("yyyy-MM-dd");
                String fdo = fulldateo.format(new Date());
                txMm = (TextView) findViewById(R.id.tMm);
                txMm.setText(fdo);

                //get Time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time2 = format.format(calendar.getTime());
                final TextView textView2 = findViewById(R.id.timeOut);
                textView2.setText(time2);

                String inputWaktuCo = textView2.getText().toString();
                String fulldo = txMm.getText().toString();

                inputJamKeluar(fdo,time2,"4321","4321");
                String logKeluar = inputWaktuCo+"                                                      Jam Keluar";
                saveKehadiran("logKeluar", logKeluar);
                tampilClockOut.setText(logKeluar);

                btOut.setEnabled(false);
                btIn.setEnabled(true);

                saveKehadiran("statusKehadiran", "keluar");

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent0 = new Intent(Kehadiran.this, Beranda.class);
        startActivity(intent0);
    }

    public void inputJamMasuk(final String tanggal_absensi, final String waktu_absensi, final String lat, final String lng) {

        Log.d("param1","tanggal_absensi"+tanggal_absensi);
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("tanggal_absensi", tanggal_absensi);
        postParam.put("waktu_absensi", waktu_absensi);
        postParam.put("lat", lat);
        postParam.put("lng", lng);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,

                "http://siprobalangankab.com/public/api/v1/attendence", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            GlobalVar.absenId = response.getJSONObject("data").getString("id");

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(getBaseContext(), "Gagal",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        pd.dismiss();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Kehadiran.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ GlobalVar.jwt_token);
                params.put("Content-Type", "application/json");
//                params.put("Accept", "application/json");
                return params;
            }
        };

        pd = ProgressDialog.show(Kehadiran.this, "Silahkan tunggu", "Proses input jam masuk", true);
        pd.setCancelable(true);

        Volley.newRequestQueue(this).add(jsonObjReq);

    }

    public void inputJamKeluar(final String tanggal_absensi, final String waktu_absensi, final String lat, final String lng) {

        Log.d("param1","tanggal_absensi"+tanggal_absensi);
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("tanggal_absensi", tanggal_absensi);
        postParam.put("waktu_absensi", waktu_absensi);
        postParam.put("lat", lat);
        postParam.put("lng", lng);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PATCH,

                "http://siprobalangankab.com/public/api/v1/attendence/"+GlobalVar.absenId, new JSONObject(postParam),
                new Response.Listener<JSONObject>()

                {
            @Override
            public void onResponse(JSONObject response) {
                try {

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getBaseContext(), "Gagal",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                pd.dismiss();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Kehadiran.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ GlobalVar.jwt_token);
                params.put("Content-Type", "application/json");
//                params.put("Accept", "application/json");
                return params;
            }
        };

        pd = ProgressDialog.show(Kehadiran.this, "Silahkan tunggu", "Proses input jam keluar", true);
        pd.setCancelable(true);

        Volley.newRequestQueue(this).add(jsonObjReq);
    }

}