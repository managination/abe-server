package com.example.abe.jpbc.pairing.a;

import com.example.abe.jpbc.Field;
import com.example.abe.jpbc.PairingParameters;
import com.example.abe.jpbc.Point;
import com.example.abe.jpbc.field.curve.CurveField;
import com.example.abe.jpbc.field.gt.GTFiniteField;
import com.example.abe.jpbc.field.quadratic.DegreeTwoExtensionQuadraticField;
import com.example.abe.jpbc.field.z.ZrField;
import com.example.abe.jpbc.pairing.AbstractPairing;
import com.example.abe.jpbc.pairing.a.TypeATateAffineMillerPairingMap;
import com.example.abe.jpbc.pairing.a.TypeATateNafProjectiveMillerPairingMap;
import com.example.abe.jpbc.pairing.a.TypeATateProjectiveMillerPairingMap;

import java.math.BigInteger;
import com.example.abe.security.SecureRandom;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class TypeAPairing extends AbstractPairing {
    public static final String NAF_MILLER_PROJECTTIVE_METHOD = "naf-miller-projective";
    public static final String MILLER_PROJECTTIVE_METHOD = "miller-projective";
    public static final String MILLER_AFFINE_METHOD = "miller-affine";

    protected int exp2;
    protected int exp1;
    protected int sign1;

    protected BigInteger r;
    protected BigInteger q; 
    protected BigInteger h;

    protected BigInteger phikOnr;

    protected byte[] genNoCofac;

    protected Field Fq;
    protected Field<? extends Point> Fq2;
    protected Field<? extends Point> Eq;


    public TypeAPairing(SecureRandom random, PairingParameters params) {
        super(random);

        initParams(params);
        initMap(params);
        initFields();
    }

    public TypeAPairing(PairingParameters params) {
        this(new SecureRandom(), params);
    }


    protected void initParams(PairingParameters curveParams) {
        // validate the type
        String type = curveParams.getString("type");
        if (type == null || !"a".equalsIgnoreCase(type))
            throw new IllegalArgumentException("Type not valid. Found '" + type + "'. Expected 'a'.");

        // load params
        exp2 = curveParams.getInt("exp2");
        exp1 = curveParams.getInt("exp1");
        sign1 = curveParams.getInt("sign1");

        r = curveParams.getBigInteger("r"); // r = 2^exp2 + sign1 * 2^exp1 + sign0 * 1
        q = curveParams.getBigInteger("q"); // we work in E(F_q) (and E(F_q^2))
        h = curveParams.getBigInteger("h");  // r * h = q + 1

        genNoCofac = curveParams.getBytes("genNoCofac", null);
    }


    protected void initFields() {
        // Init Zr
        Zr = initFp(r);

        // Init Fq
        Fq = initFp(q);

        // Init Eq
        Eq = initEq();

        // Init Fq2
        Fq2 = initFi();

        // k=2, hence phi_k(q) = q + 1, phikOnr = (q+1)/r
        phikOnr = h;

        // Init G1, G2, GT
        G1 = Eq;
        G2 = G1;
        GT = initGT();
    }


    protected Field initFp(BigInteger order) {
        return new ZrField(random, order);
    }

    protected Field<? extends Point> initEq() {
        // Remember the curve is: y^2 = x^3 + ax
        return new CurveField<Field>(random,
                                     Fq.newOneElement(),   // a
                                     Fq.newZeroElement(),  // b
                                     r,                    // order
                                     h,                    // cofactor  (r*h)=q+1=#E(F_q)
                                     genNoCofac);
    }

    protected Field<? extends Point> initFi() {
        return new DegreeTwoExtensionQuadraticField<Field>(random, Fq);
    }

    protected Field initGT() {
        return new GTFiniteField(random, r, pairingMap, Fq2);
    }


    protected void initMap(PairingParameters curveParams) {
        String method = curveParams.getString("method", NAF_MILLER_PROJECTTIVE_METHOD);

        if (NAF_MILLER_PROJECTTIVE_METHOD.endsWith(method)) {
            pairingMap = new TypeATateNafProjectiveMillerPairingMap(this);
        } else if (MILLER_PROJECTTIVE_METHOD.equals(method))
            pairingMap = new TypeATateProjectiveMillerPairingMap(this);
        else if (MILLER_AFFINE_METHOD.equals(method))
            pairingMap = new TypeATateAffineMillerPairingMap(this);
        else
            throw new IllegalArgumentException("Pairing method not recognized. Method = " + method);
    }
}
