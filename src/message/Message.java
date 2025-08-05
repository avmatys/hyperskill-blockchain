package message;

import java.security.PublicKey;

public class Message {

    private final long id;
    private final String text;  
    private final PublicKey publicKey;
    private final byte[] signature;

    public Message(long id, String text, PublicKey publicKey, byte[] signature) {
        if (signature == null){
            throw new IllegalArgumentException("Signature of message can't be null");
        }
        this.id = id; 
        this.text = text;
        this.publicKey = publicKey;
        this.signature = signature.clone();
    }

    public Message(long id, String text, PublicKey publicKey) {
        this(id, text, publicKey, new byte[0]);
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public byte[] getSignature() {
        return this.signature.clone();
    }
    
    public String getText() {
        return this.text;
    }
    
    public long getId() {
        return this.id;
    }

}
