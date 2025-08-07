import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;
import java.util.Arrays;

import crypto.*;
import entity.*;
import block.BlockChain;
import transaction.*;

public class Main {
    
    private static final int MAX_MINERS = 10;
    private static final int INIT_ZEROES = 0;
    private static final int POOL_SIZE = 4;
    private static final int BLOCK_COUNT = 15;
    private static final int PRINT_COUNT = 15;
    private static final int SLEEP_MS = 100; 

    public static void main(String[] args) {

        Verifier verifier = new Verifier.SHA1withRSA();
        TransactionBroker broker= new TransactionManager(verifier);
        BlockChain blockchain = new BlockChain(INIT_ZEROES, broker);

        Hasher hasher = new Hasher.SHA256();
        Signer signer = new Signer.SHA1withRSA();
        Generator keygen = new Generator.RSA();

        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        ExecutorService messages = Executors.newFixedThreadPool(POOL_SIZE);
        
        List<User>users = IntStream.range(0, MAX_MINERS)
                         .mapToObj(i -> new User(broker, keygen, signer))
                         .toList();
        List<Miner> miners = IntStream.range(0, MAX_MINERS)
                                      .mapToObj(i -> new Miner(blockchain, hasher, users.get(i)))
                                      .toList();
        TransactionFactory.setUsers(users);

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
