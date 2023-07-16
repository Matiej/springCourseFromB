package com.baeldung.ls.global.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionHandlerResponse {
    private LocalDateTime errorTimeStamp;
    private String message;
    List details;
    String statusCode;
    String status;

    public ExceptionHandlerResponse() {
    }

    public ExceptionHandlerResponse(LocalDateTime errorTimeStamp, String message, String statusCode, String status) {
        this.errorTimeStamp = errorTimeStamp;
        this.message = message;
        this.statusCode = statusCode;
        this.status = status;
    }

    public LocalDateTime getErrorTimeStamp() {
        return errorTimeStamp;
    }

    public void setErrorTimeStamp(LocalDateTime errorTimeStamp) {
        this.errorTimeStamp = errorTimeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getDetails() {
        return details;
    }

    public void setDetails(List details) {
        this.details = details;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
