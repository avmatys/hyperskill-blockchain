package entity;

import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;

import block.BlockChain;
import crypto.Generator;
import crypto.Signer;
import crypto.CryptoWallet;
import transaction.*;

public class User implements Runnable, CryptoWallet {
    
    private static long ID = 1;

    private final long id;
    private final String name;
    private long  balance;
    
    private final TransactionBroker broker;
    private final Signer signer;
    private final KeyPair keyPair;

    private final static int SLEEP_NO_JOB_MS = 5;

    public User(TransactionBroker broker, Generator keygen, Signer signer) {
        this.id = ID++;
        this.name = "User " + this.id;
        this.broker = broker;
        this.signer = signer;
        this.keyPair = keygen.generateKeyPair();
    }
    
    public String getName() {
        return this.name;
    }

    public PublicKey getPublicKey() {
        return this.keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.keyPair.getPrivate();
    }

    public Signer getSigner() {
        return this.signer;
    }

    @Override
    public synchronized boolean addCoins(long coins) {
        if (coins <= 0) return false;
        this.balance += coins;
        return true;
    }
    
    @Override
    public synchronized boolean subtractCoins(long coins) {
        if (coins <= 0 || coins > this.balance) return false;
        this.balance -= coins;
        return true;
    }
    
    @Override
    public synchronized long getBalance() {
        return this.balance;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.generateTransaction(null, 30); 
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void generateTransaction(User to, long coins) throws InterruptedException {
        long id = broker.requestTransactionId();
        Transaction transaction = TransactionFactory.createTransaction(id, this, to, coins);
        broker.addTransaction(transaction);
        Thread.sleep(SLEEP_NO_JOB_MS);
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true; 
        if (obj == null || getClass() != obj.getClass()) return false;
        User  other = (User) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }

}
