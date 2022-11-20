package com.mprog.context;

import com.mprog.database.model.User;
import com.mprog.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContextHolder {

    private static UserRepository userRepository;

    @Autowired
    private UserContextHolder(UserRepository userRepository) {
        UserContextHolder.userRepository = userRepository;
    }

    public static User getUser() {
        return userRepository.findByEmail(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        ).orElse(null);
    }

}
