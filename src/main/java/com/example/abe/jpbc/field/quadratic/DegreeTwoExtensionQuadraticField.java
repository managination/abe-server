package com.example.abe.jpbc.field.quadratic;

import com.example.abe.jpbc.Field;
import com.example.abe.jpbc.field.quadratic.DegreeTwoExtensionQuadraticElement;
import com.example.abe.jpbc.field.quadratic.QuadraticField;

import com.example.abe.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class DegreeTwoExtensionQuadraticField<F extends Field> extends QuadraticField<F, DegreeTwoExtensionQuadraticElement> {

    public DegreeTwoExtensionQuadraticField(SecureRandom random, F targetField) {
        super(random, targetField);
    }


    public DegreeTwoExtensionQuadraticElement newElement() {
        return new DegreeTwoExtensionQuadraticElement(this);
    }

}
