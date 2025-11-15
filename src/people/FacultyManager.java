package people;

import departments.department;
import java.util.List;
import java.util.ArrayList;

public class FacultyManager {
    private List<Faculty> facultyList;

    public FacultyManager() {
        this.facultyList = new ArrayList<>();
    }

    public void hireFaculty(Faculty faculty, department department) {
        facultyList.add(faculty);
        department.addFaculty(faculty); // Association: Department to Faculty
    }

    public void assignCourse(Faculty faculty, courses.Course course) {
        faculty.addCourse(course);
        course.setInstructor(faculty); // Association: Course to Professor
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }
}