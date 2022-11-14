package com.mprog.validation;

import com.mprog.database.model.User;
import com.mprog.database.repository.UserRepository;
import com.mprog.dto.auth.SignupRequestDto;
import com.mprog.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.mprog.utils.ResponseUtils.*;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public void validateUserSignupRequest(SignupRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            ResponseUtils.throwResponse400Exception(EMAIL_ALREADY_IN_USE_REASON);
        }
        if (signUpRequest.getEmail() == null || signUpRequest.getEmail().strip().trim().isEmpty()) {
            ResponseUtils.throwResponse400Exception(EMPTY_EMAIL_REASON);
        }
        if (signUpRequest.getPassword() == null || signUpRequest.getPassword().strip().trim().isEmpty()) {
            ResponseUtils.throwResponse400Exception(EMPTY_PASSWORD_REASON);
        }
    }

    public void validateUserLoginRequest(User user, String password) {
        if (user == null) {
            ResponseUtils.throwResponse400Exception(NO_SUCH_USER_REASON);
        }
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            ResponseUtils.throwResponse400Exception(INCORRECT_DATA_REASON);
        }
    }


}
