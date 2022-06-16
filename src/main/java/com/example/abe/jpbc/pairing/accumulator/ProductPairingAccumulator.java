package com.example.abe.jpbc.pairing.accumulator;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.PairingPreProcessing;
import com.example.abe.jpbc.pairing.accumulator.PairingAccumulator;
import com.example.abe.jpbc.util.concurrent.Pool;
import com.example.abe.jpbc.util.concurrent.accumultor.Accumulator;

import java.util.concurrent.Callable;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 * @since 2.0.0
 */
public class ProductPairingAccumulator implements PairingAccumulator {

    private Pairing pairing;

    private int cursor;
    private Element[] in1, in2;
    private Element result;


    public ProductPairingAccumulator(Pairing pairing, int n) {
        this.pairing = pairing;
        this.in1 = new Element[n];
        this.in2 = new Element[n];
        this.cursor = 0;
    }


    public Accumulator<Element> accumulate(Callable<Element> callable) {
        throw new IllegalStateException("Not supported!!!");
    }

    public Accumulator<Element> awaitTermination() {
        awaitResult();

        return this;
    }

    public Element getResult() {
        return result;
    }

    public Pool<Element> submit(Callable<Element> callable) {
        throw new IllegalStateException("Not supported!!!");
    }

    public Pool<Element> submit(Runnable runnable) {
        throw new IllegalStateException("Not supported!!!");
    }



    public PairingAccumulator addPairing(Element e1, Element e2) {
        in1[cursor] =  e1;
        in2[cursor++] =  e2;

        return this;
    }

    public PairingAccumulator addPairing(PairingPreProcessing pairingPreProcessing, Element e2) {
        throw new IllegalStateException("Not supported!!!");
    }

    public PairingAccumulator addPairingInverse(Element e1, Element e2) {
        throw new IllegalStateException("Not supported!!!");
    }

    public Element awaitResult(){
        return (result = pairing.pairing(in1, in2));
    }

}
