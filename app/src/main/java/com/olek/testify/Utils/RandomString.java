package com.olek.testify.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomString {

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ch++) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            tmp.append(ch);
        }
        symbols = tmp.toString().toCharArray();
    }

    public List<String> makeList(int n){

        List<String> list = new ArrayList<>();

        for(int i = 0;i < 10; i++){
            list.add(nextString());
        }

        return list;
    }

    public List<CharSequence> makeCharSeqList(int n){

        List<CharSequence> list = new ArrayList<>();

        for(int i = 0;i < 10; i++){
            list.add(nextString());
        }

        return list;
    }

    private final Random random = new Random();

    private final char[] buf;

    public RandomString(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        buf = new char[length];
    }

    public String nextString() {
        for (int i = 0; i < buf.length; i++) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}