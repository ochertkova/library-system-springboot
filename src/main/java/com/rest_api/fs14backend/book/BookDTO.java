package com.rest_api.fs14backend.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    @JsonProperty("isbn")
    private long ISBN;
    @JsonProperty("title")
    private String title;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("description")
    private String description;
    @JsonProperty("publishedDate")
    private String publishedDate;
    @JsonProperty("bookCoverLink")
    private String bookCoverLink;
    @JsonProperty("status")
    private String status;
    @JsonProperty("category")
    private String category;
    @JsonProperty("authors")
    private List<String> authors;

    public BookDTO(long ISBN, String title, String publisher, String description, String bookCoverLink, String status, String category, List<String> authors) {
        this.ISBN = ISBN;
        this.title = title;
        this.publisher = publisher;
        this.description = description;
        this.bookCoverLink = bookCoverLink;
        this.status = status;
        this.category = category;
        this.authors = authors;
    }
}
