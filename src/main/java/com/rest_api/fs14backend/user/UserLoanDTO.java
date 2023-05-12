package com.rest_api.fs14backend.user;
import com.rest_api.fs14backend.book.BookDTO;

import java.time.OffsetDateTime;

public class UserLoanDTO {

    public OffsetDateTime getBorrowDate() {
        return borrowDate;
    }

    public OffsetDateTime getReturnDate() {
        return returnDate;
    }

    public BookDTO getBook() {
        return book;
    }

    private OffsetDateTime borrowDate;
    private OffsetDateTime returnDate;
    private BookDTO book;

    public UserLoanDTO(OffsetDateTime borrowDate, OffsetDateTime returnDate, BookDTO book) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
    }

}