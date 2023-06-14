package com.ucelebi.automobile.model;

import com.ucelebi.automobile.enums.Role;
import jakarta.persistence.*;

import java.sql.Timestamp;
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
        this.creationTime = builder.creationTime;
        this.modifiedTime = builder.modifiedTime;
        this.active = builder.active;
        this.uid = builder.uid;
        this.name = builder.name;
        this.displayName = builder.displayName;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.profilePhoto = builder.profilePhoto;
        this.role = builder.role;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.isSundayOpen = builder.isSundayOpen;
        this.userRating = builder.userRating;
        this.openingTimes = builder.openingTimes;
        this.images = builder.images;
        this.sectors = builder.sectors;
        this.reviews = builder.reviews;
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

    public static class builder{
        private Timestamp creationTime;
        private Timestamp modifiedTime;
        private boolean active;
        private String uid;
        private String name;
        private String displayName;
        private String password;
        private String phoneNumber;
        private Role role;
        private String profilePhoto;
        private Address address;
        private Double latitude;
        private Double longitude;
        private Boolean isSundayOpen;
        private Double userRating;
        private List<String> openingTimes;
        private List<Image> images;
        private List<Sector> sectors;
        private List<Review> reviews;
        private List<CustomerPartner> favoriteCustomers;

        public builder creationTime(Timestamp creationTime) {
            this.creationTime = creationTime;
            return this;
        }
        public builder modifiedTime(Timestamp modifiedTime) {
            this.modifiedTime = modifiedTime;
            return this;
        }
        public builder active(boolean active) {
            this.active = active;
            return this;
        }
        public builder uid(String uid) {
            this.uid = uid;
            return this;
        }
        public builder name(String name) {
            this.name = name;
            return this;
        }
        public builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
        public builder password(String password) {
            this.password = password;
            return this;
        }
        public builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public builder role(Role role) {
            this.role = role;
            return this;
        }
        public builder profilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
            return this;
        }
        public builder address(Address address) {
            this.address = address;
            return this;
        }
        public builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }
        public builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }
        public builder sundayOpen(boolean isSundayOpen) {
            this.isSundayOpen = isSundayOpen;
            return this;
        }
        public builder userRating(Double userRating) {
            this.userRating = userRating;
            return this;
        }
        public builder openingTimes(List<String> openingTimes) {
            this.openingTimes = openingTimes;
            return this;
        }
        public builder image(List<Image> images) {
            this.images = images;
            return this;
        }
        public builder sectors(List<Sector> sectors) {
            this.sectors = sectors;
            return this;
        }
        public builder review(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }
        public Partner build() {
            return new Partner(this);
        }
    }
}
