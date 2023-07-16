package com.baeldung.ls.global.headerfactory;

public enum HeaderCustomKey {

    STATUS("Status"),
    MESSAGE("Message"),
    CREATED_AT("CreatedAt"),
    UPDATED_AT("UpdatedAt"),
    SERVER_FILENAME("ServerFileName");

    private String headerKeyLabel;

    HeaderCustomKey(String headerKeyLabel) {
        this.headerKeyLabel = headerKeyLabel;
    }

    public String getHeaderKeyLabel() {
        return headerKeyLabel;
    }
}
