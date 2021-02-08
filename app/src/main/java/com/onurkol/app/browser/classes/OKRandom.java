package com.onurkol.app.browser.classes;

import java.util.Random;

public class OKRandom {
    private static final String dataRandString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String dataRandAll = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz|!£$%&/=@#";
    private static final String dataRandInteger = "0123456789";
    private static Random RANDOM = new Random();

    public static String randString() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++)
            sb.append(dataRandString.charAt(RANDOM.nextInt(dataRandString.length())));
        return sb.toString();
    }
    public static String randString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(dataRandString.charAt(RANDOM.nextInt(dataRandString.length())));
        return sb.toString();
    }
    public static String randAll() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++)
            sb.append(dataRandAll.charAt(RANDOM.nextInt(dataRandAll.length())));
        return sb.toString();
    }
    public static String randAll(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(dataRandAll.charAt(RANDOM.nextInt(dataRandAll.length())));
        return sb.toString();
    }
    public static int randInteger() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++)
            sb.append(dataRandInteger.charAt(RANDOM.nextInt(dataRandInteger.length())));
        return Integer.parseInt(sb.toString());
    }
    public static int randInteger(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(dataRandInteger.charAt(RANDOM.nextInt(dataRandInteger.length())));
        return Integer.parseInt(sb.toString());
    }
}
