package message;

import java.util.Random;
import java.security.KeyPair;

import crypto.Signer;

public class MessageFactory {

    private final static String[] MSGS = {
        "Hey, I'm first!",
        "It's not fair!",
        "You always will be first because it is your blockchain!",
        "You're welcome :)",
        "Hey, nice chat"
    };

    public MessageFactory() {
        throw new UnsupportedOperationException("Message Factory can't be instatiated");
    }

    public static Message createMessage(long id, String name, KeyPair pair, Signer signer) throws InterruptedException {
        Random random = new Random();
        String message = name + ": " + MSGS[random.nextInt(MSGS.length)];
        byte[] signature = signer.sign(pair.getPrivate(), message);
        return new Message(id, message, pair.getPublic(), signature);
    }
}
