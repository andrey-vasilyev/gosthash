#About

This is a Java implementation of [RFC6986](https://tools.ietf.org/html/rfc6986) and
[GOST R 34.11-2012](http://protect.gost.ru/document.aspx?control=7&baseC=6&page=1&month=6&year=-1&search=&id=180209)
It provides new [java.security.Provider](http://docs.oracle.com/javase/8/docs/api/java/security/Provider.html) named "GOST"
with "GOST3411-2012.512" and "GOST3411-2012.256" message digest functions for 512-bit and 256-bit version of GOST3411-2012
respectively.

At the moment there are some limitations:

* Input data size is limited to 2Gb, as it is max that you can put into standard [MessageDigest](http://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html)
* There is no way to input an array of bits of arbitrary size i.e. you can't get the digest of exactly 505 bits of information although it is allowed by the specification

#Usage

Here is a simple usage example to get 512-bit digest of "Hello, World!" string:

```java
import ru.fsb.gost.GOSTProvider;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (Security.getProvider("GOST") == null) {
            Security.addProvider(new GOSTProvider());
        }

        try {
            MessageDigest md = MessageDigest.getInstance("GOST3411-2012.512");
            byte[] result = md.digest("Hello, World!".getBytes());
            System.out.println(Arrays.toString(result));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

#Benchmark

To build and run [JMH](http://openjdk.java.net/projects/code-tools/jmh/) benchmark:

    gradle jmhJar
    java -jar build/libs/gosthash-0.2-jmh.jar -wi 5 -i 5 -f 1 -jvmArgs '-server -XX:+AggressiveOpts'

Here is a sample result taken on Ubutnu 14.04 desktop with AMD FX-8320 CPU:

    Benchmark                Mode  Cnt  Score   Error  Units
    GOSTBenchmark.bench512  thrpt    5  0.101 ± 0.001  ops/s