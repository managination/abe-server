package com.example.abe;

import com.example.abe.dcpabe.other.AuthorityKeys;
//import com.example.abe.dcpabe.other.GlobalParameters;
import com.example.abe.dcpabe.other.SetupGP;
import com.example.abe.dcpabe.other.SimpleDCPABE;
import de.mirkosertic.bytecoder.api.Export;

import java.io.IOException;

public class HelloWorld {

//    static GlobalParameters gp;
//    static {
//        try {
//            gp = SetupGP.readGlobalParameters();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    @Export("method")
    public static String method() throws IOException, ClassNotFoundException {

        AuthorityKeys authorityKeys = SimpleDCPABE.authoritySetup(
                "First",
                SetupGP.readGlobalParameters(),
                "attr1");

        return authorityKeys.getAuthorityName();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println(method());
        System.out.println("Hello World!");

    }

}
