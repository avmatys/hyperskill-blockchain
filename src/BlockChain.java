import java.util.List;
import java.util.ArrayList;

class BlockChain {

    private int leadZeroes;

    private BlockInfo blockInfo;
    private final List<Block> chain;
    private final List<String> chainEvents;
    private final List<String> messages;
    private volatile int chainSize;


    public BlockChain(int leadZeroes) {
        this.leadZeroes = leadZeroes;
        this.chain = new ArrayList<>();
        this.chainEvents = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.blockInfo = new BlockInfo(1L, leadZeroes, "0", null);
    }

    public synchronized BlockInfo getBlockInfo() {
        return this.blockInfo;
    }

    public synchronized boolean addBlock(Block block) {
        if (block == null) {
            throw new IllegalArgumentException("Block can't be null");
        }
        if (blockInfo.prevHash.equals(block.getPrevHash())){
            int prevZeroes = this.leadZeroes;
            this.regulateZeroes(block.getMiningTime());
            this.chainEvents.add(this.createChainEvent(prevZeroes, this.leadZeroes));
            this.chain.add(block);
            this.chainSize = this.chain.size();
            blockInfo = new BlockInfo(blockInfo.id + 1,
                                      leadZeroes,
                                      block.getHash(), 
                                      !this.messages.isEmpty() ? String.join("\n", this.messages) : null);
            this.messages.clear();
            return true;
        }
        return false;
    }
    
    private String createChainEvent(int prev, int curr) {
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
    
    public synchronized void sendMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message can't be null");
        }
        this.messages.add(message);
    }
    
    public int getBlockCount() {
        return this.chainSize;
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
        public final String messages;

        public BlockInfo(long id, int leadZeroes, String prevHash, String messages) {
            this.id = id;
            this.leadZeroes = leadZeroes;
            this.prevHash = prevHash;
            this.messages = messages;
        }

    }
}
