package com.ucelebi.automobile.dto;

import java.sql.Timestamp;

public class AddressDTO {
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    private boolean active;
    private String code;
    private String postalCode;
    private String countryCode;
    private String cityCode;
    private String townCode;
    private String streetName;
    private String streetNumber;
    private String line;

    public AddressDTO() {}

    public AddressDTO(String code,
                      String postalCode,
                      String countryCode,
                      String cityCode,
                      String townCode,
                      String streetName,
                      String streetNumber,
                      String line) {
        this.code = code;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.townCode = townCode;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.line = line;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }
}
