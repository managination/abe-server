package com.example.abe.jpbc.pairing.map;

import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Field;
import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.PairingPreProcessing;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 * @since 2.0.0
 */
public class DefaultPairingPreProcessing implements PairingPreProcessing {

    protected Pairing pairing;
    protected Element in1;

    public DefaultPairingPreProcessing(Pairing pairing, Element in1) {
        this.pairing = pairing;
        this.in1 = in1;
    }

    public DefaultPairingPreProcessing(Pairing pairing, Field field, byte[] source, int offset) {
        this.pairing = pairing;
        this.in1 = field.newElementFromBytes(source, offset);
    }

    public Element pairing(Element in2) {
        return pairing.pairing(in1, in2);
    }

    public byte[] toBytes() {
        return in1.toBytes();
    }

}
