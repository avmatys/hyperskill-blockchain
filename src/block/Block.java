package block;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import entity.Miner;
import transaction.Transaction;

public class Block {
    
    private final long id;
    private final long magic;
    private final long timestamp;
    
    private final Miner miner;
    private final int miningTime;
    private final long coins;

    private final String hash;
    private final String prevHash;
    private final List<Transaction> transactions;

    public Block(long id, long magic, long timestamp, Miner miner, int miningTime, 
                 String hash, String prevHash, List<Transaction> transactions) {
        this.id = id;
        this.magic = magic;
        this.timestamp = timestamp;
        this.miner = miner;
        this.miningTime = miningTime;
        this.hash = hash;
        this.prevHash = prevHash;
        this.coins = 0;
        this.transactions = transactions == null 
                                ? new ArrayList<>() 
                                : new ArrayList<>(transactions);
    }

    public Block(Block block, long coins) {
        this.coins = coins;
        this.id = block.id;
        this.magic = block.magic;
        this.timestamp = block.timestamp;
        this.miner = block.miner;
        this.miningTime = block.miningTime;
        this.hash = block.hash;
        this.prevHash = block.prevHash;
        this.transactions = block.transactions == null 
                                ? new ArrayList<>() 
                                : new ArrayList<>(block.transactions);
    }

    public long getId() {
        return this.id;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPrevHash() {
        return this.prevHash;
    }

    public int getMiningTime() {
        return this.miningTime;
    }
    
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }

    @Override
    public String toString() {
        String allTransactions = this.transactions != null && !this.transactions.isEmpty() 
                                         ? "\n" + transactions.stream()
                                                              .map(Transaction::getText)
                                                              .collect(Collectors.joining("\n"))
                                         : "no transactions";
        return "Block:\n" +
               "Created by: " + miner.getName() + "\n" +
               miner.getName() + " gets " + coins + " VC\n" +
               "Id: " + id + "\n" +
               "Timestamp: " + timestamp + "\n" +
               "Magic number: " + magic + "\n" +
               "Hash of the previous block: \n" + prevHash + "\n" +
               "Hash of the block: \n" + hash + "\n" +
               "Block data: " + allTransactions + "\n" +
               "Block was generating for " + miningTime + " seconds";
    }
    
}
