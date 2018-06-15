package com.saiyan.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {

    private long id;

    @Length(max = 255)
    private String isbn;

    @Length(max = 255)
    private String title;

    @Length(max = 255)
    private String subtitle;

    @Length(max = 255)
    private String authors;

    private Date published;

    @Length(max = 255)
    private String publisher;

    private Integer pages;

    @Length(max = 255)
    private String description;

    private Byte instock;


    public Book() {
        // Jackson deserialization
    }

    public Book(long id, String isbn, String title, String subtitle, String authors, Date published, String publisher,
                Integer pages, String description, Byte instock) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.published = published;
        this.publisher = publisher;
        this.pages = pages;
        this.description = description;
        this.instock = instock;
    }


    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getDescription() { return description; }

    @JsonProperty
    public String getIsbn() {
        return isbn;
    }

    @JsonProperty
    public String getSubtitle() {
        return subtitle;
    }

    @JsonProperty
    public String getAuthors() {
        return authors;
    }

    @JsonProperty
    public String getPublished() {

        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(published);
    }

    @JsonProperty
    public String getPublisher() {
        return publisher;
    }

    @JsonProperty
    public Integer getPages() {
        return pages;
    }

    @JsonProperty
    public Byte getInstock() {
        return instock;
    }

}
