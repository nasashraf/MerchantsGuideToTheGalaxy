package com.translator.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputCategoriser {

    public static final String QUESTIONS = "Questions";
    public static final String MATERIAL_COSTS = "Material_Costs";
    public static final String CONVERSIONS = "Conversions";

    public Map<String, List<String>> categorise(List<String> lines) {
        final List<String> conversion = new ArrayList<String>();
        final List<String> materials = new ArrayList<String>();
        final List<String> questions = new ArrayList<String>();

        for (String inputLine : lines) {
            if (inputLine.endsWith("?")) {
                questions.add(inputLine);
            } else if (inputLine.endsWith("Credits")) {
                materials.add(inputLine);
            } else {
                conversion.add(inputLine);
            }
        }

        return new HashMap<String, List<String>>() {{
            put(QUESTIONS, questions);
            put(MATERIAL_COSTS, materials);
            put(CONVERSIONS, conversion);
        }};
    }
}
