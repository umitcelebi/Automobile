package com.ucelebi.automobile.dto;

import java.sql.Timestamp;

public class PartnerListDTO {
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    private boolean active;
    private String uid;
    private String displayName;
    private String profilePhoto;
    private Double userRating;
    private Double latitude;
    private Double longitude;
    private double distance;

    public PartnerListDTO() {}

    public PartnerListDTO(Timestamp creationTime, Timestamp modifiedTime, boolean active, String uid, String displayName, String profilePhoto, Double userRating, Double latitude, Double longitude) {
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
        this.uid = uid;
        this.displayName = displayName;
        this.profilePhoto = profilePhoto;
        this.userRating = userRating;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public PartnerListDTO(Timestamp creationTime, Timestamp modifiedTime, boolean active, String uid, String displayName, String profilePhoto, Double userRating, Double latitude, Double longitude, double distance) {
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
        this.uid = uid;
        this.displayName = displayName;
        this.profilePhoto = profilePhoto;
        this.userRating = userRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
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

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
