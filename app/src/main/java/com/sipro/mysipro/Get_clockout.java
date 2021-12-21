package com.sipro.mysipro;

public class Get_clockout {

    private String co_tanggal, co_waktu;

    public Get_clockout(){}

    public Get_clockout(String co_tanggal, String co_waktu){
        this.co_tanggal = co_tanggal;
        this.co_waktu = co_waktu;

    }

    public String getCo_tanggal() {
        return co_tanggal;
    }

    public String getCo_waktu() {
        return co_waktu;
    }

}
