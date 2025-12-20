package dao;

import library.Book;
import library.BorrowRecord;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryDAO {

    /**
     * Adds a new book to the library
     */
    public void createBook(Book book) {
        String sql = "INSERT INTO Book (isbn, title, author, available) VALUES (?, ?, ?, TRUE)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());

            pstmt.executeUpdate();
            System.out.println("Book added successfully: " + book.getTitle());
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing book's title and author
     */
    public void updateBook(Book book) {
        String sql = "UPDATE Book SET title = ?, author = ? WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book updated successfully: " + book.getTitle());
            } else {
                System.out.println("No book found with ISBN: " + book.getIsbn());
            }
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a book by ISBN
     */
    public void deleteBook(String isbn) {
        String sql = "DELETE FROM Book WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully: " + isbn);
            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Searches books by title OR author (case-insensitive partial match)
     */
    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT isbn, title, author, available FROM Book " +
                "WHERE LOWER(title) LIKE LOWER(?) OR LOWER(author) LIKE LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String pattern = "%" + searchTerm + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author")
                );
                books.add(book);
            }

            if (books.isEmpty()) {
                System.out.println("No books found matching: '" + searchTerm + "'");
            } else {
                System.out.println("Found " + books.size() + " book(s):");
                for (Book b : books) {
                    System.out.println("  • " + b.getTitle() + " by " + b.getAuthor() + " (ISBN: " + b.getIsbn() + ")");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching books: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Borrows a book - transaction safe
     */
    public void borrowBook(String isbn, String borrowerId, Date dueDate) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update book availability
            String updateSql = "UPDATE Book SET available = FALSE WHERE isbn = ? AND available = TRUE";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, isbn);
                int updated = ps.executeUpdate();
                if (updated == 0) {
                    throw new SQLException("Book is not available or does not exist.");
                }
            }

            // Insert borrow record
            String insertSql = "INSERT INTO BorrowRecord (isbn, borrower_id, borrow_date, due_date) VALUES (?, ?, CURDATE(), ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, isbn);
                ps.setString(2, borrowerId);
                ps.setDate(3, new java.sql.Date(dueDate.getTime()));
                ps.executeUpdate();
            }

            conn.commit();
            System.out.println("Book '" + isbn + "' successfully borrowed by " + borrowerId + ". Due: " + dueDate);
        } catch (SQLException e) {
            System.out.println("Failed to borrow book: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }

    /**
     * Returns a borrowed book - sets available = TRUE
     */
    public void returnBook(String isbn) {
        String sql = "UPDATE Book SET available = TRUE WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book returned successfully: " + isbn);
            } else {
                System.out.println("No borrowed book found with ISBN: " + isbn + " (already available or not exists)");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets full borrow history for a borrower
     */
    public List<BorrowRecord> getBorrowHistory(String borrowerId) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT br.*, b.title, b.author " +
                "FROM BorrowRecord br " +
                "JOIN Book b ON br.isbn = b.isbn " +
                "WHERE br.borrower_id = ? " +
                "ORDER BY br.borrow_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, borrowerId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("=== Borrow History for ID: " + borrowerId + " ===");
            if (!rs.isBeforeFirst()) { // Check if result set is empty
                System.out.println("No borrow history found.");
                return records;
            }

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author")
                );

                BorrowRecord record = new BorrowRecord(
                        book,
                        borrowerId,
                        rs.getDate("borrow_date"),
                        rs.getDate("due_date")
                );
                records.add(record);

                System.out.println("• " + book.getTitle() + " by " + book.getAuthor() +
                        " | Borrowed: " + rs.getDate("borrow_date") +
                        " | Due: " + rs.getDate("due_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving history: " + e.getMessage());
            e.printStackTrace();
        }
        return records;
    }
}