package com.hirebuddy.bean;

/**
 * Created by mobulous20 on 27/7/16.
 */
public class CountryCodeBean {
    private String countryCode;
    private String countryName;
    private String countryIsoCode;


    public CountryCodeBean(String countryCode, String countryIsoCode, String countryName){
        this.countryCode = countryCode;
        this.countryIsoCode =countryIsoCode;
        this.countryName =countryName;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }



}
