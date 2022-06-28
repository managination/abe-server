package com.example.abe.dcpabe.other;

import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.pairing.PairingFactory;
import com.example.abe.jpbc.pairing.a1.TypeA1CurveGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SetupGP {

    public static GlobalParameters globalSetup(int lambda) {
        GlobalParameters params = new GlobalParameters();

        params.setPairingParameters(new TypeA1CurveGenerator(3, lambda).generate());
        Pairing pairing = PairingFactory.getPairing(params.getPairingParameters());

        params.setG1(pairing.getG1().newRandomElement().getImmutable());

        return params;
    }

    public static GlobalParameters readGlobalParameters() throws IOException, ClassNotFoundException {
        try (
                ObjectInputStream inputGlobalParameters = new ObjectInputStream(
                        new FileInputStream("src/main/resources/globalParameters.json"))) {
            return (GlobalParameters) inputGlobalParameters.readObject();
        }
    }

}
