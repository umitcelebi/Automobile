package com.ucelebi.automobile.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationTime;

    @UpdateTimestamp
    private Timestamp modifiedTime;

    private boolean active;

    public Item() {
    }

    public Item(Long id, Timestamp creationTime, Timestamp modifiedTime, boolean active) {
        this.id = id;
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long pk) {
        this.id = pk;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
