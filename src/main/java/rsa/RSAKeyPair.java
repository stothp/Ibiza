package rsa;

public class RSAKeyPair{
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}
