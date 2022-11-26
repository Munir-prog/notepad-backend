package com.mprog.http.rest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mprog.database.model.User;
import com.mprog.dto.GeneralDto;
import com.mprog.dto.auth.JwtResponseDto;
import com.mprog.dto.auth.LoginRequestDto;
import com.mprog.dto.auth.MessageResponseDto;
import com.mprog.dto.auth.SignupRequestDto;
import com.mprog.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/auth/signin")
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
                        true,
                        userDetails.getId(),
                        userDetails.getEmail(),
                        roles
                )
        );
    }


    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signUpRequest) {
        userService.registerUserIfUnique(signUpRequest);
        return ResponseEntity.ok(new MessageResponseDto(true, "User registered successfully!"));
    }

    @PostMapping("/token/check")
    public ResponseEntity<Boolean> checkUser() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/test/check")
    public ResponseEntity<?> checkQuery(@RequestHeader Map<String, String> headers) {
        var token = headers.get("authorization");
        var generalDto = new GeneralDto(1L, LocalDateTime.now(),
                "Мир", "2", "Лужники", "ЦСКА", "Сочи");
        var generalDto2 = new GeneralDto(2L, LocalDateTime.now(),
                "Мир", "3", "Газпром", "Зенит", "Спартак");

        List<GeneralDto> list = new ArrayList<>();
        list.add(generalDto);
        list.add(generalDto2);
        return ResponseEntity.ok(list);
    }


}

