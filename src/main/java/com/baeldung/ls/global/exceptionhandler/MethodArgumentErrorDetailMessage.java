package com.baeldung.ls.global.exceptionhandler;

public class MethodArgumentErrorDetailMessage extends ErrorDetailMessage{
    private final String filedName;
    private final Object rejectedValue;

    public MethodArgumentErrorDetailMessage(String detailMessage, String filedName, Object rejectedValue) {
        super(detailMessage);
        this.filedName = filedName;
        this.rejectedValue = rejectedValue;
    }

    public String getFiledName() {
        return filedName;
    }
}
