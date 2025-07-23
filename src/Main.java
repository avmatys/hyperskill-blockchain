import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {
    
    private static int MAX_MINERS = 15;
    private static int INIT_ZEROES = 0;
    private static int POOL_SIZE = 4;
    private static int BLOCK_COUNT = 5;
    private static int SLEEP_MS = 100; 

    public static void main(String[] args) {

        BlockChain blockchain = new BlockChain(INIT_ZEROES);
        Hasher hasher = new Hasher.SHA256();
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        List<Miner> miners = new ArrayList<>();

        for (int i = 0; i < MAX_MINERS; i++) {
            miners.add(new Miner((long)i, blockchain, hasher));
        }
        
        for (Miner miner: miners){
            executor.submit(miner);
        }

        try {
            while (blockchain.getBlockCount() < BLOCK_COUNT) {
                System.out.println("Not completed yet....");
                System.out.println("Current count " + blockchain.getBlockCount());
                Thread.sleep(SLEEP_MS);
            } 
            executor.shutdownNow();
            blockchain.print(BLOCK_COUNT);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
