package com.rest_api.fs14backend.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
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
    private Date publishedDate;

    @Column(nullable = true)
    private String description;

    @Column
    private String bookCoverLink;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public static Book fromDto(NewBookDTO newBook, Category category, List<Author> authors) {
        Book book = new Book(
                null,
                newBook.getISBN(),
                newBook.getTitle(),
                null,
                newBook.getDescription(),
                Status.valueOf(newBook.getStatus()),
                newBook.getPublisher()
        );
        book.setCategory(category);
        book.setAuthors(authors);
        return book;
    }

    enum Status {
        BORROWED,
        AVAILABLE
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @ManyToMany
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnoreProperties("books")
    private List<Author> authors;

    public Book(UUID id, long ISBN, String title, Date publishedDate, String description, Status status, String publisher) {
        this.id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
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
}