package com.example.abe.dcpabe.other;

import com.example.abe.dcpabe.key.PublicKey;
import com.example.abe.dcpabe.key.SecretKey;
import com.example.abe.jpbc.Element;
import com.example.abe.jpbc.Pairing;
import com.example.abe.jpbc.pairing.PairingFactory;

public class SimpleDCPABE {

    public static AuthorityKeys authoritySetup(String authorityID, GlobalParameters GP, String... attributes) {
        AuthorityKeys authorityKeys = new AuthorityKeys(authorityID);

        Pairing pairing = PairingFactory.getPairing(GP.getPairingParameters());
        Element eg1g1 = pairing.pairing(GP.getG1(), GP.getG1()).getImmutable();
        for (String attribute : attributes) {
            Element ai = pairing.getZr().newRandomElement().getImmutable();
            Element yi = pairing.getZr().newRandomElement().getImmutable();

            authorityKeys.getPublicKeys().put(attribute, new PublicKey(
                    eg1g1.powZn(ai).toBytes(),
                    GP.getG1().powZn(yi).toBytes()));

            authorityKeys.getSecretKeys().put(attribute, new SecretKey(ai.toBytes(), yi.toBytes()));
        }

        return authorityKeys;
    }


}
