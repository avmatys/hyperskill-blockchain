package crypto;

import utils.StringUtils;

public interface Hasher {
    
    Result hash(int zeroes, String... inputs) throws InterruptedException;


    class SHA256 implements Hasher {
        
        @Override
        public Result hash(int leadZeroes, String... inputs) throws InterruptedException {
            if (leadZeroes < 0) {
                throw new IllegalArgumentException("Leading zeroes should be non-negative");
            }
            long magic;
            String hash = null;
            String suffix = String.join("", inputs);
            long start = System.currentTimeMillis();
            for (magic = 0; magic < Long.MAX_VALUE; magic++) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Hash computation was interrupted."); 
                }
                hash = StringUtils.applySha256(Long.toString(magic) + suffix);
                if (StringUtils.countLeadingZeroes(hash) == leadZeroes) {
                    break;
                }
            }
            long end = System.currentTimeMillis();
            int miningTime = (int) (end - start) / 1000;
            return new Result(magic, miningTime, hash);
        }
            
    }

    
    public class Result {
        
        public final long magic;
        public final int secs;
        public final String hash;

        public Result(long magic, int secs, String hash) {
            this.magic = magic;
            this.secs = secs;
            this.hash = hash;
        }

    }

}
