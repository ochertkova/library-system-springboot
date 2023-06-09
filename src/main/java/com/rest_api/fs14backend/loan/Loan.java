package com.rest_api.fs14backend.loan;


import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name="book_id")
    private Book book;

    @Column
    private OffsetDateTime borrowDate;
    private OffsetDateTime returnByDate;
    private OffsetDateTime returnedDate;

    public Loan(User user, Book book, OffsetDateTime borrowDate, OffsetDateTime returnByDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnByDate = returnByDate;
    }
}
