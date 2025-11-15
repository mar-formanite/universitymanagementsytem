package finance;

public class SalaryAccount extends FinanceAccount {
    public SalaryAccount(String accountId, double balance) {
        super(accountId, balance);
    }

    @Override
    public void generateReport() {
        System.out.println("Salary Account Report: Balance = $" + getBalance());
    }
}
