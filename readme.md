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
import java.security.Security;
import ru.fsb.gost.GOSTProvider;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

if (Security.getProvider("GOST") == null) {
    Security.addProvider(new GOSTProvider());
}

try {
    MessageDigest md = MessageDigest.getInstance("GOST3411-2012.512");
    byte[] result = md.digest("Hello, World!".getBytes());
} catch (NoSuchAlgorithmException e) {
    System.out.println(e.getMessage());
}
