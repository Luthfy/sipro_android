package com.sipro.mysipro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pimpinan extends Activity {

    Button btPimpA, btPimpB, btPimpC, btBackPimp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pimpinan);

        //kembali ke beranda
        btBackPimp = (Button) findViewById(R.id.backPimpinan);
        btBackPimp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Pimpinan.this, Data.class);
                startActivity(intent1);
            }

        });


        //pimpinan satu
        btPimpA = (Button) findViewById(R.id.pimp1);
        btPimpA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Pimpinan.this, P_ifdali.class);
                startActivity(intent2);
            }

        });

        //pimpinan dua
        btPimpB = (Button) findViewById(R.id.pimp2);
        btPimpB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Pimpinan.this, P_ahsani.class);
                startActivity(intent3);
            }

        });


//        //pimpinan tiga
//        btPimpC = (Button) findViewById(R.id.pimp3);
//        btPimpC.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent4 = new Intent(Pimpinan.this, Pimp_b.class);
//                startActivity(intent4);
//            }
//
//        });







    }

    @Override
    public void onBackPressed() {
        Intent intent0 = new Intent(Pimpinan.this, Data.class);
        startActivity(intent0);
    }

}