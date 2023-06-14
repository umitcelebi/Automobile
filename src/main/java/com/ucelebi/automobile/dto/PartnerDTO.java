package com.ucelebi.automobile.dto;

import com.ucelebi.automobile.enums.Role;

import java.sql.Timestamp;
import java.util.List;

public class PartnerDTO {
    private Timestamp creationTime;
    private Timestamp modifiedTime;
    private boolean active;
    private String uid;
    private String name;
    private String displayName;
    private String phoneNumber;
    private String profilePhoto;
    private Double userRating;
    private Double latitude;
    private Double longitude;
    private List<ReviewDTO> reviews;
    private List<SectorDTO> sectors;
    private List<String> openingTimes;
    private List<ImageDTO> images;
    private AddressDTO address;
    private boolean sundayOpen;
    private Role role;

    public PartnerDTO() {}

    public PartnerDTO(Timestamp creationTime,
                      Timestamp modifiedTime,
                      boolean active,
                      String uid,
                      String name,
                      String displayName,
                      String phoneNumber,
                      String profilePhoto,
                      Double userRating,
                      Double latitude,
                      Double longitude,
                      List<ReviewDTO> reviews,
                      List<SectorDTO> sectors,
                      List<String> openingTimes,
                      List<ImageDTO> images,
                      AddressDTO address,
                      boolean sundayOpen,
                      Role role) {
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
        this.uid = uid;
        this.name = name;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.userRating = userRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviews = reviews;
        this.sectors = sectors;
        this.openingTimes = openingTimes;
        this.images = images;
        this.address = address;
        this.sundayOpen = sundayOpen;
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

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public List<SectorDTO> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorDTO> sectors) {
        this.sectors = sectors;
    }

    public List<String> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<String> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public boolean isSundayOpen() {
        return sundayOpen;
    }

    public void setSundayOpen(boolean sundayOpen) {
        this.sundayOpen = sundayOpen;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
