package com.ucelebi.automobile.modelFilter;

public class PartnerFilter {
    private String sectorCode;
    private String cityCode;
    private String townCode;
    private double latitude;
    private double longitude;

    public PartnerFilter(String sectorCode, String cityCode, String townCode, double latitude, double longitude) {
        this.sectorCode = sectorCode;
        this.cityCode = cityCode;
        this.townCode = townCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSectorCode() {
        return sectorCode;
    }
    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getTownCode() {
        return townCode;
    }
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
