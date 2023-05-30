package com.ucelebi.automobile.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Partner extends User{
    @OneToOne
    private Address address;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    private Boolean isSundayOpen;
    private Double userRating;
    @ElementCollection
    private List<String> openingTimes;
    @OneToMany(mappedBy = "partner")
    private List<Image> images;

    @ManyToMany(mappedBy = "partners")
    private List<Sector> sectors;

    @OneToMany(mappedBy = "partner",fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "partner")
    private List<CustomerPartner> favoriteCustomers;

    public Partner() {}

    public Partner(builder builder) {
        this.setUid(builder.getUid());
        this.setName(builder.getName());
        this.setDisplayName(builder.getDisplayName());
        this.setPassword(builder.getPassword());
        this.setPhoneNumber(builder.getPhoneNumber());
        this.setProfilePhoto(builder.getProfilePhoto());
        this.setRole(builder.getRole());
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public List<String> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<String> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public Boolean getSundayOpen() {
        return isSundayOpen;
    }

    public void setSundayOpen(Boolean sundayOpen) {
        isSundayOpen = sundayOpen;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<CustomerPartner> getFavoriteCustomers() {
        return favoriteCustomers;
    }

    public void setFavoriteCustomers(List<CustomerPartner> favoriteCustomers) {
        this.favoriteCustomers = favoriteCustomers;
    }

}
