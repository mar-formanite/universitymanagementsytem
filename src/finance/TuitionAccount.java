package finance;

public class TuitionAccount extends FinanceAccount {
    public TuitionAccount(String accountId, double balance) {
        super(accountId, balance);
    }

    @Override
    public void generateReport() {
        System.out.println("Tuition Account Report: Balance = $" + getBalance());
    }
}
