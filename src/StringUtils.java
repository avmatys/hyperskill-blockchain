import java.lang.StringBuilder;
import java.security.MessageDigest;

class StringUtil {
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int countLeadingZeroes(String hash) {
        int res = 0;
        if (hash != null && hash.length() > 0) {
            int i = 0;
            while (i < hash.length() && hash.charAt(i++) == '0'){
                res = i;
            }
        }
        return res;
    }
}
