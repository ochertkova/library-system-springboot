package com.rest_api.fs14backend.user;
import com.rest_api.fs14backend.book.BookDTO;

import java.time.OffsetDateTime;

public class UserLoanDTO {

    public OffsetDateTime getBorrowDate() {
        return borrowDate;
    }

    public OffsetDateTime getReturnDate() {
        return returnByDate;
    }

    public BookDTO getBook() {
        return book;
    }

    private OffsetDateTime borrowDate;
    private OffsetDateTime returnByDate;
    private BookDTO book;

    public UserLoanDTO(OffsetDateTime borrowDate, OffsetDateTime returnByDate, BookDTO book) {
        this.borrowDate = borrowDate;
        this.returnByDate = returnByDate;
        this.book = book;
    }

}