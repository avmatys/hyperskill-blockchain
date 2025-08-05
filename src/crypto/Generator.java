package crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public interface Generator {

    KeyPair generateKeyPair();

    class RSA implements Generator {
        
        @Override
        public KeyPair generateKeyPair() {
            try {
                KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
                keygen.initialize(1024);
                return keygen.generateKeyPair();
            } catch(NoSuchAlgorithmException e) {
                throw new RuntimeException("Cryptographic generator creation failed due to an internal error.", e);
            }
        }
        
    }

}
