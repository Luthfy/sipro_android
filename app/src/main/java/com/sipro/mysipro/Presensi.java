package com.sipro.mysipro;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Presensi extends Activity {

    private JSONObject jObject;
    private String jsonResult ="";
    ProgressDialog pd;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView txCuser, txTampilCiWaktu, txTampilCiTanggal, txTampilCoWaktu, txMm, txYyyy, txCiDate;
    private Button btLoadPresensi, btBackPresensi;
    private Spinner spBulan, spTahun;

    public static String convert(int n){
        return n < 10 ? "0" + n : "" + n;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        txTampilCiWaktu = findViewById(R.id.tampilCiWaktu);
        txTampilCoWaktu = findViewById(R.id.tampilCoWaktu);
        txTampilCiTanggal = findViewById(R.id.tampilCiTanggal);
        txMm = findViewById(R.id.mm);
        txYyyy = findViewById(R.id.yyyy);
        btLoadPresensi = findViewById(R.id.loadPres);
        btBackPresensi = findViewById(R.id.backPresensi);

//        spBulan = (Spinner) findViewById(R.id.presBulan);
//        spTahun = (Spinner) findViewById(R.id.presTahun);

        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();


        btBackPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPresensiBack = new Intent(Presensi.this, Beranda.class);
                startActivity(iPresensiBack);

            }
        });

        final String isiBulan[] = {
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
        };

        final String isiTahun[] = {
                "2020",
                "2021",
                "2022",
                "2023",
                "2024"

        };

//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, isiBulan);
//        spBulan.setAdapter(adapter);
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, isiTahun);
//        spTahun.setAdapter(adapter1);

//        //spinner bulan
//        spBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String bulanSelect = isiBulan[position];
//
//                /*Jan*/
//                if (position == 0) {
//                    txMm.setText("01");
//
//                }
//                /*Feb*/
//                if (position == 1) {
//                    txMm.setText("02");
//                }
//                /*Mar*/
//                if (position == 2) {
//                    txMm.setText("03");
//                }
//                /*Apr*/
//                if (position == 3) {
//                    txMm.setText("04");
//                }
//                /*Mei*/
//                if (position == 4) {
//                    txMm.setText("05");
//                }
//                /*Jun*/
//                if (position == 5) {
//                    txMm.setText("06");
//                }
//                /*Jul*/
//                if (position == 6) {
//                    txMm.setText("07");
//                }
//                /*Agt*/
//                if (position == 7) {
//                    txMm.setText("08");
//                }
//                /*Sept*/
//                if (position == 8) {
//                    txMm.setText("09");
//                }
//                /*Okt*/
//                if (position == 9) {
//                    txMm.setText("10");
//                }
//                /*Nov*/
//                if (position == 10) {
//                    txMm.setText("11");
//                }
//                /*Dec*/
//                if (position == 11) {
//                    txMm.setText("12");
//                }
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        //spinner tahun
//        spTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String tahunSelect = isiTahun[position];
//
//                /*2020*/
//                if (position == 0) {
//                    txYyyy.setText("2020");
//                }
//                /*2021*/
//                if (position == 1) {
//                    txYyyy.setText("2021");
//                }
//                /*2022*/
//                if (position == 2) {
//                    txYyyy.setText("2022");
//                }
//                /*2023*/
//                if (position == 3) {
//                    txYyyy.setText("2023");
//                }
//                /*2024*/
//                if (position == 4) {
//                    txYyyy.setText("2024");
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });
    }

//    public void loadCi(View v) {
//
//        //get userid
//        final String current_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        txCuser = (TextView) findViewById(R.id.cuser);
//        txCuser.setText(current_user);
//
//        String callMm = txMm.getText().toString();
//        String callYy = txYyyy.getText().toString();
//
////        CollectionReference ciRef = db.collection("Users").document("User"+current_user).collection("Attendance");
//        CollectionReference ciRef = db.collection("Users");
//
//                //clock in
//                ciRef.document("User"+current_user)
//                .collection("Attendance")
////                        .whereEqualTo("type","Clock In")
////                        .orderBy("ci_ts", Query.Direction.ASCENDING)
//                        .whereEqualTo("ci_yyyy", callYy)
//                        .whereEqualTo("ci_mm", callMm)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            String aCiWaktu = "";
//                            String aCiTanggal = "";
//
//                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
//                            {
//                                Get_clockin gci = documentSnapshot.toObject(Get_clockin.class);
//                                String ciWaktuA = gci.getCi_waktu();
//                                String ciTanggalA = gci.getCi_timestamp();
//                                aCiWaktu += ciWaktuA + "\n\n";
//                                aCiTanggal += ciTanggalA+"\n\n";
//                            }
//                            txTampilCiWaktu.setText(aCiWaktu);
//                            txTampilCiTanggal.setText(aCiTanggal);
//
////                            //get ci date
////                            String getCiDate = txTampilCiTanggal.getText().toString();
////                            txCiDate = (TextView) findViewById(R.id.getCiDateA);
////                            txCiDate.setText(getCiDate);
//
//                        }
//                    });
//
////                //clock out
//                ciRef.
//                        document("User"+current_user)
//                        .collection("Attendance")
////                        .whereEqualTo("type","Clock Out")
////                        .orderBy("co_ts", Query.Direction.ASCENDING)
//                       .whereEqualTo("co_yyyy", callYy)
//                       .whereEqualTo("co_mm", callMm)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String aCoWaktu = "";
//
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
//                        {
//                            Get_clockout gco = documentSnapshot.toObject(Get_clockout.class);
//                            String CoWaktuA = gco.getCo_waktu();
//                            aCoWaktu += CoWaktuA + "\n\n";
//                        }
//                        txTampilCoWaktu.setText(aCoWaktu);
//                    }
//                });
//
//    }

    @Override
    public void onBackPressed() {
        Intent intent0 = new Intent(Presensi.this, Beranda.class);
        startActivity(intent0);
    }

    public void loadCi(View v) {

//        String bln = "" + convert(spBulan.getSelectedItemPosition()+1);
//        Toast.makeText(getApplicationContext(), bln, Toast.LENGTH_LONG).show();
//
//        String thn = "" + (spBulan.getSelectedItemPosition()+2020);
//        Toast.makeText(getApplicationContext(), thn, Toast.LENGTH_LONG).show();
//
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = null;
//        try {
//            today = sdf.parse(thn+"-"+bln+"-"+"01");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        //Date today = new Date();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//
//        calendar.add(Calendar.MONTH, 1);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.add(Calendar.DATE, -1);
//
//        Date firstDayOfMonth = calendar.getTime();
//
//        DateFormat sdf2 = new SimpleDateFormat("MM");

        //System.out.println("Today            : " + sdf.format(today));
        //System.out.println("Last Day of Month: " + sdf.format(firstDayOfMonth));



        StringRequest PostRequest = new StringRequest(Request.Method.GET, "http://siprobalangankab.com/public/api/v1/attendence?first=2021-01-01&last=2025-12-25", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

//                    String jwt = response.getJSONObject("data").getString("access_token");
                    JSONObject jo = new JSONObject(response);
                    JSONArray arr = jo.getJSONArray("data");

                    String desc ="";
                    String aCiWaktuMasuk = "";
                    String aCiTanggal = "";
                    String aCiWaktuKeluar = "";



//                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                                Detailjadwal detailjadwal = documentSnapshot.toObject(Detailjadwal.class);
                    for (int i = 0; i < arr.length(); i++) {

                        aCiTanggal += arr.getJSONObject(i).getString("tanggal_masuk") + "\n\n";
                        aCiWaktuMasuk += arr.getJSONObject(i).getString("waktu_masuk")+"\n\n";
                        aCiWaktuKeluar += arr.getJSONObject(i).getString("waktu_keluar")+"\n\n";

//                            tstart = vstart;
//                            dstart = (vstart.toDate());
//
//                            tastart = vstart;
//                            dastart = (vtstart.toDate());
//
//                            tbstart = vstart;
//                            dbend = (vtend.toDate());
//
//
//
//                            //create object
//                            Date datea = new Date();
//                            datea = dstart;
//
//                            Date dateb = new Date();
//                            dateb = dastart;
//
//                            Date datec = new Date();
//                            datec = dbend;
//
//                            //set date format
//                            TimeZone utcZone = TimeZone.getTimeZone("Asia/Singapore");
//
//                            SimpleDateFormat sdfa = new SimpleDateFormat("dd-MMMM-yyyy");
//                            sdfa.setTimeZone(utcZone);
//
//                            SimpleDateFormat sdfb = new SimpleDateFormat("HH:mm");
//                            sdfb.setTimeZone(utcZone);

                        //convert
                        String strDate = ""; //sdfa.format(datea);
                        String strDateb = "";  //sdfb.format(dateb);
                        String strEnd =""; //sdfb.format(datec);

//                                desc += vdesc;
//                                location += vlocation;
//                                waktu += vwaktu;

//                                desc += "Waktu : " + vwaktu
//                                        + "\nJudul : " + vdesc
//                                        + "\nLokasi : " + vlocation
//                                        + "\nJam Mulai : " +strDate
//                                        + "\nJam Selesai : "+strDate
//                                        + "\n\n";

                    }
                    txTampilCiWaktu.setText(aCiWaktuMasuk);
                    txTampilCiTanggal.setText(aCiTanggal);
                    txTampilCoWaktu.setText(aCiWaktuKeluar);

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
                        Toast.makeText(Presensi.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                //params.put("username", User); //not used
                //params.put("password", Pass); //not used

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+GlobalVar.jwt_token );
                return params;
            }
        };

        pd = ProgressDialog.show(Presensi.this, "Please Wait", "Connecting", true);
        pd.setCancelable(true);

        Volley.newRequestQueue(this).add(PostRequest);
    }


}