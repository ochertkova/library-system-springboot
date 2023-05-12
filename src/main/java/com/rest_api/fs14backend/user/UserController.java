package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.book.BookDTO;
import com.rest_api.fs14backend.book.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;

    @PostMapping("/")
    public User addOne(@RequestBody User user) {
        return userService.addOne(user);
    }

    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Optional<UserDTO> getUserById(@PathVariable("id") UUID id) {
        return Optional.ofNullable(userService.findById(id)).map(userMapper::toDto);
    }

    @GetMapping(path = "/{id}/loans")
    public Optional<List<UserLoanDTO>> getLoansByUserId(@PathVariable("id") UUID id) {
        return Optional.ofNullable(userService.findById(id))
                .map(User::getLoans)
                .map(loans -> loans.stream().map(
                        dbLoan -> {
                            BookDTO bookDto = bookMapper.toDto(dbLoan.getBook());
                            return new UserLoanDTO(dbLoan.getBorrowDate(), dbLoan.getReturnDate(), bookDto);
                        }
                ).collect(Collectors.toList()));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteById(id);
    }
}
