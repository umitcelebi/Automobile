package com.ucelebi.automobile.model;

import com.ucelebi.automobile.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends User{
    public Customer() {}

    public Customer(builder builder) {
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

    @OneToMany(mappedBy = "customer")
    private List<CustomerPartner> favoritePartners;

    @OneToMany(mappedBy = "customer")
    private List<BlogCustomerRelation> favoriteBlogs;
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reeviews) {
        this.reviews = reeviews;
    }

    public List<CustomerPartner> getFavoritePartners() {
        return favoritePartners;
    }

    public void setFavoritePartners(List<CustomerPartner> favoritePartners) {
        this.favoritePartners = favoritePartners;
    }

    public List<BlogCustomerRelation> getFavoriteBlogs() {
        return favoriteBlogs;
    }

    public void setFavoriteBlogs(List<BlogCustomerRelation> favoriteBlogs) {
        this.favoriteBlogs = favoriteBlogs;
    }

    public static class builder{

        private String uid;
        private String name;
        private String displayName;
        private String password;
        private String phoneNumber;
        private Role role;
        private String profilePhoto;

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
