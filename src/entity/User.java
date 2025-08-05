package entity;

import java.security.KeyPair;

import block.BlockChain;
import crypto.Generator;
import crypto.Signer;
import message.Message;
import message.MessageFactory;

public class User implements Runnable {
    
    private final String name;
    private final BlockChain blockchain;
    private final Signer signer;
    private final KeyPair keyPair;
    
    private final static int SLEEP_NO_JOB_MS = 5;

    public User(String name, BlockChain blockchain, Generator keygen, Signer signer) {
        this.name = name;
        this.blockchain = blockchain;
        this.signer = signer;
        this.keyPair = keygen.generateKeyPair();
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.generateMessage(); 
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void generateMessage() throws InterruptedException {
        long id = blockchain.getMessageId();
        Message message = MessageFactory.createMessage(id, this.name, this.keyPair, this.signer);
        blockchain.sendMessage(message);
        Thread.sleep(SLEEP_NO_JOB_MS);
    }

}
