import java.util.Date;

class BlockChain {

    private int zeroes;
    private Block head;
    private Block tail;

    public BlockChain(int zeroes) {
        this.zeroes = zeroes;
    }

    public void addBlock() {
        // Get details to generate a new block
        Date date = new Date();
        long nextId = this.tail == null ? 1 : this.tail.getId() + 1;
        String hash = this.tail == null ? "0" : this.tail.getHash();
        // Generate a hash for a new block
        long magic;
        String nextHash = null;
        String suffix = Long.toString(nextId) + Long.toString(date.getTime()) + hash;
        for (magic = 0; magic < Long.MAX_VALUE; magic++) {
            nextHash = StringUtil.applySha256(Long.toString(magic) + suffix);
            if (StringUtil.countLeadingZeroes(nextHash) == this.zeroes){
                break;           
            }
        }
        Date end = new Date();
        // Create a new block and add to the chain
        this.tail = new Block(nextId, magic, date, nextHash, this.tail); 
        if (this.head == null)
            this.head = this.tail;
        // Based on the task print newly created block
        this.tail.print();
        System.out.printf("Block was generating for %.0f seconds\n\n", (end.getTime() - date.getTime()) / 1000.);
    }

    public void print(){
        Block curr = this.head;
        while (curr != null) {
            curr.print();
            System.out.println();
            curr = curr.getNext();
        }
    }

}
