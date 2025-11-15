package library;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books; // Aggregation: Books exist independently

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book findBook(String isbn) {
        return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
    }
}
