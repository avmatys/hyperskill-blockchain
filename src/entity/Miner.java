package entity;

import java.util.Date;
import crypto.Hasher;
import block.BlockChain;
import block.Block;
import block.BlockFactory;

public class Miner implements Runnable {

    private final long id;
    private final Hasher hasher;
    private final BlockChain blockchain;
    private final static int SLEEP_NO_JOB_MS = 100;


    public Miner(long id, BlockChain blockchain, Hasher hasher) throws IllegalArgumentException {
        if (blockchain == null || hasher == null) {
            throw new IllegalArgumentException("Blockchain and hasher can't be null");
        }
        this.id = id;
        this.hasher = hasher;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.mine();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void mine() throws InterruptedException {
        BlockChain.BlockInfo info = blockchain.getBlockInfo();
        if (info == null) {
            Thread.sleep(SLEEP_NO_JOB_MS);
            return;
        }
        long timestamp = new Date().getTime();
        Block block = BlockFactory.createBlock(info, hasher, id, timestamp);
        blockchain.addBlock(block);
    }

}
