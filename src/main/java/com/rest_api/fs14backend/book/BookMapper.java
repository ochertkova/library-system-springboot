package com.rest_api.fs14backend.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book toBook(BookDTO newBook, Category category, List<Author> authors) {
        Book book = new Book(
                newBook.getISBN(),
                newBook.getTitle(),
                newBook.getDescription(),
                Book.Status.valueOf(newBook.getStatus()),
                newBook.getPublisher()
        );
        book.setCategory(category);
        book.setAuthors(authors);
        try {
            book.setPublishedDate(getPublishedDate(newBook));
        } catch (ParseException pe) {
            return book;
        }
        return book;
    }

    public BookDTO toDto(Book book) {
        List<String> authors = book
                .getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.toList());
        BookDTO dto = new BookDTO(
                book.getId(),
                book.getISBN(),
                book.getTitle(),
                book.getPublisher(),
                book.getDescription(),
                book.getBookCoverLink(),
                book.getStatus().toString(),
                book.getCategory().getName(),
                authors);
        if(book.getPublishedDate() != null) {
            setPublishedDate(dto, book.getPublishedDate());
        }
        return dto;
    }

    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy");
    private static final String timezone = "Europe/Helsinki";

    @JsonIgnore
    public Date getPublishedDate(BookDTO bookDto) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(bookDto.getPublishedDate());
    }

    public void setPublishedDate(BookDTO bookDto, Date date)  {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        bookDto.setPublishedDate(dateFormat.format(date));
    }

}
