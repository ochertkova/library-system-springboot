package com.rest_api.fs14backend.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class NewBookDTO {
    @JsonProperty("isbn")
    private long ISBN;
    @JsonProperty("title")
    private String title;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("description")
    private String description;
    @JsonProperty("publishedDate")
    private Date publishedDate;
    @JsonProperty("bookCoverLink")
    private String bookCoverLink;
    @JsonProperty("status")
    private String status;
    @JsonProperty("category")
    private String category;
    @JsonProperty("authors")
    private Set<String> authors;
}
