package com.example.abe.jpbc.pairing.map;

import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.PairingPreProcessing;
import com.example.abe.jpbc.Point;
import com.example.abe.jpbc.pairing.map.AbstractMillerPairingMap;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public abstract class AbstractMillerPairingPreProcessing implements PairingPreProcessing {

    protected AbstractMillerPairingMap.MillerPreProcessingInfo processingInfo;


    protected AbstractMillerPairingPreProcessing() {
    }

    protected AbstractMillerPairingPreProcessing(Point in1, int processingInfoSize) {
        this.processingInfo = new AbstractMillerPairingMap.MillerPreProcessingInfo(processingInfoSize);
    }

    protected AbstractMillerPairingPreProcessing(Pairing pairing, byte[] source, int offset) {
        this.processingInfo = new AbstractMillerPairingMap.MillerPreProcessingInfo(pairing, source, offset);
    }

    public byte[] toBytes() {
        if (processingInfo != null)
            return processingInfo.toBytes();
        else
            return new byte[0];
    }
}
