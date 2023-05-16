package com.rest_api.fs14backend.SecurityConfig;

import com.rest_api.fs14backend.user.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.UUID;

/**
 * UserDetails implementation subclass for library system
 *
 */
public class LibraryUserDetails extends org.springframework.security.core.userdetails.User {

    @Getter
    private final UUID userId;

    public LibraryUserDetails(User dbUser) {
        super(dbUser.getUsername(), dbUser.getPassword(), Set.of(new SimpleGrantedAuthority("ROLE_" + dbUser.getRole())));
        this.userId = dbUser.getId();
    }
}
