package com.baeldung.ls.admin.controller;

import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping(value = "/welcome")
    ResponseEntity<?> welcome() {
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body("WELCOME TO TEST APP. LOGIN PROBABLY WITH SUCCESS");
    }
}
