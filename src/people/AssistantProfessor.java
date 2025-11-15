package people;

import interfaces.Teachable;

public class AssistantProfessor extends Faculty implements Teachable {
    public AssistantProfessor(String name, String id, String email, String phone, double salary) {
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

    @Override
    public String getRole() {
        return "Assistant Professor";
    }
}