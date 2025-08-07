package transaction;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import crypto.Verifier;

public class TransactionManager implements TransactionBroker {
    
    private final Verifier verifier;
    private final AtomicLong transactionId;
    private final List<Transaction> transactions;

    public TransactionManager(Verifier verifier) {
        this.verifier = verifier;        
        this.transactionId = new AtomicLong(0);
        this.transactions = new ArrayList<>();
    }

    @Override
    public synchronized long requestTransactionId() {
        return this.transactionId.get(); 
    }

    @Override
    public synchronized List<Transaction> getTransactions() {
        return new ArrayList<>(this.transactions); 
    }

    @Override 
    public synchronized boolean addTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction must be non null");
        
        if (transaction.getId() < this.transactionId.get()) 
            return false;
        if (!this.verifier.verify(transaction.getPublicKey(), transaction.getSignature(), transaction.getText())) 
            return false;
        if (transaction.getFrom().getBalance() < transaction.getAmount()) 
            return false;
        if (transaction.getAmount() <= 0)
            return false;
        if (transaction.getFrom() == transaction.getTo())
            return false;
        this.transactions.add(transaction);
        this.transactionId.incrementAndGet();
        return true;

    }

    @Override
    public synchronized void removeTransactions() {
        this.transactions.clear();
    }

}
