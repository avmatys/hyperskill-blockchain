import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;
import java.util.Arrays;

public class Main {
    
    private static final int MAX_MINERS = 10;
    private static final int INIT_ZEROES = 0;
    private static final int POOL_SIZE = 4;
    private static final int BLOCK_COUNT = 5;
    private static final int PRINT_COUNT = 5;
    private static final int SLEEP_MS = 100; 
    private static final String[] USERS = {"Tom", "Sarah", "Nick"};

    public static void main(String[] args) {

        BlockChain blockchain = new BlockChain(INIT_ZEROES);
        Hasher hasher = new Hasher.SHA256();
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        ExecutorService messages = Executors.newFixedThreadPool(POOL_SIZE);
        
        List<Miner> miners = IntStream.range(0, MAX_MINERS)
                                      .mapToObj(i -> new Miner((long) i, blockchain, hasher))
                                      .toList();
        List<User> users = Arrays.stream(USERS)
                                 .map(name -> new User(name, blockchain))
                                 .toList();

        miners.stream().forEach(miner -> executor.submit(miner));
        users.stream().forEach(user -> messages.submit(user));

        try {
            while (blockchain.getBlockCount() < BLOCK_COUNT) {
                Thread.sleep(SLEEP_MS);
            } 
            executor.shutdownNow();
            messages.shutdownNow();
            blockchain.print(PRINT_COUNT);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            messages.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
