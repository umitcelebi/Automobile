package com.ucelebi.automobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class City extends Item{
    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "city")
    private List<Town> towns;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
