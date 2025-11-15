package interfaces;

import courses.Course;

public interface Enrollable {
    void enrollInCourse(Course course);
    void dropCourse(Course course);
    void viewSchedule();
}
