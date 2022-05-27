package com.example.abe.service;

import com.example.abe.model.Matrix;
import com.example.abe.repository.MatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MatrixService {

    private final MatrixRepository matrixRepository;

    @Autowired
    public MatrixService(MatrixRepository matrixRepository) {
        this.matrixRepository = matrixRepository;
    }

    public List<ArrayList<String>> getAllMatrices() {
        List<Matrix> matrices = matrixRepository.findAll();
        List<ArrayList<String>> textMatrix = new ArrayList<>();
        for (Matrix m : matrices) {
            List<String> text = new ArrayList<>();
            ArrayList<byte[]> matr = new ArrayList<>(m.getMatr());
            for (byte[] t : matr ) {
                text.add(new String(t));
            }
            textMatrix.add(new ArrayList<>(text));
        }
        return textMatrix;
    }

    public void createMatrix(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        Matrix matrix = new Matrix(bytes, new ArrayList<>(Arrays.asList(bytes, bytes, bytes)));
        matrixRepository.save(matrix);
    }
}
