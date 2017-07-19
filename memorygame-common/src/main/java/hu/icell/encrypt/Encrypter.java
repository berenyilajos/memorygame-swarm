package hu.icell.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class Encrypter {

    public Encrypter() {
        // TODO Auto-generated constructor stub
    }
    
    public static String getMD5(String rawPass) {
        MessageDigest md = null;
        try {
          md = MessageDigest.getInstance("MD5");
        } catch(NoSuchAlgorithmException e) {
          e.printStackTrace();
          return null;
        }

        md.update(rawPass.getBytes());
        byte[] mdBytes = md.digest();
        
        // only java 8
//        String passEncrypted = Base64.getEncoder().encodeToString(mdBytes);
        String passEncrypted = DatatypeConverter.printBase64Binary(mdBytes);
        
        return passEncrypted;
    }

}
