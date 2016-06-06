package ru.fsb.gost.benchmark;

import ru.fsb.gost.GOSTProvider;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;

import java.security.Security;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

public class GOSTBenchmark {

    @State(Scope.Thread)
    public static class ThreadState {
        public MessageDigest md512;
        public static byte[] payload;
        static {
            payload = new byte[10 * 1024 * 1024];
            Arrays.fill(payload, (byte)0xFF);
        }

        @Setup
        public void setup () {
            if (Security.getProvider("GOST") == null) {
                Security.addProvider(new GOSTProvider());
            }

            try {
                md512 = MessageDigest.getInstance("GOST3411-2012.512");
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Benchmark
    public byte[] bench512(ThreadState state) {
         return state.md512.digest(state.payload);
    }
}
