package people;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    // Register a new student
    public void registerStudent(Student student) {
        students.add(student);
        System.out.println("Registered new student: " + student.getName());
    }

    // Find student by ID
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    // Display all students
    public void displayAllStudents() {
        System.out.println("=== All Students ===");
        for (Student s : students) {
            System.out.println(s.getName() + " | " + s.getRole());
        }
    }
}
