package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<BookDTO> getAll(@AuthenticationPrincipal UserDetails authUser) {
        System.out.println("User details");
        System.out.println(authUser);

        return bookService.getAllBooks().stream().map(bookMapper::toDto).toList();
    }

    @PostMapping("/")
    public Book addOne(@RequestBody BookDTO bookDto) {
        Category category = categoryService
                .findByName(bookDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        List<Author> authors = bookDto
                .getAuthors()
                .stream()
                .map(authorService::findByName)
                .collect(Collectors.toList());
        Book newBook = bookMapper.toBook(bookDto, category, authors);
        return bookService.addOne(newBook);
    }

    @PostMapping("/{id}/borrow")
    public Book borrowBook(@PathVariable("id") UUID id, @AuthenticationPrincipal UserDetails authUser){
        return bookService.borrowBook(id, UUID.fromString("609a808e-5233-48e0-b63d-83b6761110ae"));
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
