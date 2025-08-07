package block;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import crypto.Verifier;
import transaction.Transaction;
import transaction.TransactionBroker;

public class BlockChain {

    private int leadZeroes;
    private BlockInfo blockInfo;
    private final List<Block> chain;
    private final List<String> events;
    private final TransactionBroker broker;

    private final static int REWARD = 100;

    public BlockChain(int leadZeroes, TransactionBroker broker) {
        this.leadZeroes = leadZeroes;
        this.chain = new ArrayList<>();
        this.events = new ArrayList<>();
        this.broker = broker;
        this.blockInfo = new BlockInfo(1L, leadZeroes, "0", new ArrayList<>());
    }

    public synchronized BlockInfo getBlockInfo() {
        return this.blockInfo;
    }

    public synchronized int getBlockCount() {
        return this.chain.size();
    }

    public synchronized int addBlock(Block block) {
        Objects.requireNonNull(block, "Block must be non null");

        if (blockInfo.prevHash.equals(block.getPrevHash())){
            int diff = this.regulate(block.getMiningTime());
            this.events.add(this.getLeadZeroesEvent(diff));
            
            this.chain.add(new Block(block, REWARD));
            this.blockInfo = new BlockInfo(blockInfo.id + 1, leadZeroes, 
                                           block.getHash(), broker.getTransactions()); 
            broker.removeTransactions();
            return REWARD;
        }
        return 0;
    }
    
    private String getLeadZeroesEvent(int diff) {
        if (diff == 0) return "N stays the same";
        if (diff > 0) return "N was increased to " + this.leadZeroes;
        return "N was decreased by " + (-diff);
    }

    public int regulate(int time) {
        int prev = this.leadZeroes;
        if (time < 15 && this.leadZeroes < 4) {
            this.leadZeroes++;
        } else if (this.leadZeroes > 0){
            this.leadZeroes--;
        }
        return this.leadZeroes - prev;
    }

    public void print(int count){
        for (int i = 0; i < Math.min(this.chain.size(), count); i++) {
            System.out.println(this.chain.get(i));
            System.out.println(this.events.get(i));
            System.out.println();
        }
    }

    public static class BlockInfo {

        public final int leadZeroes;
        public final String prevHash;
        public final long id;
        public final List<Transaction> transactions;

        public BlockInfo(long id, int leadZeroes, String prevHash, List<Transaction> transactions) {
            this.id = id;
            this.leadZeroes = leadZeroes;
            this.prevHash = prevHash;
            this.transactions = transactions != null ? new ArrayList<>(transactions) : new ArrayList<>();
        }

    }
}
