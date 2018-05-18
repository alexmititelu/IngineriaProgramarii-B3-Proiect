package info;

/**
 * Clasa Book cu toate datele
 */
public class Book extends Item {

    private int year;
    private String author = "";

    /**
     * Constructor implicit Book
     * @param name nume Book
     * @param path
     * @param year
     * @param author
     */
    public Book(String name, String path, int year, String author) {
        super(name, path);
        this.year = year;
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "year='" + year + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
