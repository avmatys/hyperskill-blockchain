package block;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import crypto.Verifier;
import message.Message;

public class BlockChain {

    private int leadZeroes;
    private BlockInfo blockInfo;
    private final List<Block> chain;
    private final List<String> chainEvents;

    private final Verifier verifier;
    private final AtomicLong messageId;
    private final List<Message> messages;


    public BlockChain(int leadZeroes, Verifier verifier) {
        this.leadZeroes = leadZeroes;
        this.chain = new ArrayList<>();
        this.chainEvents = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.verifier = verifier;
        this.messageId = new AtomicLong(0);
        this.blockInfo = new BlockInfo(1L, leadZeroes, "0", new ArrayList<>());
    }

    public synchronized BlockInfo getBlockInfo() {
        return this.blockInfo;
    }

    public synchronized int getBlockCount() {
        return this.chain.size();
    }

    public synchronized boolean addBlock(Block block) {
        if (block == null) {
            throw new IllegalArgumentException("Block can't be null");
        }
        if (blockInfo.prevHash.equals(block.getPrevHash())){
            int prevZeroes = this.leadZeroes;
            this.regulateZeroes(block.getMiningTime());
            this.chainEvents.add(this.getLeadZeroesEvent(prevZeroes, this.leadZeroes));
            this.chain.add(block);
            this.blockInfo = new BlockInfo(blockInfo.id + 1, leadZeroes, block.getHash(), this.messages); 
            this.messages.clear();
            return true;
        }
        return false;
    }
    
    private String getLeadZeroesEvent(int prev, int curr) {
        if (prev == curr) return "N stays the same";
        if (prev < curr) return "N was increased to " + curr;
        return "N was decreased by " + (prev - curr);
    }

    public void regulateZeroes(int time) {
        if (time < 30) {
            leadZeroes++;
        } else  {
            leadZeroes--;
        } 
    }

    public synchronized long getMessageId() {
        return this.messageId.get();
    }
    
    public synchronized boolean sendMessage(Message msg) {
        if (msg == null) {
            throw new IllegalArgumentException("Message can't be null");
        }
        if (msg.getId() < this.messageId.get()){
            return false;
        }
        if(!this.verifier.verify(msg.getPublicKey(), msg.getSignature(), msg.getText())){
            return false;
        }
        this.messages.add(msg);
        this.messageId.incrementAndGet();
        return true;
    }


    public void print(int count){
        for (int i = 0; i < Math.min(this.chain.size(), count); i++) {
            System.out.println(this.chain.get(i));
            System.out.println(this.chainEvents.get(i));
            System.out.println();
        }
    }

    public static class BlockInfo {

        public final int leadZeroes;
        public final String prevHash;
        public final long id;
        public final List<Message> messages;

        public BlockInfo(long id, int leadZeroes, String prevHash, List<Message> messages) {
            this.id = id;
            this.leadZeroes = leadZeroes;
            this.prevHash = prevHash;
            this.messages = messages != null ? new ArrayList<>(messages) : new ArrayList<>();
        }

    }
}
