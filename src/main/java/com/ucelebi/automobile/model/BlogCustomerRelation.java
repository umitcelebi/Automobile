package com.ucelebi.automobile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class BlogCustomerRelation extends Item{
    @ManyToOne
    private Blog blog;
    @ManyToOne
    private Customer customer;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
