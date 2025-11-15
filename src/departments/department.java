package departments;
import people.Faculty;
import courses.Course;
import java.util.ArrayList;
import java.util.List;

public class department {
    private String name;
    private List<Faculty> facultyMembers; // Aggregation: Faculty exist independently
    private List<Course> offeredCourses; // Aggregation: Courses exist independently
    private Budget budget; // Composition: Owned by department

    public department(String name, double budgetAmount) {
        this.name = name;
        this.facultyMembers = new ArrayList<>();
        this.offeredCourses = new ArrayList<>();
        this.budget = new Budget(budgetAmount); // Composition: Created with department
    }

    public void addFaculty(Faculty faculty) {
        facultyMembers.add(faculty);
    }

    public void addCourse(Course course) {
        offeredCourses.add(course);
    }

    public List<Faculty> getFacultyMembers() {
        return facultyMembers;
    }

    public List<Course> getOfferedCourses() {
        return offeredCourses;
    }

    public Budget getBudget() {
        return budget;
    }

    public String getName() {
        return this.name;
    }
}