package com.example.notes.Helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class KeyGenerator {
    public static String generateKey(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash).substring(0,16);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private static String bytesToHex(byte[] hash){
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
