package com.devakt.controller;

import com.devakt.service.InitDatabaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://devakt.ddns.net","http://devakt.ddns.net:3000"}, maxAge = 3600)
@AllArgsConstructor
public class InitController {

    private final InitDatabaseService initDatabaseService;

    @GetMapping(value = "/init")
    @ResponseBody
    public ResponseEntity init() {
        initDatabaseService.initData();
        return ResponseEntity.noContent().build();
    }

}
