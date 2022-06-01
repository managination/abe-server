package com.example.abe.model;

import javax.persistence.*;
import java.util.ArrayList;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Matrix")
@Table(name = "matrix")
public class Matrix {

    @Id
    @SequenceGenerator(
            name = "matrix_sequence",
            sequenceName = "matrix_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "matrix_sequence"
    )
    private Long id;

    private byte[] arr;

    private ArrayList<byte[]> matr;

    public Matrix() {
    }

    public Matrix(byte[] arr, ArrayList<byte[]> matr) {
        this.arr = arr;
        this.matr = matr;
    }

    public Long getId() {
        return id;
    }

    public byte[] getArr() {
        return arr;
    }

    public ArrayList<byte[]> getMatr() {
        return matr;
    }
}
