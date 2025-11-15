package people;

import interfaces.Payable;
import java.util.ArrayList;
import java.util.List;

public abstract class Faculty extends Person implements Payable {
    private double salary; // Encapsulated
    private List<courses.Course> taughtCourses; // Aggregation: Courses exist independently

    public Faculty(String name, String id, String email, String phone, double salary) {
        super(name, id, email, phone);
        this.salary = salary;
        this.taughtCourses = new ArrayList<>();
    }

    // Encapsulation: Controlled salary access
    public double getSalary() {
        return salary;
    }

    public void adjustSalary(double newSalary) {
        // Only through HR (simulated)
        this.salary = newSalary;
    }

    public void addCourse(courses.Course course) {
        taughtCourses.add(course);
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
        System.out.println("Faculty " + getName() + " registered.");
    }

    @Override
    public void login() {
        System.out.println("Faculty " + getName() + " logged in.");
    }

    @Override
    public List<String> getPermissions() {
        return List.of("teach", "assign_grades", "view_students");
    }

    @Override
    public double calculateFeesOrSalary() {
        return salary;
    }

    // Polymorphism
    @Override
    public String getRole() {
        return "Faculty";
    }

    @Override
    public double calculateWorkload() {
        return taughtCourses.size() * 10 + 5; // Courses + research
    }

    @Override
    public void accessLibrary() {
        System.out.println("Borrow limit: 20 books.");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Faculty Dashboard: Courses=" + taughtCourses.size() + ", Salary=" + salary);
    }
}