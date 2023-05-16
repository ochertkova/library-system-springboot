package com.rest_api.fs14backend.auth;

import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserDTO;
import com.rest_api.fs14backend.user.UserMapper;
import com.rest_api.fs14backend.user.UserService;
import com.rest_api.fs14backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public Map<String, String> login(@RequestBody AuthRequest authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        User user = userService.findByUsername(authRequest.getUsername());
        String token = jwtUtils.generateToken(user);
        return Map.of("token", token,
                "role", user.getRole().toString(),
                "name", user.getName());
    }

    @PostMapping("/signup")
    public User signup(@RequestBody UserDTO userDto) {
        // TODO: check that username & email is available

        User newUser = userMapper.toUser(userDto);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(User.Role.USER);
        userService.addOne(newUser);
        return newUser;
    }
}
