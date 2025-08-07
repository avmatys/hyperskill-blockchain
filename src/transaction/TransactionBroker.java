package transaction;

import java.util.List;

public interface TransactionBroker {
    
    long requestTransactionId();
    List<Transaction> getTransactions();
    boolean addTransaction(Transaction transaction);
    void removeTransactions();

}
