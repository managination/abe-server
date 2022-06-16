package com.example.abe.jpbc.field.base;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Vector;
import com.example.abe.jpbc.field.base.AbstractElement;
import com.example.abe.jpbc.field.base.AbstractFieldOver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public abstract class AbstractVectorElement<E extends Element, F extends AbstractFieldOver> extends AbstractElement<F> implements Vector<E> {

    protected List<E> coeff;

    protected AbstractVectorElement(F field) {
        super(field);

        this.coeff = new ArrayList<E>();
    }


    public E getAt(int index) {
        return coeff.get(index);
    }

    public int getSize() {
        return coeff.size();
    }

}