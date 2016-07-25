package ru.fsb.gost.test;

import org.junit.Test;
import static org.junit.Assert.*;
import ru.fsb.gost.GOSTProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class GOSTProviderTest {

    private final byte[] M1 = {
            0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35,
            0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37,
            0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39,
            0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31,
            0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33,
            0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35,
            0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37,
            0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30
    };

    private final byte[] M2 = {
            (byte)0xfb, (byte)0xe2, (byte)0xe5, (byte)0xf0, (byte)0xee, (byte)0xe3, (byte)0xc8, (byte)0x20,
            (byte)0xfb, (byte)0xea, (byte)0xfa, (byte)0xeb, (byte)0xef, (byte)0x20, (byte)0xff, (byte)0xfb,
            (byte)0xf0, (byte)0xe1, (byte)0xe0, (byte)0xf0, (byte)0xf5, (byte)0x20, (byte)0xe0, (byte)0xed,
            (byte)0x20, (byte)0xe8, (byte)0xec, (byte)0xe0, (byte)0xeb, (byte)0xe5, (byte)0xf0, (byte)0xf2,
            (byte)0xf1, (byte)0x20, (byte)0xff, (byte)0xf0, (byte)0xee, (byte)0xec, (byte)0x20, (byte)0xf1,
            (byte)0x20, (byte)0xfa, (byte)0xf2, (byte)0xfe, (byte)0xe5, (byte)0xe2, (byte)0x20, (byte)0x2c,
            (byte)0xe8, (byte)0xf6, (byte)0xf3, (byte)0xed, (byte)0xe2, (byte)0x20, (byte)0xe8, (byte)0xe6,
            (byte)0xee, (byte)0xe1, (byte)0xe8, (byte)0xf0, (byte)0xf2, (byte)0xd1, (byte)0x20, (byte)0x2c,
            (byte)0xe8, (byte)0xf0, (byte)0xf2, (byte)0xe5, (byte)0xe2, (byte)0x20, (byte)0xe5, (byte)0xd1
    };

    private final byte[] M3 = new byte[10 * 1024 * 1024];

    private final String A1_512 =
            "1b54d01a4af5b9d5cc3d86d68d285462" +
            "b19abc2475222f35c085122be4ba1ffa" +
            "00ad30f8767b3a82384c6574f024c311" +
            "e2a481332b08ef7f41797891c1646f48";

    private final String A2_512 =
            "1e88e62226bfca6f9994f1f2d51569e0" +
            "daf8475a3b0fe61a5300eee46d961376" +
            "035fe83549ada2b8620fcd7c496ce5b3" +
            "3f0cb9dddc2b6460143b03dabac9fb28";

    private final String A3_512 =
            "150d0e80a28e18f7517ee992483b6763" +
            "2091f19fa13fd8b0bc4dd2ae7c4c942e" +
            "f20388583f0deb517197c5c8acb5ad4f" +
            "22f2b8b3f5f8cb84a29370260862f52c";

    private final String A1_256 =
            "9d151eefd8590b89daa6ba6cb74af927" +
            "5dd051026bb149a452fd84e5e57b5500";

    private final String A2_256 =
            "9dd2fe4e90409e5da87f53976d7405b0" +
            "c0cac628fc669a741d50063c557e8f50";


    public GOSTProviderTest() {
        if (Security.getProvider("GOST") == null) {
            Security.addProvider(new GOSTProvider());
        }
    }

    private static String bytesToHexStr(byte[] bytes) {
        String res = "";
        for (byte aByte : bytes) {
            res += String.format("%02x", aByte);
        }
        return res;
    }

    private static byte[] reverse(byte[] src) {
        int len = src.length;
        byte[] result = new byte[len];

        for (int i = 0; i < len; i++) {
            result[len - 1 - i] = src[i];
        }

        return result;
    }

    @Test
    public void testGOST3411_2012_512() {
        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.512");

            /**
             * Looking carefully at GOST examples it becomes clear that the example messages
             * are given in reverse order (who knows why!?) e.g. the M2 example message is read
             * from the end which is nonsense for a block algorithm. So we have to reverse
             * example messages before taking their digests.
             */
            byte[] m1 = reverse(M1);
            byte[] m2 = reverse(M2);

            assertEquals(A1_512, bytesToHexStr(md.digest(m1)));
            assertEquals(A2_512, bytesToHexStr(md.digest(m2)));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGOST3411_2012_256() {
        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.256");

            byte[] m1 = reverse(M1);
            byte[] m2 = reverse(M2);

            assertEquals(A1_256, bytesToHexStr(md.digest(m1)));
            assertEquals(A2_256, bytesToHexStr(md.digest(m2)));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDigestByteUpdate() {
        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.512");

            byte[] m = reverse(M2);
            for (byte b : m) {
                md.update(b);
            }

            assertEquals(A2_512, bytesToHexStr(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDigestBlockUpdate() {
        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.512");

            for (int i = 0; i < M3.length; i += 8) {
                md.update(M3, i, 8);
            }
            assertEquals(A3_512, bytesToHexStr(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }
}
