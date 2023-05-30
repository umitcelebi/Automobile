package com.ucelebi.automobile.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok().body("OK");
    }
}
