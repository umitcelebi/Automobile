package com.ucelebi.automobile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class CustomerPartner extends Item{
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Partner partner;

    public CustomerPartner() {}

    public CustomerPartner(Customer customer, Partner partner) {
        this.customer = customer;
        this.partner = partner;
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
}
