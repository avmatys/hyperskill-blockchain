package transaction;

import java.util.Objects;

import java.security.PublicKey;
import entity.User;

public class Transaction {

    private final long id;
    private final String text;  
    private final User from;
    private final User to;
    private final long amount;
    private final PublicKey publicKey;
    private final byte[] signature;

    public Transaction(long id, String text, User from, User to, long amount, PublicKey publicKey, byte[] signature) {
        Objects.requireNonNull(signature, "Signature of messaget must be non null");
        Objects.requireNonNull(from, "Sender must be non null");
        Objects.requireNonNull(to, "Recipient must be non null");

        this.id = id; 
        this.text = text;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.publicKey = publicKey;
        this.signature = signature.clone();
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

    public User getFrom() {
        return this.from;
    }

    public User getTo() {
        return this.to;
    }

    public long getAmount() {
        return this.amount;
    }
}
