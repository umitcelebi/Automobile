package com.ucelebi.automobile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Sector extends Item {
    private String code;
    private String name;

    @ManyToMany
    @JoinTable(name = "partner_sector",
    joinColumns = @JoinColumn(name = "sector_id"),
    inverseJoinColumns = @JoinColumn(name = "partner_id"))
    private List<Partner> partners;

    public Sector() {}

    public Sector(String code, String name) {
        this.code = code;
        this.name = name;
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

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }
}
