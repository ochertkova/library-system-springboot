package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepo;
    public Author addOne(Author author) {
        return authorRepo.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }
    public Author findById(UUID authorId){return authorRepo.findById(authorId).orElse(null);}
    public void deleteById(UUID authorId) {
        if (authorRepo.findById(authorId).isPresent()) {
            authorRepo.deleteById(authorId);
        } else {
            throw new NotFoundException("Author not found");
        }
    }
}
