package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookMapper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    protected Book newBookDtoToBook(NewBookDTO newBook) {
        /*  TODO throw exceptions if category or authors not found*/
        Category category = categoryService.findByName(newBook.getCategory());
        List<Author> authors = newBook.getAuthors().stream().map(authorService::findByName).collect(Collectors.toList());
        return Book.fromDto(newBook, category, authors);
    }
}
