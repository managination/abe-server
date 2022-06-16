package com.example.abe.jpbc.pairing.accumulator;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.pairing.accumulator.AbstractPairingAccumulator;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 * @since 2.0.0
 */
public class MultiThreadedMulPairingAccumulator extends AbstractPairingAccumulator {


    public MultiThreadedMulPairingAccumulator(Pairing pairing) {
        super(pairing);
    }

    public MultiThreadedMulPairingAccumulator(Pairing pairing, Element value) {
        super(pairing, value);
    }


    protected void reduce(Element value) {
        this.result.mul(value);
    }
}
