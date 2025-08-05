package crypto;

import java.util.Arrays;
import java.util.Objects;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.nio.charset.StandardCharsets;

public interface Signer {

    byte[] sign(PrivateKey privateKey, byte[] data);
    byte[] sign(PrivateKey privateKey, String... data);


    class SHA1withRSA implements Signer {
        
        @Override
        public byte[] sign(PrivateKey privateKey, byte[] data) {
            Objects.requireNonNull(privateKey, "Private key must not be null");
            Objects.requireNonNull(data, "Data must be not null");
            
            try {
                Signature sig = Signature.getInstance("SHA1withRSA");
                sig.initSign(privateKey);
                sig.update(data);
                return sig.sign();
            } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
                throw new RuntimeException("Cryptographic sign failed due to an internal error.", e);
            }
        }
        
        @Override
        public byte[] sign(PrivateKey privateKey, String... data) {
            Objects.requireNonNull(data, "Data should be specified");
            String combined = Arrays.stream(data)
                                    .filter(Objects::nonNull)
                                    .reduce("", String::concat);
            return this.sign(privateKey, combined.getBytes(StandardCharsets.UTF_8));
        }

    }

}
