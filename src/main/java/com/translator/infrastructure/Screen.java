package com.translator.infrastructure;

import com.translator.application.Console;

public class Screen implements Console {
    public void write(String output) {
        System.out.println(output);
    }
}
