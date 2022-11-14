package com.mprog.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseUtils {

    public static final String NO_SUCH_USER_REASON = "No such user";
    public static final String BAD_EMAIL_TYPE_REASON = "Bad email type";
    public static final String EMAIL_ALREADY_IN_USE_REASON = "Email is already in use";
    public static final String EMPTY_EMAIL_REASON = "Email address should not be empty";
    public static final String EMPTY_PASSWORD_REASON = "Password should not be empty";
    public static final String INCORRECT_DATA_REASON = "The email address or password is incorrect";

    public static void throwResponse400Exception(String message) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
