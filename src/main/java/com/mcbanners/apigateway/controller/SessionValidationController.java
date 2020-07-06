package com.mcbanners.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/validate")
public class SessionValidationController {
    @GetMapping
    public ResponseEntity<Map<String, Boolean>> validate() {
        // basically, if this method gets called then the session is valid; it would get stopped earlier in the pipeline otherwise
        // the body is not necessary, but is consistent with other validity checks throughout the project
        return new ResponseEntity<>(Collections.singletonMap("valid", true), HttpStatus.OK);
    }
}
