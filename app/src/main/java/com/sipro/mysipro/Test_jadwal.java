package com.sipro.mysipro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

public class Test_jadwal extends Activity {

    private JSONObject jObject;
    private String jsonResult ="";
    ProgressDialog pd;

    private static final String TAG = "MyActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  CollectionReference schedRef = db.collection("Schedules");

    private TextView jadJudul, jadStart,jadDesc, jadWaktu;
    private Button btLoad, btBackJadwal;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jadwal);

      jadDesc = findViewById(R.id.jaddesc);
     btLoad = findViewById(R.id.load2);
     btBackJadwal = findViewById(R.id.backJadwal);
//     jadWaktu = findViewById(R.id.jadwaktu);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        TextView textView1 = findViewById(R.id.tanggaljad);
        textView1.setText(formattedDate);

        btBackJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iJadwalBack = new Intent(Test_jadwal.this, Beranda.class);
                startActivity(iJadwalBack);
            }
        });
    }

    public void get_protected_data(View view) {

        StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/meeting", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jo = new JSONObject(response);
                    JSONArray arr = jo.getJSONObject("data").getJSONArray("peserta_rapat");

                    String desc ="";
                    String waktu = "";
                    for (int i = 0; i < arr.length(); i++) {

                                String vdesc = arr.getJSONObject(i).getString("judul_rapat"); //detailjadwal.getTitle();
                                String vlocation =  arr.getJSONObject(i).getString("lokasi_rapat");; //detailjadwal.getLocation();
                                String vtstart =  arr.getJSONObject(i).getString("waktu_rapat");
                                String vttanggal =  arr.getJSONObject(i).getString("tanggal_rapat");

                            //convert
                            String strDate = "";
                            String strDateb = "";
                            String strEnd ="";


                            desc += vdesc
                                    + "\n"+ vlocation
                                    + "\n" +vttanggal
                                    + "\n" +vtstart
                                    + "\n\n";

//                            desc += vdesc
//
//                                    +"\n\n";
//
//                            waktu += vlocation
//                                    +"\n" + vttanggal
//                                    +"\n" + vtstart
//                                     +"\n\n";
                        }
                        jadDesc.setText(desc);
//                    jadWaktu.setText(waktu);

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
                        Toast.makeText(Test_jadwal.this, "Error", Toast.LENGTH_SHORT).show();
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

        pd = ProgressDialog.show(Test_jadwal.this, "Please Wait", "Connecting", true);
        pd.setCancelable(true);

        Volley.newRequestQueue(this).add(PostRequest);
    }

}