package library;

import java.util.Date;

public class BorrowRecord {
    private Book book;
    private String borrowerId;
    private Date borrowDate;
    private Date dueDate;

    public BorrowRecord(Book book, String borrowerId, Date borrowDate, Date dueDate) {
        this.book = book;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public double calculateFine() {
        // Simple fine calculation
        Date today = new Date();
        if (today.after(dueDate)) {
            long daysOverdue = (today.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
            return daysOverdue * 0.5; // $0.50 per day
        }
        return 0;
    }
}
