package com.ucelebi.automobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Image extends Item{
    private String name;
    private String type;
    @Column(nullable = false, length = 100)
    private String imagePath;
    private String partnerUid;
    @JsonIgnore
    @ManyToOne
    private Partner partner;

    public Image() {}
    public Image(ImageBuilder imageBuilder) {
        this.name=imageBuilder.name;
        this.type=imageBuilder.type;
        this.imagePath=imageBuilder.imagePath;
        this.partnerUid = imageBuilder.partnerUid;
        this.partner=imageBuilder.partner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPartnerUid() {
        return partnerUid;
    }

    public void setPartnerUid(String partnerUid) {
        this.partnerUid = partnerUid;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public static class ImageBuilder{
        private String name;
        private String type;
        private String imagePath;
        private String partnerUid;
        private Partner partner;

        public ImageBuilder name(String name){
            this.name=name;
            return this;
        }

        public ImageBuilder type(String type){
            this.type=type;
            return this;
        }

        public ImageBuilder image(String imagePath){
            this.imagePath=imagePath;
            return this;
        }

        public ImageBuilder partnerUid(String partnerUid) {
            this.partnerUid = partnerUid;
            return this;
        }

        public ImageBuilder partner(Partner partner){
            this.partner=partner;
            return this;
        }

        public Image build(){
            return new Image(this);

        }
    }
}
