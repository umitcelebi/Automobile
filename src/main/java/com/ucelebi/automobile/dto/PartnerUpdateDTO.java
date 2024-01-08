package com.ucelebi.automobile.dto;

import com.ucelebi.automobile.enums.Role;

import java.util.List;

/**
 * User: ucelebi
 * Date: 25.06.2023
 * Time: 21:20
 */
public class PartnerUpdateDTO {
    private boolean active;
    private String uid;
    private String name;
    private String displayName;
    private String phoneNumber;
    private String profilePhoto;
    private Double latitude;
    private Double longitude;
    private List<String> sectors;
    private List<WorkingTimesDTO> workingTimesDTOS;
    private List<ImageDTO> images;
    private AddressDTO address;
    private boolean sundayOpen;
    private Role role;

    public PartnerUpdateDTO() {}

    public PartnerUpdateDTO(boolean active,
                            String uid,
                            String name,
                            String displayName,
                            String phoneNumber,
                            String profilePhoto,
                            Double latitude,
                            Double longitude,
                            List<String> sectors,
                            List<WorkingTimesDTO> workingTimesDTOS,
                            List<ImageDTO> images,
                            AddressDTO address,
                            boolean sundayOpen,
                            Role role) {
        this.active = active;
        this.uid = uid;
        this.name = name;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sectors = sectors;
        this.workingTimesDTOS = workingTimesDTOS;
        this.images = images;
        this.address = address;
        this.sundayOpen = sundayOpen;
        this.role = role;
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
    public List<String> getSectors() {
        return sectors;
    }
    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }
    public List<WorkingTimesDTO> getWorkingTimesDTOS() {
        return workingTimesDTOS;
    }
    public void setWorkingTimesDTOS(List<WorkingTimesDTO> workingTimesDTOS) {
        this.workingTimesDTOS = workingTimesDTOS;
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
