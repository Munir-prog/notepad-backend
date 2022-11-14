package com.mprog.config.security;

import com.mprog.database.model.User;
import com.mprog.database.repository.UserRepository;
import com.mprog.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserValidation userValidation;

    @Override
    public UsernamePasswordAuthenticationToken authenticate(Authentication authentication)
      throws AuthenticationException {
 
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();


        User user = userRepository.getByEmail(email);
        userValidation.validateUserLoginRequest(user, password);


        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getRoles()
                        .stream()
                        .map(x -> new SimpleGrantedAuthority(x.getName().name()))
                        .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}