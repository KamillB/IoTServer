package com.example.iotserver.main.utils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encrypter {

    public static String encrypt(String password) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encrypted = DatatypeConverter.printHexBinary(digest).toLowerCase();

        return encrypted;
    }
}
