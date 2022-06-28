package com.example.abe.controller;

import com.example.abe.dcpabe.other.GlobalParameters;
import com.example.abe.service.GlobalParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/globalParameters")
public class GlobalParametersController {

    private final GlobalParametersService globalParametersService;

    @Autowired
    public GlobalParametersController(GlobalParametersService globalParametersService) {
        this.globalParametersService = globalParametersService;
    }

    @GetMapping
    public GlobalParameters getGlobalParameters() throws IOException, ClassNotFoundException {
        return globalParametersService.getGlobalParameters();
    }

    @PostMapping
    public void createGlobalParameters() throws IOException, ClassNotFoundException {
        globalParametersService.createGlobalParameters();
    }
}
