package com.mprog.http;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mprog.database.model.User;
import com.mprog.dto.JwtResponseDto;
import com.mprog.dto.LoginRequestDto;
import com.mprog.dto.MessageResponseDto;
import com.mprog.dto.SignupRequestDto;
import com.mprog.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {

        var authentication = userService.processAuthentication(loginRequest);
        var jwt = userService.generateJwtToken(authentication);

        var userDetails = (User) authentication.getPrincipal();
        var roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponseDto(
                        jwt,
                        userDetails.getId(),
                        userDetails.getEmail(),
                        roles
                )
        );
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signUpRequest) {
        if (!userService.registerUserIfUnique(signUpRequest)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Email is already in use!"));
        }
        return ResponseEntity.ok(new MessageResponseDto("User registered successfully!"));
    }


}

