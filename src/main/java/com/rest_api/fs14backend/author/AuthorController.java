package com.rest_api.fs14backend.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/authors")

public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping
    public List<Author> getAll() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/")
    public Author addOne(@RequestBody Author author) {
        return authorService.addOne(author);
    }

    @GetMapping(path="/{id}")
    public Optional<Author> getAuthorById(@PathVariable("id") UUID id) {
        return Optional.ofNullable(authorService.findById(id));
    }
    @DeleteMapping(path="/{id}")
    public void deleteAuthorById(@PathVariable("id") UUID id){
        authorService.deleteById(id);
    }

}
