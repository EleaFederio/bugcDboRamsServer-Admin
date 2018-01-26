package dboMars.Utilities;

public class SecretKey {

    public static String generate(int length){
        String hashKey = createSecretKey(length);
        //System.out.println(hashKey);
        return  hashKey;
    }

    private static String createSecretKey(int length){
        String secretCode = "";
        for(int x = 0; x < length - 2; x++){
            secretCode = secretCode + randomCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        String randomNumbers = randomCharacters("0123456789");
        secretCode = insertAtRamdom(secretCode, randomNumbers);
        return  secretCode;
    }

    private static String randomCharacters(String letters){
        int n = letters.length();
        int r = (int)(n * Math.random());
        return letters.substring(r, r + 1);
    }

    private static String insertAtRamdom(String str, String toInsert){
        int n = str.length();
        int r = (int)((n + 1) * Math.random());
        return str.substring(0, r) + toInsert + str.substring(r);
    }

}
