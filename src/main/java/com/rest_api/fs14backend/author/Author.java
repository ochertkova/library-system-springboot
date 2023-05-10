package com.rest_api.fs14backend.author;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @ManyToMany
    @JoinTable(name = "book_authors")
    @JsonIgnoreProperties("books")
    private List<Author> authorsList;

}
