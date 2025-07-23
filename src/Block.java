class Block {
    
    private final long id;
    private final long magic;
    private final long timestamp;
    private final long minerId; 
    private final int miningTime;
    private final String hash;
    private final String prevHash;
    

    public Block(long id, long magic, long timestamp, long minerId, int miningTime, String hash, String prevHash) {
        this.id = id;
        this.magic = magic;
        this.timestamp = timestamp;
        this.minerId = minerId;
        this.miningTime = miningTime;
        this.hash = hash;
        this.prevHash = prevHash;
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

    public void print() {
        System.out.println("Block:");
        System.out.println("Created by miner # " + this.minerId);
        System.out.println("Id: " + this.id);
        System.out.println("Timestamp: " + this.timestamp);
        System.out.println("Magic number: " + this.magic);
        System.out.println("Hash of the previous block: \n" + this.prevHash);
        System.out.println("Hash of the block: \n" + this.hash);
        System.out.println("Block was generating for " + this.miningTime + " seconds");
    }
    
}
