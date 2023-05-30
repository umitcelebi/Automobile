package com.ucelebi.automobile.auth;

import com.ucelebi.automobile.enums.UserType;
import com.ucelebi.automobile.enums.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RegisterRequest {
    private String name;
    private String username;
    private String password;
    private Role role;
    private String phoneNumber;
    private MultipartFile profilePhoto;
    private String mail;
    private UserType userType;
    private Double latitude;
    private Double longitude;
    private boolean sundayOpen;
    private List<String> sectors;

    public RegisterRequest() {}

    public RegisterRequest(String name,
                           String username,
                           String password,
                           Role role,
                           String phoneNumber,
                           MultipartFile profilePhoto,
                           String mail,
                           UserType userType,
                           Double latitude,
                           Double longitude,
                           boolean sundayOpen, List<String> sectors) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.mail = mail;
        this.userType = userType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sundayOpen = sundayOpen;
        this.sectors = sectors;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }
    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
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
    public boolean isSundayOpen() {
        return sundayOpen;
    }
    public void setSundayOpen(boolean sundayOpen) {
        this.sundayOpen = sundayOpen;
    }
    public List<String> getSectors() {
        return sectors;
    }
    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }
}
