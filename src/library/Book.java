package library;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return available; }

    public String getAuthor() {
        return author;
    }
    public void borrow() {
        available = false;
    }

    public void returnBook() {
        available = true;
    }
}

