package com.rest_api.fs14backend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping("/")
    public Book addOne(@RequestBody Book book) {
        return bookService.addOne(book);
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
