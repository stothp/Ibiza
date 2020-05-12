package RSA;

import java.math.BigInteger;
import java.util.ArrayList;

public class FME {

    private static Boolean[] powerOfTwo(BigInteger number){
        ArrayList<Boolean> pot = new ArrayList<Boolean>();
        BigInteger a = number;
        while (a.compareTo(BigInteger.ZERO) == 1){
            pot.add (a.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 1);
            a = a.shiftRight(1);
        }
        return pot.toArray(new Boolean[pot.size()]);
    }

    public static BigInteger fme(BigInteger a, BigInteger b, BigInteger m) {
        Boolean[] pot = powerOfTwo(b);

        BigInteger current = a;
        BigInteger result = null;
        for (int i = 0; i < pot.length; i++){
            BigInteger mod = current.mod(m);
            if (pot[i]) {
                if (result != null) {
                    result = result.multiply(mod);
                } else {
                    result = mod;
                }
            }
            current = mod.multiply(mod);
        }

        return result.mod(m);
    }
}
