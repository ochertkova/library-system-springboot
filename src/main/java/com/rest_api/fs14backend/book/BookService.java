package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;


    @Autowired
    private BookMapper bookMapper;

    public Book addOne(NewBookDTO bookDto) {
        Book newBook = bookMapper.newBookDtoToBook(bookDto);
        return bookRepo.save(newBook);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book findById(UUID bookId) {
        return bookRepo.findById(bookId).orElse(null);
    }

    public void deleteById(UUID bookId) {
        if (bookRepo.findById(bookId).isPresent()) {
            bookRepo.deleteById(bookId);
        } else {
            throw new NotFoundException("Book not found");
        }
    }
}
