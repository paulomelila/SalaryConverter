package com.example.paulo.salaryconverter;

/**
 * Created by kaorihirata on 2017-06-07.
 */

public class Country {
    private String countryName;
    private int countryImg;

    public Country(String countryName,int countryImg){
        this.countryName=countryName;
        this.countryImg=countryImg;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryImg() {
        return countryImg;
    }

    public void setCountryImg(int countryImg) {
        this.countryImg = countryImg;
    }
}
