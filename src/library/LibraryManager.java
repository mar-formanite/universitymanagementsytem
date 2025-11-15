package library;

import people.Person;
import java.util.Date;

public class LibraryManager {
    private Library library;

    public LibraryManager(Library library) {
        this.library = library;
    }

    public void borrowBook(Person person, String isbn) {
        Book book = library.findBook(isbn);
        if (book != null && book.isAvailable()) {
            book.borrow();
            BorrowRecord record = new BorrowRecord(book, person.getId(), new Date(), new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // 7 days
            System.out.println(person.getName() + " borrowed " + book.getTitle());
        }
    }

    public void returnBook(String isbn) {
        Book book = library.findBook(isbn);
        if (book != null) {
            book.returnBook();
            System.out.println("Book returned: " + book.getTitle());
        }
    }
}