package com.rest_api.fs14backend.author;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest_api.fs14backend.book.Book;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String name;
    @ManyToMany(mappedBy="authors",fetch= FetchType.LAZY)
    @JsonIgnoreProperties("books")
    private List<Book> books;

}
