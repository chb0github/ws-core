package org.bongiorno.common.utils;

import org.springframework.security.crypto.codec.Hex;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WSRandom {

    private static final Random S_RNG = new SecureRandom();

    public static long nextId() {
        return Math.abs(S_RNG.nextLong());
    }

    public static String bsHardwareID() {
        return hexString(32);
    }

    public static String railHardwareID() {
        return hexString(16);
    }

    public static String chargerRfid() {
        return hexString(8);
    }

    public static String ccId(){
        return hexString(32);
    }

    public static String email(){
        return UUID.randomUUID().toString() + "@bongiorno.com";
    }

    public static String hexString(int byteCount) {
        return prvtHexString(S_RNG,byteCount);
    }
    private static String prvtHexString(Random rng, int byteCount) {
        byte[] bytes = new byte[byteCount];
        rng.nextBytes(bytes);
        return new String(Hex.encode(bytes));
    }

    public static String mac() {
        String hex = hexString(6);
        return hex.replaceAll("(..)\\B", "$1:");
    }

    @SafeVarargs
    public static <T> T selectOne(T ... things) {
        int index = Math.abs(S_RNG.nextInt(things.length));
        return things[index];
    }

    public static <T> T selectOne(List<T> things) {
        int index = Math.abs(S_RNG.nextInt(things.size()));
        return things.get(index);
    }

    public static byte[] someData() {
        return someData(32);
    }
    public static byte[] someData(int count) {
        byte[] someData = new byte[count];
        S_RNG.nextBytes(someData);
        return someData;
    }

}
