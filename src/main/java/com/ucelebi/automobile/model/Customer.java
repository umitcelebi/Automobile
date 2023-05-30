package com.ucelebi.automobile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends User{
    public Customer() {
    }

    public Customer(builder builder) {
        this.setUid(builder.getUid());
        this.setName(builder.getName());
        this.setDisplayName(builder.getDisplayName());
        this.setPassword(builder.getPassword());
        this.setPhoneNumber(builder.getPhoneNumber());
        this.setProfilePhoto(builder.getProfilePhoto());
        this.setRole(builder.getRole());
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

}
