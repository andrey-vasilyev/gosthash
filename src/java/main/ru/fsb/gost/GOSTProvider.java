package ru.fsb.gost;

import java.security.Provider;

public final class GOSTProvider extends Provider {

    public GOSTProvider() {
        super("GOST", 0.1, "The Russian Federal standard (GOST) provider " +
                "(implements client mechanisms for: GOST R 34.11-2012)");
        put("MessageDigest.GOST3411-2012.256", GOST3411_2012_256.class.getCanonicalName());
        put("MessageDigest.GOST3411-2012.512", GOST3411_2012_512.class.getCanonicalName());
    }
}
