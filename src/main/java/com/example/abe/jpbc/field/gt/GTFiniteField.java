package com.example.abe.jpbc.field.gt;

import com.example.abe.jpbc.Field;
import com.example.abe.jpbc.field.base.AbstractFieldOver;
import com.example.abe.jpbc.field.gt.GTFiniteElement;
import com.example.abe.jpbc.pairing.map.PairingMap;

import java.math.BigInteger;
import com.example.abe.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class GTFiniteField<F extends Field> extends AbstractFieldOver<F, GTFiniteElement> {
    protected PairingMap pairing;
    protected BigInteger order;


    public GTFiniteField(SecureRandom random, BigInteger order, PairingMap pairing, F targetField) {
        super(random, targetField);

        this.order = order;
        this.pairing = pairing;
    }

    
    public GTFiniteElement newElement() {
        return new GTFiniteElement(pairing, this);
    }

    public BigInteger getOrder() {
        return order;
    }

    public GTFiniteElement getNqr() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public int getLengthInBytes() {
        return getTargetField().getLengthInBytes();
    }

}
