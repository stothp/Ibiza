package RSA;

import java.math.BigInteger;
import java.security.PublicKey;

public class RSA {
    public static RSAKeyPair generateKeys(int bits){
        BigInteger p = Prime.getPrime(bits);
        BigInteger q = Prime.getPrime(bits);

        BigInteger n = p.multiply(q);
        BigInteger fin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(3);
        EEA eea;
        while (!(eea = new EEA(fin, e)).getLnko().equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
        };

        BigInteger d = eea.getX();
        if (d.compareTo(BigInteger.TWO) == -1){
            d = d.add(fin);
        }

        RSAPublicKey publicKey = new RSAPublicKey(e, n);
        RSAPrivateKey privateKey = new RSAPrivateKey(d, n);
        return new RSAKeyPair(publicKey, privateKey);
    }

    public static BigInteger encode (BigInteger message, RSAPublicKey publicKey){
        return FME.fme(message, publicKey.getE(), publicKey.getN());
    }

    public static BigInteger decode (BigInteger cipherText, RSAPrivateKey privateKey){
        return FME.fme(cipherText, privateKey.getD(), privateKey.getN());
    }
}
