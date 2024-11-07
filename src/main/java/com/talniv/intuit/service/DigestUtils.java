package com.talniv.intuit.service;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class DigestUtils {

    public static String getFileMD5(String file) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(file));
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

}
