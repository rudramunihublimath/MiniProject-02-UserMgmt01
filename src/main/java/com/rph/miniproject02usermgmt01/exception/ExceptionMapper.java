package com.rph.miniproject02usermgmt01.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(value = SmtpException.class)
    public ResponseEntity<ErrorResponse> handleSmtpException(SmtpException e) {
        ErrorResponse resp = new ErrorResponse();
        resp.setErrorCode("SMTP100");
        resp.setErrorCMsg(e.getMessage());
        resp.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UserAppException.class)
    public ResponseEntity<ErrorResponse> handleUserAppException(UserAppException e) {
        ErrorResponse resp = new ErrorResponse();
        resp.setErrorCode("UserApp100");
        resp.setErrorCMsg(e.getMessage());
        resp.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
