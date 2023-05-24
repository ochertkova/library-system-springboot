package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.exceptions.EntityAlreadyExists;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepo;
    public Author addOne(Author author) {
        Optional<Author> authorToAdd = authorRepo.findByName(author.getName());
        if (authorToAdd.isEmpty()) {
            return authorRepo.save(author);
        } else {
            throw new EntityAlreadyExists("Author already exists");
        }

    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }
    public Author findById(UUID authorId){return authorRepo.findById(authorId).orElse(null);}

    public Author findByName(final String authorName){
        Optional<Author> maybeAuthor = authorRepo.findByName(authorName);
        return maybeAuthor.orElseThrow(() -> new NotFoundException(
                "Author %s not found".formatted(authorName)
        ));
    }
    public void deleteById(UUID authorId) {
        Optional<Author> authorToDelete = authorRepo.findById(authorId);
        if (authorToDelete.isPresent()) {
            authorRepo.deleteById(authorId);
        } else {
            throw new NotFoundException("Author not found");
        }
    }
}
