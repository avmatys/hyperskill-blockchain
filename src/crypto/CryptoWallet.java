package crypto;

public interface CryptoWallet {
    
    boolean addCoins(long coins);
    boolean subtractCoins(long coins);
    long getBalance();
    
}
