package com.example.abe.jpbc.pairing.accumulator;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.pairing.accumulator.MultiThreadedMulPairingAccumulator;
import com.example.abe.jpbc.pairing.accumulator.PairingAccumulator;
import com.example.abe.jpbc.pairing.accumulator.SequentialMulPairingAccumulator;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 * @since 2.0.0
 */
public class PairingAccumulatorFactory {

    private static final PairingAccumulatorFactory INSTANCE = new PairingAccumulatorFactory();

    public static PairingAccumulatorFactory getInstance() {
        return INSTANCE;
    }

    private boolean multiThreadingEnabled;


    private PairingAccumulatorFactory() {
        this.multiThreadingEnabled = false; // Runtime.getRuntime().availableProcessors() > 1;
    }


    public PairingAccumulator getPairingMultiplier(Pairing pairing) {
        return isMultiThreadingEnabled() ? new MultiThreadedMulPairingAccumulator(pairing)
                : new SequentialMulPairingAccumulator(pairing);
    }

    public PairingAccumulator getPairingMultiplier(Pairing pairing, Element element) {
        return isMultiThreadingEnabled() ? new MultiThreadedMulPairingAccumulator(pairing, element)
                : new SequentialMulPairingAccumulator(pairing, element);
    }

    public boolean isMultiThreadingEnabled() {
        return multiThreadingEnabled;
    }

    public void setMultiThreadingEnabled(boolean multiThreadingEnabled) {
        this.multiThreadingEnabled = multiThreadingEnabled;
    }

}
