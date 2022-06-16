package com.example.abe.jpbc.field.base;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Field;
import com.example.abe.jpbc.FieldOver;
import com.example.abe.jpbc.field.base.AbstractField;

import java.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public abstract class AbstractFieldOver<F extends Field, E extends Element> extends AbstractField<E> implements FieldOver<F, E> {
    protected F targetField;


    protected AbstractFieldOver(SecureRandom random, F targetField) {
        super(random);
        this.targetField = targetField;
    }


    public F getTargetField() {
        return targetField;
    }

}
