package com.rph.miniproject02usermgmt01.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String errorCode;
    private String errorCMsg;
    private LocalDateTime dateTime ;
}
