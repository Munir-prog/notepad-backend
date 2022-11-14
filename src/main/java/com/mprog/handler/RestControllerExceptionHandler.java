package com.mprog.handler;

import com.mprog.dto.auth.MessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.mprog.utils.ResponseUtils.*;

@Slf4j
@RestControllerAdvice(basePackages = "com.mprog.http.rest")
public class RestControllerExceptionHandler  {

    @ExceptionHandler(Exception.class)
    public MessageResponseDto handleExceptions(Exception exception) {
        var messageResponseDto = new MessageResponseDto();
        messageResponseDto.setSuccess(false);
        var message = exception.getMessage();
        if (message.contains(NO_SUCH_USER_REASON)) {
            messageResponseDto.setMessage(NO_SUCH_USER_REASON);
        } else if (message.contains(INCORRECT_DATA_REASON)) {
            messageResponseDto.setMessage(INCORRECT_DATA_REASON);
        } else if (message.contains(EMAIL_ALREADY_IN_USE_REASON)) {
            messageResponseDto.setMessage(EMAIL_ALREADY_IN_USE_REASON);
        } else if (message.contains(EMPTY_EMAIL_REASON)) {
            messageResponseDto.setMessage(EMPTY_EMAIL_REASON);
        } else if (message.contains(EMPTY_PASSWORD_REASON)) {
            messageResponseDto.setMessage(EMPTY_PASSWORD_REASON);
        } else if (message.contains(BAD_EMAIL_TYPE_REASON)) {
            messageResponseDto.setMessage(BAD_EMAIL_TYPE_REASON);
        }
        else {
            messageResponseDto.setMessage(exception.getMessage());
        }
        return messageResponseDto;
    }
}