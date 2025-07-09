import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        int zeroes = sc.nextInt();
        BlockChain bc = new BlockChain(zeroes);
        for (int i = 0; i < 5; i++){
            bc.addBlock();
        }
    }
}
