package com.example.abe;

import com.example.abe.dcpabe.other.DCPABE;
import de.mirkosertic.bytecoder.api.Export;

public class HelloWorld {

    @Export("createGlobalParameters")
    public static String createGlobalParameters() {
        return DCPABE.globalSetup(160).toString();
    }

    public static void main(String[] args) {

        System.out.println(createGlobalParameters());
        System.out.println("Hello World!");
    }
}
