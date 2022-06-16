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
public class SequentialMulPairingAccumulator implements PairingAccumulator {

    private Pairing pairing;
    private Element value;


    public SequentialMulPairingAccumulator(Pairing pairing) {
        this.pairing = pairing;
        this.value = pairing.getGT().newOneElement();
    }

    public SequentialMulPairingAccumulator(Pairing pairing, Element value) {
        this.pairing = pairing;
        this.value = value;
    }

    public Accumulator<Element> accumulate(Callable<Element> callable) {
        throw new IllegalStateException("Not supported!!!");
    }

    public Accumulator<Element> awaitTermination() {
        return this;
    }

    public Element getResult() {
        return value;
    }

    public Pool submit(Callable<Element> callable) {
        throw new IllegalStateException("Not supported!!!");
    }

    public Pool submit(Runnable runnable) {
        throw new IllegalStateException("Not supported!!!");
    }

    public PairingAccumulator addPairing(Element e1, Element e2) {
        value.mul(pairing.pairing(e1, e2));

        return this;
    }

    public PairingAccumulator addPairing(PairingPreProcessing pairingPreProcessing, Element e2) {
        value.mul(pairingPreProcessing.pairing(e2));

        return this;
    }

    public PairingAccumulator addPairingInverse(Element e1, Element e2) {
        value.mul(pairing.pairing(e1, e2).invert());

        return this;
    }

    public Element awaitResult(){
        return value;
    }

}
