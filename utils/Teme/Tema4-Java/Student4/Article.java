package info;

import java.util.Arrays;

public class Article extends Item {

    private int year;
    private String[] authorList;

    public Article(String name, String path, int year, String ...authorList) {
        super(name, path);
        this.year = year;
        this.authorList = authorList;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getAuthorList() {
        return authorList;
    }

    public void setAuthorList(String ...authorList) {
        this.authorList = authorList;
    }

    @Override
    public String toString() {
        return "Article{" +
                "year='" + year + '\'' +
                ", authorList=" + Arrays.toString(authorList) +
                '}';
    }
}
