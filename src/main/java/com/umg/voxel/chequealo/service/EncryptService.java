package com.umg.voxel.chequealo.service;

import org.springframework.stereotype.Service;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Service
public class EncryptService {
    /**
     * @param pwd  the password
     * @param seed the seed
     * @return string
     */
    public String encrypt(String pwd, String seed) {
        String result = "";

        try {
            byte[] key = seed.getBytes("UTF-8");
            key = Arrays.copyOf(key, 16);
            SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(pwd.getBytes());
            byte[] tmpByte = Base64.encodeBase64(encrypted);

            result = new String(tmpByte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = pwd;
        }

        return result;
    }

    /**
     * @param pwd  the password
     * @param seed the seed
     * @return string
     */
    public String decrypt(String pwd, String seed) {
        String result = "";

        try {
            byte[] key = seed.getBytes("UTF-8");
            key = Arrays.copyOf(key, 16);
            SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] tmpBClave = Base64.decodeBase64(pwd);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decrypted = cipher.doFinal(tmpBClave);

            result = new String(decrypted);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = pwd;
        }

        return result;
    }
}
