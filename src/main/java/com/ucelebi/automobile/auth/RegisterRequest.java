package com.ucelebi.automobile.auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ucelebi.automobile.enums.Role;
import com.ucelebi.automobile.enums.UserType;

/**
 * User: ucelebi
 * Date: 24.06.2023
 * Time: 17:22
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomerRegisterRequest.class, name = UserType.Constants.BIREYSEL_VALUE),
        @JsonSubTypes.Type(value = PartnerRegisterRequest.class, name = UserType.Constants.KURUMSAL_VALUE)
})
public abstract class RegisterRequest {
    protected String username;
    protected String password;
    protected String name;
    protected String displayName;
    protected String mail;
    protected Role role;
    protected String phoneNumber;
    protected UserType userType;

    public RegisterRequest() {}

    public RegisterRequest(String name,
                           String displayName,
                           String username,
                           String password,
                           Role role,
                           String phoneNumber,
                           String mail,
                           UserType userType) {
        this.name = name;
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.userType = userType;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
