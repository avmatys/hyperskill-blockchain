import java.util.Random;

class User implements Runnable {
    
    private final String name;
    private final BlockChain blockchain;
    
    private final static int SLEEP_NO_JOB_MS = 5;
    private final static String[] MESSAGES = { 
                            "Hey, I'm first!",
                            "It's not fair!",
                            "You always will be first because it is your blockchain!",
                            "Anyway, thank you for this amazing chat.",
                            "You're welcome :)",
                            "Hey Tom, nice chat"
                         };


    public User(String name, BlockChain blockchain) {
        this.name = name;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.generateMessage(); 
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


    public void generateMessage() throws InterruptedException {
        Random random = new Random();
        String message = this.name + ": " + MESSAGES[random.nextInt(MESSAGES.length)];
        blockchain.sendMessage(message);
        Thread.sleep(SLEEP_NO_JOB_MS);
    }

}
