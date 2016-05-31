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

    public final String A1_512 =
            "486f64c1917879417fef082b3381a4e2" +
            "11c324f074654c38823a7b76f830ad00" +
            "fa1fbae42b1285c0352f227524bc9ab1" +
            "6254288dd6863dccd5b9f54a1ad0541b";

    public final String A2_512 =
            "28fbc9bada033b1460642bdcddb90c3f" +
            "b3e56c497ccd0f62b8a2ad4935e85f03" +
            "7613966de4ee00531ae60f3b5a47f8da" +
            "e06915d5f2f194996fcabf2622e6881e";

    public final String A1_256 =
            "00557be5e584fd52a449b16b0251d05d" +
            "27f94ab76cbaa6da890b59d8ef1e159d";

    public final String A2_256 =
            "508f7e553c06501d749a66fc28c6cac0" +
            "b005746d97537fa85d9e40904efed29d";


    public GOSTProviderTest() {
        if (Security.getProvider("GOST") == null) {
            Security.addProvider(new GOSTProvider());
        }
    }

    public static String bytesToHexStr(byte[] bytes) {
        String res = "";
        for (int i = 0; i < bytes.length; ++i ) {
            res += String.format("%02x", bytes[i]);
        }
        return res;
    }

    @Test
    public void testGOST3411_2012_512() {
        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.512");

            assertEquals(A1_512, bytesToHexStr(md.digest(M1)));
            assertEquals(A2_512, bytesToHexStr(md.digest(M2)));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGOST3411_2012_256() {
        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.256");

            assertEquals(A1_256, bytesToHexStr(md.digest(M1)));
            assertEquals(A2_256, bytesToHexStr(md.digest(M2)));
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

}
