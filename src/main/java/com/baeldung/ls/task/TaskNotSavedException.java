package com.baeldung.ls.task;

public class TaskNotSavedException extends Exception{
    public TaskNotSavedException(String message) {
        super(message);
    }

    public TaskNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
