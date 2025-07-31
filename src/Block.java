class Block {
    
    private final long id;
    private final long magic;
    private final long timestamp;
    private final long minerId; 
    private final int miningTime;
    private final String hash;
    private final String prevHash;
    private final Object data;    

    public Block(long id, 
                 long magic, 
                 long timestamp, 
                 long minerId, 
                 int miningTime, 
                 String hash, 
                 String prevHash, 
                 Object data) {
        this.id = id;
        this.magic = magic;
        this.timestamp = timestamp;
        this.minerId = minerId;
        this.miningTime = miningTime;
        this.hash = hash;
        this.prevHash = prevHash;
        this.data = data;
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

    public Object getData() {
        return this.data;
    }

    public int getMiningTime() {
        return this.miningTime;
    }
    @Override
    public String toString() {
        return "Block:\n" +
               "Created by miner # " + minerId + "\n" +
               "Id: " + id + "\n" +
               "Timestamp: " + timestamp + "\n" +
               "Magic number: " + magic + "\n" +
               "Hash of the previous block: \n" + prevHash + "\n" +
               "Hash of the block: \n" + hash + "\n" +
               "Block data: " + (data != null ? ("\n" + data.toString()) : "no messages") + "\n" +
               "Block was generating for " + miningTime + " seconds";
    }
    
}
