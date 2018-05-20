package com.company;


import java.util.Arrays;

public class Manual extends Document {
    public Manual(String title, String path) throws Exception {
        super(title, path, Arrays.asList("Default"), 0);
    }

    @Override
    public String toString() {
        return "Manual{} " + super.toString();
    }
}
