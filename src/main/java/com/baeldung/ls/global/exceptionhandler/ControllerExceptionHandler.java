package com.baeldung.ls.global.exceptionhandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleHibernateException(RuntimeException rex, WebRequest request) {
        //todo prepared for database, uncoment code to
//        if (rex instanceof FeignException) {
//            message = "Gov resource server error. Can not send and receive any report";
//            log.error(message, rex);
//        } else if (rex instanceof HibernateException) {
//            message = "Data Base server error. Can not get or save any report.";
//            log.error(message, rex);
//        } else {
        return prepareResponse(HttpStatus.BAD_REQUEST, rex);
    }

    @ExceptionHandler({IllegalArgumentException.class, EntityNotFoundException.class})
    public final ResponseEntity<Object> handleIllegalArgumentException(RuntimeException rex, WebRequest request) {
        return prepareResponse(HttpStatus.BAD_REQUEST, rex);
    }

    @ExceptionHandler({ConstraintViolationException.class, org.hibernate.exception.ConstraintViolationException.class})
    public final ResponseEntity<Object> handleConstraintViolationException(RuntimeException rex, WebRequest request) {
        return prepareResponse(HttpStatus.BAD_REQUEST, rex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return prepareResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return prepareResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return prepareResponse(HttpStatus.BAD_REQUEST, ex);
    }


    private ResponseEntity<Object> prepareResponse(HttpStatus status, Exception exception) {
        String message = ErrorMessagesDictionary.getValidMessage(exception.getClass().getSimpleName());
        ExceptionHandlerResponse exceptionHandlerResponse = getExceptionHandlerResponse(exception, message, status);
        LOG.error(message, exception);
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(getExceptionHeaders(status.name(), exception.getMessage()))
                .body(exceptionHandlerResponse);
    }

    private ExceptionHandlerResponse getExceptionHandlerResponse(Exception ex, String message, HttpStatus httpStatus) {

        ExceptionHandlerResponse exceptionHandlerResponse = new ExceptionHandlerResponse(LocalDateTime.now(),
                message,
                String.valueOf(httpStatus.value()),
                httpStatus.name());

        if (ex instanceof MethodArgumentNotValidException) {
            List<MethodArgumentErrorDetailMessage> methodArgumentErrorDetailMessages = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()
                    .stream()
                    .map(err -> new MethodArgumentErrorDetailMessage(
                            err.getDefaultMessage(),
                            ((FieldError) err).getField(),
                            ((FieldError) err).getRejectedValue()))
                    .toList();
            exceptionHandlerResponse.setDetails(methodArgumentErrorDetailMessages);
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException err = (MethodArgumentTypeMismatchException) ex;
            MethodArgumentErrorDetailMessage methodArgumentErrorDetailMessage
                    = new MethodArgumentErrorDetailMessage(err.getMessage(), err.getName(), err.getValue());
            exceptionHandlerResponse.setDetails(List.of(methodArgumentErrorDetailMessage));
        } else {
            exceptionHandlerResponse.setDetails(new ArrayList<>(List.of(new ErrorDetailMessage(ex.getMessage()))));
        }

        return exceptionHandlerResponse;
    }

    private HttpHeaders getExceptionHeaders(String status, String message) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Status", status);
        httpHeaders.add("Message", message);
        return httpHeaders;
    }
}
