package com.mprog.handler;

import com.mprog.dto.auth.MessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.mprog.http.rest")
public class RestControllerExceptionHandler  {

    private static final String NO_SUCH_USER_REASON = "No such user";
    private static final String INCORRECT_DATA_REASON = "The email address or password is incorrect";
    @ExceptionHandler(Exception.class)
    public MessageResponseDto handleExceptions(Exception exception) {
        var messageResponseDto = new MessageResponseDto();
        messageResponseDto.setSuccess(false);
        var message = exception.getMessage();
        if (message.contains(NO_SUCH_USER_REASON)) {
            messageResponseDto.setMessage(NO_SUCH_USER_REASON);
        } else if (message.contains(INCORRECT_DATA_REASON)) {
            messageResponseDto.setMessage(INCORRECT_DATA_REASON);
        } else {
            messageResponseDto.setMessage(exception.getMessage());
        }
        return messageResponseDto;
    }
}