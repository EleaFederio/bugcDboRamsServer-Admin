package dboMars.Utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Security{
    String decrypted = null;

    public String encrypt(String hashThis){
        String name = hashThis;
        String secret = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(name.getBytes(), 0, name.length());
            secret = (new BigInteger(1, md.digest()).toString(16));
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return secret;
    }

    public String decrypt(String decryptThis){

        StringBuffer sb;
        try {
            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            final byte[] array = md.digest(decryptThis.getBytes("UTF-8"));
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return decrypted = sb.toString();
        }
        catch (Exception ex) {
            return null;
        }
    }
}
