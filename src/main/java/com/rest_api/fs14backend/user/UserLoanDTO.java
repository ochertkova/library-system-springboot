package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.loan.Loan;

import java.time.OffsetDateTime;

public class UserLoanDTO {

    public OffsetDateTime getBorrowDate() {
        return borrowDate;
    }

    public OffsetDateTime getReturnDate() {
        return returnDate;
    }

    public Book getBook() {
        return book;
    }

    private OffsetDateTime borrowDate;
    private OffsetDateTime returnDate;
    private Book book;

    public UserLoanDTO(OffsetDateTime borrowDate, OffsetDateTime returnDate, Book book) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
    }

    public static UserLoanDTO fromLoan(Loan dbLoan) {
        return new UserLoanDTO(dbLoan.getBorrowDate(), dbLoan.getReturnDate(), dbLoan.getBook());
    };
}