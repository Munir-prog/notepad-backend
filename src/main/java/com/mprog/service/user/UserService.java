package com.mprog.service.user;

import com.mprog.config.jwt.JwtUtils;
import com.mprog.config.security.CustomAuthenticationProvider;
import com.mprog.database.model.User;
import com.mprog.database.model.role.ERole;
import com.mprog.database.model.role.Role;
import com.mprog.database.repository.RoleRepository;
import com.mprog.database.repository.UserRepository;
import com.mprog.dto.auth.LoginRequestDto;
import com.mprog.dto.auth.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public UsernamePasswordAuthenticationToken processAuthentication(LoginRequestDto loginRequest) {
        var authentication = customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public boolean registerUserIfUnique(SignupRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return false;
        }

        User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        if (user.getLastname() == null) {
            user.setLastname("Unknown");
        }
        if (user.getFirstname() == null) {
            user.setFirstname("Unknown");
        }
        Set<Role> roles = processRoles(signUpRequest.getRoles());

        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    public String generateJwtToken(UsernamePasswordAuthenticationToken authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    private Set<Role> processRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }
}
