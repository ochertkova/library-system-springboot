package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.exceptions.BookUnavailableException;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.exceptions.RelatedEntityException;
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

    public List<Book> searchBooks(String search) {
        return bookRepo.searchBooks(search.toLowerCase());
    }


    public Book findById(UUID bookId) {
        return bookRepo.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public void deleteById(UUID bookId) {
        Optional<Book> bookToDelete = bookRepo.findById(bookId);
        List<Loan> bookLoans = loanRepo.findAllByBookId(bookId);
        if (!bookLoans.isEmpty()) {
            throw new RelatedEntityException("Book has related loans");
        }
        if (bookToDelete.isPresent()) {
            bookRepo.deleteById(bookId);
        } else {
            throw new NotFoundException("Book not found");
        }
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

    private Book validateBookToReturn(UUID bookId) {
        Optional<Book> maybeBook = bookRepo.findById(bookId);
        Book bookToReturn = maybeBook.orElseThrow(() -> new NotFoundException("Book not found"));

        if (bookToReturn.getStatus() == BORROWED) {
            return bookToReturn;
        } else {
            throw new BookUnavailableException("This book is already available");
        }
    }

    public Book returnBook(UUID bookId, UUID userId) {
        User user = userService.findById(userId);
        Book bookToReturn = validateBookToReturn(bookId);
        Loan activeLoan = user.getActiveLoans()
                .stream()
                .filter(loan -> loan.getBook().equals(bookToReturn))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Book not found in active loans"));

        bookToReturn.setStatus(AVAILABLE);

        OffsetDateTime returnedDate = OffsetDateTime.now();
        activeLoan.setReturnedDate(returnedDate);

        bookRepo.save(bookToReturn);
        loanRepo.save(activeLoan);

        return bookToReturn;
    }

    public Book updateBook(UUID bookId, Book updatedData) {
        Optional<Book> maybeBook = bookRepo.findById(bookId);
        Book bookToUpdate = maybeBook.orElseThrow(() -> new NotFoundException("Book not found"));
        bookToUpdate.setFields(updatedData);
        bookRepo.save(bookToUpdate);
        return bookToUpdate;
    }
}

