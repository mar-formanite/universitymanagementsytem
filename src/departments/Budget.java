package departments;

public class Budget {
    private double allocatedAmount;
    private double spentAmount;

    public Budget(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
        this.spentAmount = 0;
    }

    // ADD THIS GETTER - fixes the error in DepartmentDAO
    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void spend(double amount) {
        if (spentAmount + amount <= allocatedAmount) {
            spentAmount += amount;
        }
    }

    public double getRemaining() {
        return allocatedAmount - spentAmount;
    }

    // Optional: useful for updates
    public double getSpentAmount() {
        return spentAmount;
    }
}
