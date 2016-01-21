package com.translator.infrastructure;

import com.translator.application.InputProcessingService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InputReader {

    public static void main(String[] args) {

        try {
            List<String> input = Files.readAllLines(Paths.get(args[0]));

            InputProcessingService inputProcessingService = new InputProcessingService();
            inputProcessingService.adaptAndProcess(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
