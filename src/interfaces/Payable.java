package interfaces;

public interface Payable {
    void processPayment(double amount);
    void generateInvoice();
    void getFinancialSummary();
}
