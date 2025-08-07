package block;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import entity.Miner;
import crypto.Hasher;
import transaction.Transaction;

public class BlockFactory {

    public BlockFactory() {
        throw new UnsupportedOperationException("Block Factory can't be instatiated");
    }

    public static Block createBlock(BlockChain.BlockInfo info, Hasher hasher, Miner miner, long timestamp) throws InterruptedException {
        Objects.requireNonNull(hasher, "Hasher must be non null");
        Objects.requireNonNull(info, "Block Info must be non null");
        Objects.requireNonNull(miner, "Miner must be non null");    

        String sId = Long.toString(info.id);
        String sTimestamp = Long.toString(timestamp);
        String sMinerId = Long.toString(miner.getId());
        String transactions = Optional.ofNullable(info.transactions)
                                      .orElse(Collections.emptyList()) 
                                      .stream()
                                      .map(Transaction::getText)
                                      .collect(Collectors.joining());
        Hasher.Result res = hasher.hash(info.leadZeroes, sId, sTimestamp, sMinerId, info.prevHash, transactions);
        return new Block(info.id,res.magic, timestamp, miner, res.time, res.hash, info.prevHash, info.transactions);
    }
}
