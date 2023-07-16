package com.baeldung.ls.global.exceptionhandler;

public class ErrorDetailMessage {
    private final String detailMessage;

    public ErrorDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
