package com.mprog.dto;

import com.mprog.database.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {

    private String token;
    private Long id;
    private String email;
    private List<String> roles;
}
