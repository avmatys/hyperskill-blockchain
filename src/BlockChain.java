import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class BlockChain {

    private int leadZeroes;

    private BlockInfo blockInfo;
    private final List<Block> chain;
    private final List<String> transactions;

    public BlockChain(int leadZeroes) {
        this.leadZeroes = leadZeroes;
        this.chain = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.blockInfo = new BlockInfo(1L, leadZeroes, "0");
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
            this.transactions.add(this.createTransactionMsg(prevZeroes, this.leadZeroes));
            this.chain.add(block);
            blockInfo = new BlockInfo(blockInfo.id + 1, leadZeroes, block.getHash());;
            return true;
        }
        return false;
    }
    
    private String createTransactionMsg(int prev, int curr) {
        if (prev == curr) return "N stays the same";
        if (prev < curr) return "N was increased to " + curr;
        return "N was decreased by " + (prev - curr);
    }

    public void regulateZeroes(int time) {
        if (time < 2) {
            leadZeroes++;
        } else  {
            leadZeroes--;
        } 
    }
    
    public synchronized int getBlockCount() {
        return this.chain.size();
    }

    public void print(int count){
        for (int i = 0; i < Math.min(this.chain.size(), count); i++) {
            chain.get(i).print();
            System.out.println(this.transactions.get(i));
            System.out.println();
        }
    }

    public static class BlockInfo {

        public final int leadZeroes;
        public final String prevHash;
        public final long id;

        public BlockInfo(long id, int leadZeroes, String prevHash) {
            this.id = id;
            this.leadZeroes = leadZeroes;
            this.prevHash = prevHash;
        }

    }
}
