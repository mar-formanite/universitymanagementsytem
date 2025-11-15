package courses;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class EnrollmentRecord {
    private List<String> enrolledCourses; // List of course codes
    private Date enrollmentDate;

    public EnrollmentRecord() {
        this.enrolledCourses = new ArrayList<>();
        this.enrollmentDate = new Date();
    }

    public void addCourse(String courseCode) {
        enrolledCourses.add(courseCode);
    }

    public void removeCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }
}