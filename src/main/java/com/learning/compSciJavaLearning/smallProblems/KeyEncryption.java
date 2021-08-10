package com.learning.compSciJavaLearning.smallProblems;

import java.util.Random;

public class KeyEncryption {
    public static byte[] randomKey(int length){
        byte[] dummy = new byte[length];
        Random random = new Random();
        random.nextBytes(dummy);
        return dummy;
    }

    public static KeyPair encrypt(String original){
        byte[] originaBytes = original.getBytes();
        byte[] dummyKey = randomKey(originaBytes.length);
        byte[] encryptedKey = new byte[originaBytes.length];
        for (int i = 0; i < originaBytes.length; i++){
            encryptedKey[i] = (byte) (originaBytes[i] ^ dummyKey[i]);
        }
        return new KeyPair(dummyKey, encryptedKey);
    }

    public static String decrypt(KeyPair kp){
        byte[] decrypted = new byte[kp.key1.length];
        for (int i = 0; i < kp.key1.length; i++){
            decrypted[i] = (byte) (kp.key1[i] ^ kp.key2[i]);
        }

        return new String(decrypted);
    }


    public static void main(String[] args) {
        KeyPair kp = encrypt("Testing an encrypted string jhfdjkhjdkfhgjkdfhljkdfhljf");
        System.out.println(kp.getKey1());
        System.out.println(kp.getKey2());

        String result = decrypt(kp);
        System.out.println(result);

    }

}

class KeyPair{
    public final byte[] key1;
    public final byte[] key2;

    KeyPair(byte[] key1, byte[] key2){
        this.key1 = key1;
        this.key2 = key2;
    }

    public byte[] getKey1() {
        return key1;
    }

    public byte[] getKey2() {
        return key2;
    }

}
