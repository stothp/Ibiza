package RSA;

import java.math.BigInteger;

public class EEA {
    private BigInteger lnko = BigInteger.ZERO;
    private BigInteger x = BigInteger.ZERO;
    private BigInteger y = BigInteger.ZERO;

    private static <T extends Number> void arrayShift(T[] array, T newVal){
        for (int i = 0; i < array.length - 1; i++){
            array[i] = array[i + 1];
        }
        array[array.length - 1] = newVal;
    }

    public EEA(BigInteger a, BigInteger b) {
        int k = 0;
        BigInteger[] r = new BigInteger[3];
        BigInteger[] q = new BigInteger[3];
        BigInteger[] x = new BigInteger[3];
        BigInteger[] y = new BigInteger[3];

        if (a.compareTo(b) == -1) {
            r[0] = a;
            r[1] = b;
        } else {
            r[0] = b;
            r[1] = a;
        }

        x[0] = BigInteger.ONE;
        x[1] = BigInteger.ZERO;
        y[0] = BigInteger.ZERO;
        y[1] = BigInteger.ONE;

        while (true) {
            k++;
            q[1] = r[0].divide(r[1]);
            r[2] = r[0].mod(r[1]);
            if (r[2].equals(BigInteger.ZERO)) {
                this.lnko = r[1];
                if (k % 2 != 0) {
                    this.x = x[1].multiply(BigInteger.valueOf(-1));
                } else {
                    this.x = x[1];
                }
                if ((k + 1) % 2 != 0) {
                    this.y = y[1].multiply(BigInteger.valueOf(-1));
                } else {
                    this.y = y[1];
                }
                return;
            }
            x[2] = x[1].multiply(q[1]).add(x[0]);
            y[2] = y[1].multiply(q[1]).add(y[0]);
            arrayShift(q, BigInteger.ZERO);
            arrayShift(r, BigInteger.ZERO);
            arrayShift(x, BigInteger.ZERO);
            arrayShift(y, BigInteger.ZERO);
        }
    }

    public BigInteger getLnko(){
        return lnko;
    }

    public BigInteger getX(){
        return x;
    }

    public BigInteger getY(){
        return y;
    }
}