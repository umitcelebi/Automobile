package com.ucelebi.automobile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Review extends Item{
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Partner partner;
    private Double rating;
    private String message;

    public Review() {}

    public Review(Customer customer, Partner partner, Double rating, String message) {
        this.customer = customer;
        this.partner = partner;
        this.rating = rating;
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
