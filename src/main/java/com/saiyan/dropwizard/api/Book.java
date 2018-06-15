package com.saiyan.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Book {

    private long id;

    @Length(max = 255)
    private String title;

    @Length(max = 255)
    private String description;

    public Book() {
        // Jackson deserialization
    }

    public Book(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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
}
