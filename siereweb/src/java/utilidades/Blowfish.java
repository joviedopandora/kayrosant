package utilidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Blowfish {
 
   

    
    
    
    public static String encriptar(String cleartext, String key) throws Exception {
        try {
            return crypt(cleartext, key, Cipher.ENCRYPT_MODE);
        } catch (Exception ex) {
            throw new Exception("Imposible encriptar los datos.");
        }
    }

    public static String desEncriptar(String ciphertext, String key) throws Exception {
        try {
            return crypt(ciphertext, key, Cipher.DECRYPT_MODE);
        } catch (Exception ex) {
            throw new Exception("Imposible desencriptar los datos.");
        }
    }

    private static String crypt(String input, String key, int mode) throws Exception {
        // Install SunJCE provider
        Provider sunJce = new com.sun.crypto.provider.SunJCE();
        Security.addProvider(sunJce);

        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
        kgen.init(448);
        SecretKey skey = kgen.generateKey();

        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");

        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(mode, skeySpec);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
        CipherOutputStream cos = new CipherOutputStream(bos, cipher);

        int length = 0;
        byte[] buffer = new byte[8192];

        while ((length = bis.read(buffer)) != -1) {
            cos.write(buffer, 0, length);
        }

        bis.close();
        cos.close();

        return bos.toString();
    }

    

}
