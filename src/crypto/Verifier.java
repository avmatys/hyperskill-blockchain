package crypto;

import java.util.Arrays;
import java.util.Objects;
import java.security.PublicKey;
import java.security.Signature;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface Verifier {
    
    boolean verify(PublicKey publicKey, byte[] signature, String... data);
    boolean verify(PublicKey publicKey, byte[] signature, byte[] data);


    class SHA1withRSA implements Verifier {

        @Override
        public boolean verify(PublicKey publicKey, byte[] signature, byte[] data) {
            Objects.requireNonNull(publicKey, "Public key must not be null");
            Objects.requireNonNull(signature, "Signature must not be null");
            Objects.requireNonNull(data, "Data must not be null");

            try {
                Signature sig = Signature.getInstance("SHA1withRSA");
                sig.initVerify(publicKey);
                sig.update(data);
                return sig.verify(signature);
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                throw new RuntimeException("Cryptographic verification failed due to an internal error.", e);
            }   
        }
        
        @Override
        public boolean verify(PublicKey publicKey, byte[] signature, String... data) {
            Objects.requireNonNull(data, "Data should be specified");

            String combined = Arrays.stream(data)
                                    .filter(Objects::nonNull)
                                    .reduce("", String::concat);
            return this.verify(publicKey, signature, combined.getBytes(StandardCharsets.UTF_8));
        }

    }

}
