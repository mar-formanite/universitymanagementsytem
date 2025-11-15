package courses;

import people.Faculty;  // Updated: Now imports Faculty instead of Professor
import people.Student;
import java.util.ArrayList;
import java.util.List;

public abstract class Course {
    private String courseCode;
    private String courseName;
    private int capacity;
    private Faculty instructor;  // Updated: Changed from Professor to Faculty
    private List<Student> enrolledStudents; // Aggregation: Students exist independently
    private Syllabus syllabus; // Composition: Owned by course

    public Course(String courseCode, String courseName, int capacity, Faculty instructor) {  // Updated: Parameter type changed to Faculty
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = capacity;
        this.instructor = instructor;
        this.enrolledStudents = new ArrayList<>();
        this.syllabus = new Syllabus(courseCode); // Composition: Created with course
    }

    // Encapsulation
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCapacity() { return capacity; }
    public Faculty getInstructor() { return instructor; }  // Updated: Return type changed to Faculty
    public void setInstructor(Faculty instructor) { this.instructor = instructor; }  // Updated: Parameter type changed to Faculty

    public void enrollStudent(Student student) {
        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
        }
    }

    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Abstract methods (Abstraction)
    public abstract double calculateFinalGrade(Student student);
    public abstract boolean checkPrerequisites(Student student);
    public abstract void generateSyllabus();
}
