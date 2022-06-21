package com.example.abe.myJdk;

import sun.security.jca.GetInstance;
import sun.security.jca.GetInstance.Instance;
import sun.security.jca.Providers;
import sun.security.provider.SunEntries;
import sun.security.util.Debug;

import java.security.*;
import java.security.Provider.Service;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecureRandom extends Random {

    public SecureRandom() {
        /*
         * This call to our superclass constructor will result in a call
         * to our own {@code setSeed} method, which will return
         * immediately when it is passed zero.
         */
        super(0);
    }

}
