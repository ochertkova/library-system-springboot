package com.rest_api.fs14backend.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = true)
    private long ISBN;

    @Column
    private String title;

    @Column(nullable = true)
    private String publisher;

    @Column(nullable = true)
    @Setter
    private Date publishedDate;

    @Column(nullable = true)
    private String description;

    @Column
    private String bookCoverLink;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    enum Status {
        BORROWED,
        AVAILABLE
    }

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnoreProperties("books")
    @Setter
    private List<Author> authors;

    public Book(long ISBN, String title, String description, String bookCoverlink, Status status, String publisher) {
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.bookCoverLink = bookCoverlink;
        this.status = status;
        this.publisher = publisher;
    }

    public UUID getId() {
        return id;
    }

    public long getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean matchesSearch(String search) {
        String searchLowerCase = search.toLowerCase();
        return this.getTitle().toLowerCase().contains(searchLowerCase)
                || this.getDescription().toLowerCase().contains(searchLowerCase);
    }

    public void setFields(Book otherBook) {
        this.ISBN = otherBook.getISBN();
        this.title = otherBook.getTitle();
        this.description = otherBook.getDescription();
        this.bookCoverLink = otherBook.getBookCoverLink();
        this.status = Optional.ofNullable(otherBook.getStatus()).orElse(this.status);
        this.publisher = otherBook.getPublisher();
        this.publishedDate = otherBook.getPublishedDate();
        this.category = otherBook.getCategory();
        this.setAuthors(otherBook.getAuthors());
    }
}