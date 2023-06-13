package com.ucelebi.automobile.dto;

import java.sql.Timestamp;

public class CustomerListDTO {
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    private boolean active;
    private String uid;
    private String displayName;
    private String profilePhoto;

    public CustomerListDTO() {}

    public CustomerListDTO(Timestamp creationTime,
                           Timestamp modifiedTime,
                           boolean active,
                           String uid,
                           String displayName,
                           String profilePhoto) {
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
        this.uid = uid;
        this.displayName = displayName;
        this.profilePhoto = profilePhoto;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
