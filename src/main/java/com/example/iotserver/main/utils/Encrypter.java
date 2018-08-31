package com.example.iotserver.main.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encrypter {

    public static String encrypt(String password) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< digest.length ;i++)
        {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        String encrypted = sb.toString();
        return encrypted;
    }
}
