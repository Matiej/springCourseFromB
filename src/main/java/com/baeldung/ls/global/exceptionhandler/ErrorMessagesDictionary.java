package com.baeldung.ls.global.exceptionhandler;

import java.util.Arrays;

public enum ErrorMessagesDictionary {
    ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException", "Given argument error=> "),
    ENTITY_NOT_FOUND_EX("EntityNotFoundException", "Given argument error=> "),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION("MethodArgumentNotValidException", "Method arguments error -> "),
    CONSTRAINT_VIOLATION_EXCEPTION("ConstraintViolationException", "Path parameter error. "),
    VALIDATION_EXCEPTION("ValidationException", "Validation error ->"),
    METHOD_ARGUMENT_MISMATCH_EXCEPTION("MethodArgumentTypeMismatchException", "Method arguments missmatch error-> "),
    HIBERNATE_EXCEPTION("HibernateException", "Data Base server error. Can not get or save any report." );

    private static final String DEFAULT_MESSAGE = "Exception error -> ";
    private String exceptionClassName;
    private String message;

    ErrorMessagesDictionary(String exceptionClassName, String message) {
        this.exceptionClassName = exceptionClassName;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public static String getValidMessage(String error) {
        return Arrays.stream(values())
                .filter(errorMsg -> errorMsg.getExceptionClassName().equalsIgnoreCase(error))
                .map(ErrorMessagesDictionary::getMessage)
                .findAny()
                .orElse(DEFAULT_MESSAGE);
    }
}
