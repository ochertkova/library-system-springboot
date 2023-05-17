package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.exceptions.BookUnavailableException;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.loan.Loan;
import com.rest_api.fs14backend.loan.LoanRepository;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rest_api.fs14backend.book.Book.Status.AVAILABLE;
import static com.rest_api.fs14backend.book.Book.Status.BORROWED;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private LoanRepository loanRepo;

    public Book addOne(Book book) {
        return bookRepo.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book findById(UUID bookId) {
        return bookRepo.findById(bookId).orElse(null);
    }

    public Optional<Book> deleteById(UUID bookId) {
        Optional<Book> bookToDelete;
        if (bookRepo.findById(bookId).isPresent()) {
            bookToDelete = bookRepo.findById(bookId);
            bookRepo.deleteById(bookId);
        } else {
            throw new NotFoundException("Book not found");
        }
        return bookToDelete;
    }

    private Book validateBookToBorrow(UUID bookId) {
        Optional<Book> maybeBook = bookRepo.findById(bookId);
        Book bookToBorrow = maybeBook.orElseThrow(() -> new NotFoundException("Book not found"));

        if (bookToBorrow.getStatus() == AVAILABLE) {
            return bookToBorrow;
        } else {
            throw new BookUnavailableException("This book is unavailable");
        }
    }

    public Book borrowBook(UUID bookId, UUID userId) {
        User user = userService.findById(userId);
        Book bookToBorrow = validateBookToBorrow(bookId);
        bookToBorrow.setStatus(BORROWED);

        OffsetDateTime borrowDate = OffsetDateTime.now();
        OffsetDateTime returnDate = OffsetDateTime.now().plusWeeks(2);

        Loan newLoan = new Loan(user, bookToBorrow, borrowDate, returnDate);

        bookRepo.save(bookToBorrow);
        loanRepo.save(newLoan);

        return bookToBorrow;
    }
}

