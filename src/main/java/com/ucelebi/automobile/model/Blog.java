package com.ucelebi.automobile.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Blog extends Item{
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Lob
    @Column(nullable = false)
    private String firstParagraph;

    @Lob
    private String secondParagraph;

    @Lob
    private String thirdParagraph;

    @ManyToMany
    @JoinTable(name = "blog_categories",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<BlogCategory> categories;

    @ManyToMany
    @JoinTable(name = "blog_tags",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<BlogTag> tags;

    @OneToMany(mappedBy = "blog")
    private List<BlogCustomerRelation> favoriteCustomers;
    @ManyToOne
    private Author author;

    //todo image

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstParagraph() {
        return firstParagraph;
    }

    public void setFirstParagraph(String firstParagraph) {
        this.firstParagraph = firstParagraph;
    }

    public String getSecondParagraph() {
        return secondParagraph;
    }

    public void setSecondParagraph(String secondParagraph) {
        this.secondParagraph = secondParagraph;
    }

    public String getThirdParagraph() {
        return thirdParagraph;
    }

    public void setThirdParagraph(String thirdParagraph) {
        this.thirdParagraph = thirdParagraph;
    }

    public List<BlogCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<BlogCategory> categories) {
        this.categories = categories;
    }

    public List<BlogTag> getTags() {
        return tags;
    }

    public void setTags(List<BlogTag> tags) {
        this.tags = tags;
    }

    public List<BlogCustomerRelation> getFavoriteCustomers() {
        return favoriteCustomers;
    }

    public void setFavoriteCustomers(List<BlogCustomerRelation> favoriteCustomers) {
        this.favoriteCustomers = favoriteCustomers;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
