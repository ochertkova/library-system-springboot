package com.rest_api.fs14backend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

    public Book addOne(Book book) {
        return bookRepo.save(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
}
