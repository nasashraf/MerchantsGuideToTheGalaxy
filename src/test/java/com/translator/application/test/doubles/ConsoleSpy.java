package com.translator.application.test.doubles;

import com.translator.application.Console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleSpy implements Console {
    public List<String> outputsWritten;

    public ConsoleSpy() {
        this.outputsWritten = new ArrayList<String>();
    }

    public void write(String output) {
        outputsWritten.add(output);
    }
}
