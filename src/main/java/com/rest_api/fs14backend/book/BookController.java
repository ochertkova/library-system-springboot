package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.SecurityConfig.LibraryUserDetails;
import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.BookUnavailableException;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAllBooks().stream().map(bookMapper::toDto).toList();
    }

    @PostMapping("/")
    public ResponseEntity<?> addOne(@RequestBody BookDTO bookDto) {
        try {
            Category category = categoryService
                    .findByName(bookDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            List<Author> authors = bookDto
                    .getAuthors()
                    .stream()
                    .map(authorService::findByName)
                    .collect(Collectors.toList());
            Book newBook = bookMapper.toBook(bookDto, category, authors);
            return ResponseEntity.ok(bookService.addOne(newBook));
        } catch ( NotFoundException nfe ) {
            return ResponseUtils.respNotFound(nfe.getMessage());
        }
    }

    @PostMapping("/{id}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable("id") String pathId, @AuthenticationPrincipal LibraryUserDetails authUser) {
        try {
            UUID bookId = UUID.fromString(pathId);
            Book borrowedBook = bookService.borrowBook(bookId, authUser.getUserId());
            return ResponseEntity.ok(borrowedBook);
        } catch (IllegalArgumentException iea) {
            return ResponseUtils.respBadRequest("Invalid book id");
        } catch (NotFoundException nfe) {
            return ResponseUtils.respNotFound(nfe.getMessage());
        } catch (BookUnavailableException bue) {
            return ResponseUtils.respConflict("Book is unavailable");
        }
    }

    @GetMapping(path="/{id}")
    public Optional<Book> getBookById(@PathVariable("id") UUID id) {
        return Optional.ofNullable(bookService.findById(id));
    }
    @DeleteMapping(path="/{id}")
    public void deleteBookById(@PathVariable("id") UUID id){
        bookService.deleteById(id);
    }

}
