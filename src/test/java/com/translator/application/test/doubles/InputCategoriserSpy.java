package com.translator.application.test.doubles;

import com.translator.application.InputCategoriser;

import java.util.List;
import java.util.Map;

public class InputCategoriserSpy extends InputCategoriser {

    private Map<String, List<String>> categories;
    public List<String> categoriseCalledWith;

    @Override
    public Map<String, List<String>> categorise(final List<String> lines) {
        this.categoriseCalledWith = lines;
        return categories;
    }

    public void setCategoriesStub(Map<String, List<String>> categories) {
        this.categories = categories;
    }

}
