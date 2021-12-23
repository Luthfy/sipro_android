package com.sipro.mysipro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.sipro.mysipro.dewan.Dewan;
import com.sipro.mysipro.dewan.DewanAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListDewanActivity extends AppCompatActivity {

    private JSONObject jObject;
    private String jsonResult = "";
    ProgressDialog pd;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Button dataPimpinan, dataKelengkapan, dataFraksi,
            dataKomisi, dataPartai, btCari;
    private RecyclerView rcDewan;
    private TextView txTampilNama, txTampilSub;

    Spinner spStruktur;
    private DewanAdapter adapter;
    private ArrayList<Dewan> dewanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dewan);

        rcDewan = (RecyclerView) findViewById(R.id.rc_dewan);

        txTampilNama = findViewById(R.id.tampilNama);
        txTampilSub = findViewById(R.id.tampilSub);
        spStruktur = (Spinner) findViewById(R.id.spDataDewan);
        btCari = findViewById(R.id.btCariData);

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
                                            String vnama = "";
                                            String vsub = "";
                                            for (int i = 0; i < arr.length(); i++) {

                                                vnama += arr.getJSONObject(i).getJSONObject("user").getString("name") + "\n\n";
                                                vsub += arr.getJSONObject(i).getString("jabatan") + "\n\n";

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
                                                Toast.makeText(ListDewanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                ) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();

                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", "Bearer " + GlobalVar.jwt_token);
                                        return params;
                                    }
                                };

                                pd = ProgressDialog.show(ListDewanActivity.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(ListDewanActivity.this).add(PostRequest);

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

                                            String vnama = "";
                                            String vsub = "";
                                            for (int i = 0; i < arr.length(); i++) {

                                                //String vnama = arr.getJSONObject(i).getJSONObject("user").getString("name");
                                                //String vsub = arr.getJSONObject(i).getString("sub_jenis_struktur");

                                                vnama += arr.getJSONObject(i).getJSONObject("user").getString("name") + "\n\n";
                                                vsub += arr.getJSONObject(i).getString("sub_jenis_struktur") + "\n\n";

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
                                                Toast.makeText(ListDewanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                ) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();

                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", "Bearer " + GlobalVar.jwt_token);
                                        return params;
                                    }
                                };

                                pd = ProgressDialog.show(ListDewanActivity.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(ListDewanActivity.this).add(PostRequest);

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
                                            String nama = "";
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
                                                Toast.makeText(ListDewanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                ) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();

                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", "Bearer " + GlobalVar.jwt_token);
                                        return params;
                                    }
                                };

                                pd = ProgressDialog.show(ListDewanActivity.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(ListDewanActivity.this).add(PostRequest);

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
                                            String nama = "";
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
                                                Toast.makeText(ListDewanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                ) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();

                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", "Bearer " + GlobalVar.jwt_token);
                                        return params;
                                    }
                                };

                                pd = ProgressDialog.show(ListDewanActivity.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(ListDewanActivity.this).add(PostRequest);

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

                                            ArrayList<Dewan> listData = new ArrayList<Dewan>();
                                            if (arr != null) {
                                                for (int i = 0; i < arr.length(); i++) {
                                                    listData.add((Dewan) arr.get(i));
                                                }
                                            }

                                            setRecyclerView(listData);

                                            String nama = "";
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
                                                Toast.makeText(ListDewanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                ) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();

                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", "Bearer " + GlobalVar.jwt_token);
                                        return params;
                                    }
                                };

                                pd = ProgressDialog.show(ListDewanActivity.this, "Please Wait", "Connecting", true);
                                pd.setCancelable(true);

                                Volley.newRequestQueue(ListDewanActivity.this).add(PostRequest);

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

    public void setRecyclerView(ArrayList<Dewan> data_dewan)
    {
        adapter = new DewanAdapter(data_dewan);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListDewanActivity.this);

        rcDewan.setLayoutManager(layoutManager);

        rcDewan.setAdapter(adapter);
    }

}
