package interfaces;

import courses.Course;

public interface Teachable {
    void teach(Course course);
    void assignGrades(Course course);
    void holdOfficeHours();
}
