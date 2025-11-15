package people;

import interfaces.Researchable;
import interfaces.Teachable;

public class Professor extends Faculty implements Teachable, Researchable {
    public Professor(String name, String id, String email, String phone, double salary) {
        super(name, id, email, phone, salary);
    }

    // Teachable interface
    @Override
    public void teach(courses.Course course) {
        System.out.println("Teaching " + course.getCourseCode());
        addCourse(course);
    }

    @Override
    public void assignGrades(courses.Course course) {
        System.out.println("Assigning grades for " + course.getCourseCode());
    }

    @Override
    public void holdOfficeHours() {
        System.out.println("Holding office hours.");
    }

    // Researchable interface
    @Override
    public void publishPaper(String paperTitle) {
        System.out.println("Published: " + paperTitle);
    }

    @Override
    public void conductResearch(String topic) {
        System.out.println("Researching: " + topic);
    }

    @Override
    public void applyForGrant(double amount) {
        System.out.println("Applied for grant: $" + amount);
    }

    @Override
    public String getRole() {
        return "Professor";
    }
}