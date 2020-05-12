package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Prime {
    public static BigInteger getPrime(int bits) {
        BigInteger primeCandidate;
        Boolean prime;
        do {
            prime = true;
            primeCandidate = new BigInteger(bits, new SecureRandom());
            if (primeCandidate.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
                prime = false;
                continue;
            }
            MR mr = new MR(primeCandidate);
            for (int i = 0; i < 3; i++){
                BigInteger a;
                do {
                    a = new BigInteger(bits, new Random());
                } while (a.compareTo(primeCandidate) != -1);
                if (mr.checkAgainstA(a)) {
                    prime = false;
                    break;
                }
            }
        } while (!prime);
        return primeCandidate;
    }
}
