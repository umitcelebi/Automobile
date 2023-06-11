package com.ucelebi.automobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Country extends Item{
    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country")
    private List<City> cities;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Address> addresses;

    public Country() {}

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Country(Long id, Timestamp creationTime, Timestamp modifiedTime, boolean active, String code, String name, List<City> cities) {
        super(id, creationTime, modifiedTime, active);
        this.code = code;
        this.name = name;
        this.cities = cities;
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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
