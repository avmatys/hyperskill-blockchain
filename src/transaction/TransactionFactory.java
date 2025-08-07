package transaction;

import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.security.KeyPair;

import crypto.Signer;
import entity.User;

public class TransactionFactory {
    
    private static List<User> USERS;

    public TransactionFactory() {
        throw new UnsupportedOperationException("Transaction Factory can't be instatiated");
    }

    public static void setUsers(List<User> users) {
        if (TransactionFactory.USERS == null) {
            USERS = Collections.unmodifiableList(users);
        }
    }

    public static Transaction createTransaction(long id, User from, User to, long coins) throws InterruptedException {
        if (to == null) {
            Random random = new Random();
            to = USERS.get(random.nextInt(USERS.size()));
        }
        String message = from.getName() + " sent " + coins + " VC to " + to.getName();    
        byte[] signature = from.getSigner().sign(from.getPrivateKey(), message);
        return new Transaction(id, message, from, to, coins, from.getPublicKey(), signature);
    }
}
