package people;

import interfaces.Payable;
import java.util.List;

public abstract class Staff extends Person implements Payable {
    private double salary; // Encapsulated
    private List<String> assignedTasks; // Aggregation: Tasks as strings

    public Staff(String name, String id, String email, String phone, double salary, List<String> tasks) {
        super(name, id, email, phone);
        this.salary = salary;
        this.assignedTasks = tasks;
    }

    // Encapsulation
    public double getSalary() {
        return salary;
    }

    public void adjustSalary(double newSalary) {
        this.salary = newSalary;
    }

    // Payable interface
    @Override
    public void processPayment(double amount) {
        System.out.println("Processed salary payment of $" + amount);
    }

    @Override
    public void generateInvoice() {
        System.out.println("Generated salary invoice.");
    }

    @Override
    public void getFinancialSummary() {
        System.out.println("Salary: $" + salary);
    }

    // Abstract implementations
    @Override
    public void register() {
        System.out.println("Staff " + getName() + " registered.");
    }

    @Override
    public void login() {
        System.out.println("Staff " + getName() + " logged in.");
    }

    @Override
    public List<String> getPermissions() {
        return List.of("manage_facilities", "assist_admin");
    }

    @Override
    public double calculateFeesOrSalary() {
        return salary;
    }

    // Polymorphism
    @Override
    public String getRole() {
        return "Staff";
    }

    @Override
    public double calculateWorkload() {
        return assignedTasks.size() * 2; // Based on tasks
    }

    @Override
    public void accessLibrary() {
        System.out.println("Borrow limit: 10 books.");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Staff Dashboard: Tasks=" + assignedTasks.size() + ", Salary=" + salary);
    }
}