import java.util.Date;

class Block {
    
    private long id;
    private long magic;
    private Date date;
    private Block prev;
    private Block next;
    private String hash;

    public Block(long id, long magic, Date date, String hash, Block prev) {
        this.id = id;
        this.date = date;
        this.prev = prev;
        this.hash = hash;
        this.magic = magic;
        if (prev != null){
            this.prev.next = this;
        }
    }

    public long getId() {
        return this.id;
    }

    public Block getNext() {
        return this.next;
    }

    public Date getDate() {
        return this.date;
    }
    
    public String getHash() {
        return this.hash;
    }

    public void print() {
        System.out.println("Block:");
        System.out.println("Id: " + this.id);
        System.out.println("Timestamp: " + this.date.getTime());
        System.out.println("Magic number: " + this.magic);
        System.out.println("Hash of the previous block: \n" + (this.prev != null ? this.prev.hash : "0"));
        System.out.println("Hash of the block: \n" + this.hash);
    }
    
}
