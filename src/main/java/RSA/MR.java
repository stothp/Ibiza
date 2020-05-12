package RSA;

import java.math.BigInteger;

public class MR {
    private BigInteger s, d, r, n;

    public class SDR {
        private BigInteger s, d, r;

        public SDR(BigInteger n) {
            BigInteger a = n;
            BigInteger s = BigInteger.ZERO;
            while (a.mod(BigInteger.TWO) == BigInteger.ZERO){
                a = a.divide(BigInteger.TWO);
                s = s.add(BigInteger.ONE);
            }
            this.s = s;
            this.d = a;
            if (s.compareTo(BigInteger.ZERO) == 1) {
                this.r = s.subtract(BigInteger.ONE);
            } else {
                this.r = s;
            }
        }

        public BigInteger getS() {
            return s;
        }

        public BigInteger getD() {
            return d;
        }

        public BigInteger getR() {
            return r;
        }
    }

    public MR (BigInteger n){
        SDR sdr = new SDR(n.subtract(BigInteger.ONE));
        this.n = n;
        this.s = sdr.getS();
        this.d = sdr.getD();
        this.r = sdr.getR();
        //System.out.println (n + ": " + s + " " + d + " " + r);
    }

    public Boolean checkAgainstA(BigInteger a){
        if (a.compareTo(n) != -1){
            throw new IllegalArgumentException("The base has to be smaller than n!");
        }

//        System.out.println ("+ " + a + " " + d + " " + n);
//        System.out.println ("* " + RSA.FME.fme(a, d, n));
        if (FME.fme(a, d, n).equals(BigInteger.ONE)){
            return false;
        }

        BigInteger current = FME.fme(a, d, n);
        for (BigInteger r = BigInteger.ZERO; r.compareTo(s) == -1; r = r.add(BigInteger.ONE)){
//            System.out.println (current.mod(n));
            BigInteger mod = current.mod(n);
            if (mod.equals(n.subtract(BigInteger.ONE))){
                return false;
            }
            current = mod.multiply(mod);
        }

        return true;
    }

}
