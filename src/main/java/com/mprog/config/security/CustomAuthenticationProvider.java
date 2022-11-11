package com.mprog.config.security;

import com.mprog.database.model.User;
import com.mprog.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UsernamePasswordAuthenticationToken authenticate(Authentication authentication)
      throws AuthenticationException {
 
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();


        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new BadCredentialsException("1000");
        }
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("1000");
        }
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