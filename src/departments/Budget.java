package departments;

public class Budget {
    private double allocatedAmount;
    private double spentAmount;

    public Budget(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
        this.spentAmount = 0;
    }

    public void spend(double amount) {
        if (spentAmount + amount <= allocatedAmount) {
            spentAmount += amount;
        }
    }

    public double getRemaining() {
        return allocatedAmount - spentAmount;
    }
}
