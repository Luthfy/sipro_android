package com.sipro.mysipro;

public class Get_clockin {

    private String ci_tanggal, ci_waktu, ci_timestamp;

    public Get_clockin(){

    }


    public Get_clockin(String ci_tanggal, String ci_waktu, String ci_timestamp) {
        this.ci_tanggal = ci_tanggal;
        this.ci_waktu = ci_waktu;
        this.ci_timestamp = ci_timestamp;

    }

    public String getCi_tanggal() {
        return ci_tanggal;
    }

    public String getCi_waktu() {
        return ci_waktu;
    }

    public String getCi_timestamp() {
        return ci_timestamp;
    }

}
