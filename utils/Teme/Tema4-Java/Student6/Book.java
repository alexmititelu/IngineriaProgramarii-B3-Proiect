package com.company;


import java.util.Arrays;

public class Book extends Document {
    private String publisher;

    public Book(String title, String path, int year, String... autors) throws Exception{
        super(title, path, Arrays.asList(autors), year);

        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "publisher='" + publisher + '\'' +
                "} " + super.toString();
    }
}
