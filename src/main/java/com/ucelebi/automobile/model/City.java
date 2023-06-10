package com.ucelebi.automobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class City extends Item{
    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Country country;

    @OneToMany(mappedBy = "city")
    private List<Town> towns;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;

    public City() {}

    public City(Long id, Timestamp creationTime, Timestamp modifiedTime, boolean active, String code, String name, Country country, List<Town> towns) {
        super(id, creationTime, modifiedTime, active);
        this.code = code;
        this.name = name;
        this.country = country;
        this.towns = towns;
    }

    public City(String code, String name, Country country) {
        this.code = code;
        this.name = name;
        this.country = country;
    }

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
