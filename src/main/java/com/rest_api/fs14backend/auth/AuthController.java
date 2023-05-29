package com.rest_api.fs14backend.auth;

import com.rest_api.fs14backend.SecurityConfig.LibraryUserDetails;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserDTO;
import com.rest_api.fs14backend.user.UserMapper;
import com.rest_api.fs14backend.user.UserService;
import com.rest_api.fs14backend.utils.JwtUtils;
import com.rest_api.fs14backend.utils.ResponseUtils;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        User user = userService.findByUsername(authRequest.getUsername());
        if (user == null) {
            return ResponseUtils.respNotFound("Username was not found. Please sign up");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (AuthenticationException ae) {
            return ResponseUtils.respBadRequest("Incorrect password");
        }
        return userinfoTokenResponse(user);
    }

    private ResponseEntity<Map<String, String>> userinfoTokenResponse(User user) {
        String token = jwtUtils.generateToken(user);
        Map<String, String> payload = Map.of("token", token,
                "role", user.getRole().toString(),
                "name", user.getName());
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDto) {
        if (userService.findByUsername(userDto.getUsername()) != null) {
            return ResponseUtils.respBadRequest("Username is already reserved");
        }
        User newUser = userMapper.toUser(userDto);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(User.Role.USER);
        userService.addOne(newUser);
        return ResponseEntity.ok(newUser);
    }
    @GetMapping("/userinfo")
    public ResponseEntity<?> userinfo(@AuthenticationPrincipal @Nullable LibraryUserDetails userDetails ){
        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = userService.findByUsername(username);
            return ResponseEntity.ok(userinfoTokenResponse(user));
        }
        return ResponseUtils.respBadRequest("No token present");
    }
}
