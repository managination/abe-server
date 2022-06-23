package com.example.abe.jpbc.field.z;

import com.example.abe.jpbc.field.base.AbstractField;
import com.example.abe.jpbc.field.z.ZElement;

import java.math.BigInteger;
import com.example.abe.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class ZField extends AbstractField<ZElement> {

    public ZField() {
        this(new SecureRandom());
    }

    public ZField(SecureRandom random) {
        super(random);
    }


    public ZElement newElement() {
        return new ZElement(this);
    }

    public BigInteger getOrder() {
        return BigInteger.ZERO;
    }

    public ZElement getNqr() {
        throw new IllegalStateException("Not implemented yet!!!");
    }

    public int getLengthInBytes() {
        return -1;
    }
    
}
