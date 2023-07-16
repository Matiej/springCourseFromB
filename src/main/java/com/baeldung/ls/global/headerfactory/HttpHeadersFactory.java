package com.baeldung.ls.global.headerfactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class HttpHeadersFactory {
    private static final String SUCCESSFUL = "Successful";

    public static HttpHeaders getSuccessfulDefaultHeaders(HttpStatus status, HttpMethod... allowedMethods) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, Arrays.toString(allowedMethods));
        httpHeaders.add(HeaderCustomKey.STATUS.getHeaderKeyLabel(), status.name());
        httpHeaders.add(HeaderCustomKey.MESSAGE.getHeaderKeyLabel(), SUCCESSFUL);
        return httpHeaders;
    }
}
