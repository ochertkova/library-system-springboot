package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.book.BookDTO;
import com.rest_api.fs14backend.book.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private BookMapper bookMapper;

    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setLoans(user.getLoans().stream().map(dbLoan -> {
            BookDTO bookDto = bookMapper.toDto(dbLoan.getBook());
            return new UserLoanDTO(dbLoan.getBorrowDate(), dbLoan.getReturnDate(), bookDto);
        }).collect(Collectors.toList()));
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public User toUser(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        return user;
    }
}
