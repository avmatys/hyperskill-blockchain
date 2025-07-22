import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class BlockChain {

    private int leadZeroes;

    private BlockInfo blockInfo;
    private final List<Block> chain;

    public BlockChain(int leadZeroes) {
        this.leadZeroes = leadZeroes;
        this.chain = new ArrayList<>();
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
            this.regulateZeroes(block.getMiningTime());
            this.chain.add(block);
            BlockInfo next = new BlockInfo(blockInfo.id + 1, leadZeroes, block.getHash());
            blockInfo = next;
            return true;
        }
        return false;
    }

    public void regulateZeroes(int time) {
        if (time < 1) {
            leadZeroes += 3;
        } else if (time < 5) {
            leadZeroes += 2;
        } else if (time < 10) {
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
