package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.SecurityConfig.LibraryUserDetails;
import com.rest_api.fs14backend.book.BookDTO;
import com.rest_api.fs14backend.book.BookMapper;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains APIs of logged in user
 */
@RestController
@RequestMapping("api/v1/myaccount")
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/loans")
    public ResponseEntity<?> myLoans(@AuthenticationPrincipal
                                     LibraryUserDetails authUser) {
        try {
            User loggedInUser = userService.findByUsername(authUser.getUsername());
            return ResponseEntity.ok(loggedInUser.getActiveLoans().stream().map(dbLoan -> {
                    BookDTO bookDto = bookMapper.toDto(dbLoan.getBook());
                    return new UserLoanDTO(dbLoan.getBorrowDate(), dbLoan.getReturnByDate(), bookDto);
                }
            ));
        } catch (NotFoundException nfe) {
            return ResponseUtils.respNotFound(nfe.getMessage());
        }
    }
}
