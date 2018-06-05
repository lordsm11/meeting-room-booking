package com.devakt.controller;

import com.devakt.service.InitDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"}, maxAge = 3600)
public class InitController {

    @Autowired
    private InitDatabaseService initDatabaseService;

    @GetMapping(value = "/init")
    @ResponseBody
    public ResponseEntity init() {
        initDatabaseService.initData();
        return ResponseEntity.noContent().build();
    }

}
