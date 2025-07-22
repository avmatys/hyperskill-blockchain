class BlockFactory {

    public BlockFactory() {
        throw new UnsupportedOperationException("Block Factory can't be instatiated");
    }

    public static Block createBlock(BlockChain.BlockInfo info, Hasher hasher, long minerId, long timestamp) throws InterruptedException {
        if (hasher == null || info == null) {
            throw new IllegalArgumentException("Hasher and BlockInfo should be not null");
        }
        String sId = Long.toString(info.id);
        String sTimestamp = Long.toString(timestamp);
        String sMinerId = Long.toString(minerId);
        Hasher.Result res = hasher.hash(info.leadZeroes, sId, sTimestamp, sMinerId, info.prevHash);
        return new Block(info.id, res.magic, timestamp, minerId, res.secs, res.hash, info.prevHash);
    }
}
