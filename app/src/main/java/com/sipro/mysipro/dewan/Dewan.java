package com.sipro.mysipro.dewan;

public class Dewan {

    private String imageUrl;
    private String namaDewan;
    private String jabatanDewan;

    public Dewan(String imageUrl, String namaDewan, String jabatanDewan) {
        this.imageUrl = imageUrl;
        this.namaDewan = namaDewan;
        this.jabatanDewan = jabatanDewan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNamaDewan() {
        return namaDewan;
    }

    public void setNamaDewan(String namaDewan) {
        this.namaDewan = namaDewan;
    }

    public String getJabatanDewan() {
        return jabatanDewan;
    }

    public void setJabatanDewan(String jabatanDewan) {
        this.jabatanDewan = jabatanDewan;
    }
}
