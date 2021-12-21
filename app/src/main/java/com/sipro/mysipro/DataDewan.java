package com.sipro.mysipro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;
import android.app.ProgressDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



import com.google.android.material.button.MaterialButtonToggleGroup;


public class DataDewan extends Activity {

    private JSONObject jObject;
    private String jsonResult ="";
    ProgressDialog pd;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Button dataPimpinan, dataKelengkapan, dataFraksi,
                    dataKomisi, dataPartai, btCari;

    private TextView txTampilNama, txTampilSub;

    Spinner spStruktur;


    private DewanAdapter adapter;
    private ArrayList<Dewan> dewanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_dewan);

        txTampilNama = findViewById(R.id.tampilNama);
        txTampilSub = findViewById(R.id.tampilSub);
        spStruktur = (Spinner) findViewById(R.id.spDataDewan);
        btCari = findViewById(R.id.btCariData);


        //dataPimpinan = findViewById(R.id.btDataPimpinan);
        //dataKelengkapan = findViewById(R.id.btDataKelengkapan);

//       dataPimpinan = findViewById(R.id.btDataPimpinan);
//        dataPimpinan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent intent1 = new Intent(Beranda.this, Data.class);
//                Intent intent1 = new Intent(DataDewan.this, DataDewanPimpinan.class);
//                startActivity(intent1);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//            }
//        });
//
//        dataKelengkapan = findViewById(R.id.btDataKelengkapan);
//        dataKelengkapan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent intent1 = new Intent(Beranda.this, Data.class);
//                Intent intent2 = new Intent(DataDewan.this, DataDewanKelengkapan.class);
//                startActivity(intent2);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//            }
//        });
//
//        dataFraksi = findViewById(R.id.btDataFraksi);
//        dataFraksi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent intent1 = new Intent(Beranda.this, Data.class);
//                Intent intent3 = new Intent(DataDewan.this, DataDewanFraksi.class);
//                startActivity(intent3);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//            }
//        });
//
//        dataKomisi = findViewById(R.id.btDataKomisi);
//        dataKomisi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent intent1 = new Intent(Beranda.this, Data.class);
//                Intent intent4 = new Intent(DataDewan.this, DataDewanKomisi.class);
//                startActivity(intent4);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//            }
//        });
//
//        dataPartai = findViewById(R.id.btDataPartai);
//        dataPartai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent intent1 = new Intent(Beranda.this, Data.class);
//                Intent intent5 = new Intent(DataDewan.this, DataDewanPartai.class);
//                startActivity(intent5);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//
//            }
//        });


        final String isiStruktur[] = {
                "Pimpinan",
                "Fraksi",
                "Komisi",
                "Alat Kelengkapan Dewan",
                "Partai"

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, isiStruktur);
        spStruktur.setAdapter(adapter);

        spStruktur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                switch (i) {
                    /*Pimpinan*/
                    case 0:
                        btCari.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                               //txTampilNama.setText("0");
                               //txTampilSub.setText("0");


                                StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/structure/list_structure?jenis_struktur=pimpinan", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject jo = new JSONObject(response);
                                            JSONArray arr = jo.getJSONArray("data");
                                            //String nama ="";
                                           // String sub = "";
                                            String vnama ="";
                                            String vsub="";
                                            for (int i = 0; i < arr.length(); i++) {

                                                vnama += arr.getJSONObject(i).getJSONObject("user").getString("name")+"\n\n";
                                                vsub += arr.getJSONObject(i).getString("jabatan")+"\n\n";

                                                //nama += vnama + "\n\n";
                                                //sub += vsub + "\n\n";

                                            }

                                            //txTampilNamaPimpinan.setText(nama);
                                            //txTampilSubPimpinan.setText(sub);


                                            txTampilNama.setText(vnama);
                                            txTampilSub.setText(vsub);


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
                                                Toast.makeText(DataDewan.this, "Error", Toast.LENGTH_SHORT).show();
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

                                pd = ProgressDialog.show(DataDewan.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(DataDewan.this).add(PostRequest);

                            }

                        });

                        break;

                    case 1:
                        btCari.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //txTampilNama.setText("0");
                                //txTampilSub.setText("0");


                                StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/structure/list_structure?jenis_struktur=fraksi", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject jo = new JSONObject(response);
                                            JSONArray arr = jo.getJSONArray("data");
                                            //String nama ="";
                                            //String sub = "";

                                            String vnama ="";
                                            String vsub = "";
                                            for (int i = 0; i < arr.length(); i++) {

                                                //String vnama = arr.getJSONObject(i).getJSONObject("user").getString("name");
                                                //String vsub = arr.getJSONObject(i).getString("sub_jenis_struktur");

                                                 vnama += arr.getJSONObject(i).getJSONObject("user").getString("name")+"\n\n";
                                                 vsub += arr.getJSONObject(i).getString("sub_jenis_struktur")+"\n\n";

                                                //nama += vnama + "\n\n";
                                                //sub += vsub + "\n\n";

                                            }


                                           // txTampilNama.setText(nama);
                                           // txTampilSub.setText(sub);

                                            txTampilNama.setText(vnama);
                                            txTampilSub.setText(vsub);

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
                                                Toast.makeText(DataDewan.this, "Error", Toast.LENGTH_SHORT).show();
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

                                pd = ProgressDialog.show(DataDewan.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(DataDewan.this).add(PostRequest);

                            }

                        });

                        break;

                    case 2:
                        btCari.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //txTampilNama.setText("0");
                                //txTampilSub.setText("0");


                                StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/structure/list_structure?jenis_struktur=komisi", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject jo = new JSONObject(response);
                                            JSONArray arr = jo.getJSONArray("data");
                                            String nama ="";
                                            String sub = "";
                                            for (int i = 0; i < arr.length(); i++) {

                                                String vnama = arr.getJSONObject(i).getJSONObject("user").getString("name");
                                                String vsub = arr.getJSONObject(i).getString("sub_jenis_struktur");

                                                nama += vnama + "\n\n";
                                                sub += vsub + "\n\n";

                                            }


                                            txTampilNama.setText(nama);
                                            txTampilSub.setText(sub);


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
                                                Toast.makeText(DataDewan.this, "Error", Toast.LENGTH_SHORT).show();
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

                                pd = ProgressDialog.show(DataDewan.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(DataDewan.this).add(PostRequest);

                            }

                        });

                        break;

                    case 3:
                        btCari.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //txTampilNama.setText("0");
                                //txTampilSub.setText("0");


                                StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/structure/list_structure?jenis_struktur=akd", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject jo = new JSONObject(response);
                                            JSONArray arr = jo.getJSONArray("data");
                                            String nama ="";
                                            String sub = "";
                                            for (int i = 0; i < arr.length(); i++) {

                                                String vnama = arr.getJSONObject(i).getJSONObject("user").getString("name");
                                                String vsub = arr.getJSONObject(i).getString("sub_jenis_struktur");

                                                nama += vnama + "\n\n";
                                                sub += vsub + "\n\n";

                                            }


                                            txTampilNama.setText(nama);
                                            txTampilSub.setText(sub);


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
                                                Toast.makeText(DataDewan.this, "Error", Toast.LENGTH_SHORT).show();
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

                                pd = ProgressDialog.show(DataDewan.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(DataDewan.this).add(PostRequest);

                            }

                        });

                        break;

                    case 4:
                        btCari.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //txTampilNama.setText("0");
                                //txTampilSub.setText("0");


                                StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/structure/list_structure?jenis_struktur=partai", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject jo = new JSONObject(response);
                                            JSONArray arr = jo.getJSONArray("data");
                                            String nama ="";
                                            String sub = "";
                                            for (int i = 0; i < arr.length(); i++) {

                                                String vnama = arr.getJSONObject(i).getJSONObject("user").getString("name");
                                                String vsub = arr.getJSONObject(i).getString("sub_jenis_struktur");

                                                nama += vnama + "\n\n";
                                                sub += vsub + "\n\n";

                                            }


                                            txTampilNama.setText(nama);
                                            txTampilSub.setText(sub);


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
                                                Toast.makeText(DataDewan.this, "Error", Toast.LENGTH_SHORT).show();
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

                                pd = ProgressDialog.show(DataDewan.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(DataDewan.this).add(PostRequest);

                            }

                        });

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });


    }

}