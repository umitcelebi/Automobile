package com.ucelebi.automobile.dto;

import com.ucelebi.automobile.enums.Role;

import java.sql.Timestamp;

public class CustomerDTO {
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    private boolean active;
    private String uid;
    private String name;
    private String displayName;
    private String phoneNumber;
    private String profilePhoto;
    private Role role;

    public CustomerDTO() {}
    public CustomerDTO(Timestamp creationTime,
                       Timestamp modifiedTime,
                       boolean active,
                       String uid,
                       String name,
                       String displayName,
                       String phoneNumber,
                       String profilePhoto,
                       Role role) {
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
        this.uid = uid;
        this.name = name;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.role = role;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
