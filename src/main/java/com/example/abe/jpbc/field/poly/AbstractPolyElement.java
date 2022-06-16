package com.example.abe.jpbc.field.poly;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Polynomial;
import com.example.abe.jpbc.field.base.AbstractElement;
import com.example.abe.jpbc.field.base.AbstractFieldOver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public abstract class AbstractPolyElement<E extends Element, F extends AbstractFieldOver>
        extends AbstractElement<F> implements Polynomial<E> {

    protected List<E> coefficients;


    protected AbstractPolyElement(F field) {
        super(field);

        this.coefficients = new ArrayList<E>();
    }


    public int getSize() {
        return coefficients.size();
    }

    public E getAt(int index) {
        return coefficients.get(index);
    }

    public List<E> getCoefficients() {
        return coefficients;
    }

    public E getCoefficient(int index) {
        return coefficients.get(index);
    }

    public int getDegree() {
        return coefficients.size();
    }

}