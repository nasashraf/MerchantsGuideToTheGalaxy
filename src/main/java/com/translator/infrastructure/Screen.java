package com.translator.infrastructure;

public class Screen implements Console{
    public void write(String output) {
        System.out.println(output);
    }
}
