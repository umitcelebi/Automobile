package com.ucelebi.automobile.dto;

import java.sql.Timestamp;

public class TownDTO {
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    private boolean active;
    private String code;
    private String name;
    private String cityCode;

    public TownDTO() {}

    public TownDTO(Timestamp creationTime, Timestamp modifiedTime, boolean active, String code, String name, String cityCode) {
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
        this.code = code;
        this.name = name;
        this.cityCode = cityCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
