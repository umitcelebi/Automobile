package com.ucelebi.automobile.model;


import com.ucelebi.automobile.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User extends Item implements UserDetails {

    @Column(nullable = false, unique = true)
    protected String uid;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String displayName;
    @Column(nullable = false)
    protected String password;
    @Column(nullable = false)
    protected String phoneNumber;
    protected String profilePhoto;
    protected Boolean isPhoneNumberVerified;
    protected Boolean isBlocked;
    protected Date blockedDate;
    protected String blockingDescription;
    protected Date lastLogin;

    @Enumerated(EnumType.STRING)
    protected Role role;

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

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getPhoneNumberVerified() {
        return isPhoneNumberVerified;
    }

    public void setPhoneNumberVerified(Boolean phoneNumberVerified) {
        isPhoneNumberVerified = phoneNumberVerified;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Date getBlockedDate() {
        return blockedDate;
    }

    public void setBlockedDate(Date blockedDate) {
        this.blockedDate = blockedDate;
    }

    public String getBlockingDescription() {
        return blockingDescription;
    }

    public void setBlockingDescription(String blockingDescription) {
        this.blockingDescription = blockingDescription;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
