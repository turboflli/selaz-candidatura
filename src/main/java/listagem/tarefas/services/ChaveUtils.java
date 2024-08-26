package listagem.tarefas.services;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class ChaveUtils {

    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static final String gerarChave(){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(128); // block size is 128bits
        SecretKey secretKey = keyGenerator.generateKey();

        /* Complete hashed password in hexadecimal format */
        return Hex.encodeHexString(secretKey.getEncoded());
    }

    public static final String gerarIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        return Hex.encodeHexString(ivParameterSpec.getIV());
    }

    public static String encriptografar(String plainText, String chave)
            throws Exception {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");


        /* Complete hashed password in hexadecimal format */
        byte[] encodedKey = decoder.decode(chave);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        cipher.init(Cipher.DECRYPT_MODE, originalKey, ivParameterSpec);
        byte[] plainTextByte = plainText.getBytes("UTF-8");
        cipher.init(Cipher.ENCRYPT_MODE, originalKey, ivParameterSpec);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        return Base64.getEncoder().encodeToString(encryptedByte);
    }

    public static String descriptografar(String encryptedText, String chave, String ivParameterSpec) {



        try {
            IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(ivParameterSpec));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            /* Complete hashed password in hexadecimal format */
            //Base64.Decoder decoder = Base64.getDecoder();
            SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(chave), "AES");

            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] byteText = Base64.getDecoder().decode(encryptedText);//encryptedText.substring(0, encryptedText.length()-1).getBytes();
            byte[] decryptedByte = cipher.doFinal(byteText);
            //byte[] byteText = cipher.doFinal();
            //byte[] decryptedByte = cipher.doFinal(byteText);
            String decryptedText = new String(decryptedByte).replace("\r","");
            return decryptedText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        }

        return "";
    }
}
