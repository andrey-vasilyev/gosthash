#About

This is a Java implementation of [RFC6986](https://tools.ietf.org/html/rfc6986) and
[GOST R 34.11-2012](http://protect.gost.ru/document.aspx?control=7&baseC=6&page=1&month=6&year=-1&search=&id=180209)
It provides new [java.security.Provider](http://docs.oracle.com/javase/8/docs/api/java/security/Provider.html) named "GOST"
with "GOST3411-2012.512" and "GOST3411-2012.256" message digest functions for 512-bit and 256-bit version of GOST3411-2012
respectively.

This implementation was integrated into [Bouncy Castle](https://www.bouncycastle.org)

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
    java -jar build/libs/gosthash-0.4-jmh.jar -wi 5 -i 15 -f 1 -jvmArgs "-server -XX:+AggressiveOpts"

Here are some results taken on Ubutnu 14.04 desktop with AMD FX-8320 CPU and openjdk 1.8.0_91:

| Unoptimized | Optimized | Optimized + Unrolled |
| ----------- | --------- | -------------------- |
|    0.101    |   1.259   |         1.963        |

The results are given in operations per second i.e. the number of times benchmark function is executed per second (more is better).

#Credit

For table optimization, which dramatically (12x) improves performance, credit goes to [Oleksandr Kazymyrov](https://github.com/okazymyrov/stribog).
Some clarifications on how this optimization works are given in section 3 of his [paper](https://okazymyrov.github.io/assets/attachments/articles/2013/6795e47b49068629cbcb2d323faafa.pdf)
