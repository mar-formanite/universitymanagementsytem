package people;

import courses.Course;
import courses.EnrollmentRecord;
import courses.Transcript;
import interfaces.Enrollable;
import interfaces.Payable;
import java.util.ArrayList;
import java.util.List;

public abstract class Student extends Person implements Enrollable, Payable {
    private double gpa; // Encapsulated
    private List<Course> enrolledCourses; // Aggregation: Courses exist independently
    private Transcript transcript; // Composition: Owned by student
    private EnrollmentRecord record; // Composition: Owned by student

    public Student(String name, String id, String email, String phone) {
        super(name, id, email, phone);
        this.enrolledCourses = new ArrayList<>();
        this.transcript = new Transcript(); // Composition: Created with student
        this.record = new EnrollmentRecord(); // Composition: Created with student
    }

    // Encapsulation: Controlled access to GPA
    public double getGPA() {
        return gpa;
    }

    public void updateGrade(String courseCode, String grade) {
        // Logic to update grade in transcript (controlled access)
        transcript.addGrade(courseCode, grade);
        calculateGPA(); // Recalculate after update
    }

    private void calculateGPA() {
        // Simple GPA calculation (expand as needed)
        // Assume grades: A=4, B=3, etc.
        double totalPoints = 0;
        int count = 0;
        for (String grade : transcript.getGrades().values()) {
            switch (grade) {
                case "A": totalPoints += 4; break;
                case "B": totalPoints += 3; break;
                case "C": totalPoints += 2; break;
                case "D": totalPoints += 1; break;
            }
            count++;
        }
        this.gpa = count > 0 ? totalPoints / count : 0;
    }

    // Enrollable interface
    @Override
    public void enrollInCourse(Course course) {
        if (enrolledCourses.size() < 5) { // Example limit
            enrolledCourses.add(course);
            course.enrollStudent(this); // Association: Many-to-many
        }
    }

    @Override
    public void dropCourse(Course course) {
        enrolledCourses.remove(course);
        course.dropStudent(this);
    }

    @Override
    public void viewSchedule() {
        System.out.println("Schedule for " + getName() + ": " + enrolledCourses);
    }

    // Payable interface
    @Override
    public void processPayment(double amount) {
        // Simulate payment processing
        System.out.println("Processed tuition payment of $" + amount);
    }

    @Override
    public void generateInvoice() {
        System.out.println("Generated invoice for tuition.");
    }

    @Override
    public void getFinancialSummary() {
        System.out.println("Outstanding tuition: $" + calculateFeesOrSalary());
    }

    // Abstract implementations
    @Override
    public void register() {
        System.out.println("Student " + getName() + " registered.");
    }

    @Override
    public void login() {
        System.out.println("Student " + getName() + " logged in.");
    }

    @Override
    public List<String> getPermissions() {
        return List.of("view_courses", "enroll", "view_grades");
    }

    @Override
    public double calculateFeesOrSalary() {
        return 5000.0; // Example tuition
    }

    // Polymorphism
    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public double calculateWorkload() {
        return enrolledCourses.size() * 3; // Based on credit hours
    }

    @Override
    public void accessLibrary() {
        System.out.println("Borrow limit: 5 books.");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Student Dashboard: GPA=" + gpa + ", Courses=" + enrolledCourses.size());
    }
}