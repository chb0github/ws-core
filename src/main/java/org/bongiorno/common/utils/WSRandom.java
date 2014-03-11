package org.bongiorno.common.utils;

import org.springframework.security.crypto.codec.Hex;

import java.security.SecureRandom;
import java.util.*;

public class WSRandom {

    private static final Random S_RNG = new SecureRandom();

    public static long nextId() {
        return Math.abs(S_RNG.nextLong());
    }


    public static String ccId(){
        return hexString(32);
    }

    public static String email(){
        return UUID.randomUUID().toString() + "@bongiorno.org";
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
        int index = S_RNG.nextInt(things.length);
        return things[index];
    }

    public static <T> T selectOne(List<T> things) {
        int index = S_RNG.nextInt(things.size());
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

    public static <T> List<T> selectAFew(int howMany, List<T> things) {
        return (List<T>) selectAFew(howMany,things.toArray(new Object[0]));
    }

    @SafeVarargs
    public static <T> List<T> selectAFew(int howMany, T... things) {
        Class<?> componentType = things.getClass().getComponentType();
        if((Iterable.class.isAssignableFrom(componentType)))
            throw new IllegalArgumentException("Only an array can be passed. your object is " + componentType);

        Set<Integer> alreadyUsed = new HashSet<>();
        List<T> results = new ArrayList<>();
        howMany = Math.min(howMany,things.length);

        while (results.size() < howMany) {
            int index = S_RNG.nextInt(things.length);
            if(!alreadyUsed.contains(index)) {
                results.add(things[index]);
                alreadyUsed.add(index);
            }
        }
        return results;
    }

}
