package com.rest_api.fs14backend.user;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String username;
    private String name;
    private String email;

    private String password;

    private List<UserLoanDTO> loans;
}
