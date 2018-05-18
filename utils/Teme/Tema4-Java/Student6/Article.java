package com.company;



import java.util.Arrays;

public class Article extends Document {
    public Article(String title, String path, int year, String... autors) throws Exception{
        super(title, path, Arrays.asList(autors), year);
    }

    @Override
    public String toString() {
        return "Article{} " + super.toString();
    }
}
