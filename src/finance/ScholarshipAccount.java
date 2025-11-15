package finance;

public class ScholarshipAccount extends FinanceAccount {
    public ScholarshipAccount(String accountId, double balance) {
        super(accountId, balance);
    }

    @Override
    public void generateReport() {
        System.out.println("Scholarship Account Report: Balance = $" + getBalance());
    }
}