package com.example.abe.controller;

import com.example.abe.service.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/matrix")
public class MatrixController {

    private final MatrixService matrixService;

    @Autowired
    public MatrixController(MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    @GetMapping
    public List<ArrayList<String>> getAllMatrices() {
        return matrixService.getAllMatrices();
    }

    @PostMapping
    public void createMatrix(@RequestBody String text) {
        matrixService.createMatrix(text);
    }
}
