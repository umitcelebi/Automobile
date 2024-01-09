package com.ucelebi.automobile.model;

import com.ucelebi.automobile.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Customer extends User{
    public Customer() {}

    public Customer(builder builder) {
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
    }

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "customers")
    private List<Partner> favoritePartners;

    @OneToMany(mappedBy = "customer")
    private List<BlogCustomerRelation> favoriteBlogs;
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reeviews) {
        this.reviews = reeviews;
    }

    public List<Partner> getFavoritePartners() {
        return favoritePartners;
    }

    public void setFavoritePartners(List<Partner> favoritePartners) {
        this.favoritePartners = favoritePartners;
    }

    public List<BlogCustomerRelation> getFavoriteBlogs() {
        return favoriteBlogs;
    }

    public void setFavoriteBlogs(List<BlogCustomerRelation> favoriteBlogs) {
        this.favoriteBlogs = favoriteBlogs;
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
        public Customer build() {
            return new Customer(this);
        }

    }
}
