package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.exceptions.EntityAlreadyExists;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAll() {
        List<Author> payload = authorService.getAllAuthors();
        if (payload == null) {
            return ResponseUtils.respNotFound("Authors not found");
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping()
    public ResponseEntity<?> addOne(@RequestBody Author author) {
        try {
            Author payload =  authorService.addOne(author);
            return ResponseEntity.ok(payload);
        } catch (EntityAlreadyExists eae){
            return ResponseUtils.respConflict(eae.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") UUID id) {
        Optional<Author> payload = Optional.ofNullable(authorService.findById(id));
        if (payload.isEmpty()) {
            return ResponseUtils.respNotFound("Author not found");
        }
        return ResponseEntity.ok(payload);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("id") UUID id) {
        try {
          authorService.deleteById(id);
          return ResponseEntity.ok(null);
        } catch (NotFoundException nfe) {
            return ResponseUtils.respNotFound(nfe.getMessage());
        }
    }
}
