package com.ucelebi.automobile.auth;

import com.ucelebi.automobile.dto.AddressDTO;
import com.ucelebi.automobile.dto.WorkingTimesDTO;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;

import java.util.List;

/**
 * User: ucelebi
 * Date: 24.06.2023
 * Time: 11:50
 */
public class PartnerRegisterRequest extends RegisterRequest{
    private Double latitude;
    private Double longitude;
    private boolean sundayOpen;
    private List<String> sectors;
    private List<WorkingTimesDTO> workingTimes;
    private AddressDTO address;

    public PartnerRegisterRequest() {}

    public PartnerRegisterRequest(String name, String displayName, String username, String password, Role role,
                                  String phoneNumber, String mail, UserType userType, Double latitude,
                                  Double longitude, boolean sundayOpen, List<String> sectors,
                                  List<WorkingTimesDTO> workingTimes, AddressDTO address) {
        super(name, displayName, username, password, role, phoneNumber, mail, userType);
        this.latitude = latitude;
        this.longitude = longitude;
        this.sundayOpen = sundayOpen;
        this.sectors = sectors;
        this.workingTimes = workingTimes;
        this.address = address;
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

    public List<WorkingTimesDTO> getWorkingTimes() {
        return workingTimes;
    }

    public void setWorkingTimes(List<WorkingTimesDTO> workingTimes) {
        this.workingTimes = workingTimes;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
