package people;

import dao.DepartmentDAO;
import dao.PersonDAO;  // Added - essential for saving faculty to DB
import departments.department;
import courses.Course;

import java.util.List;
import java.util.ArrayList;

public class FacultyManager {

    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private PersonDAO personDAO = new PersonDAO();  // Required to save faculty in Person table

    // Properly typed in-memory list (optional but useful for quick access)
    private List<Faculty> facultyList = new ArrayList<>();

    public FacultyManager() {
        // Constructor
    }

    /**
     * Displays all faculty from the in-memory list.
     * For full persistence, use PersonDAO.getAll() and filter as in UniversitySystem.
     */
    public void displayAllFaculty() {
        System.out.println("=== All Faculty Members ===");
        if (facultyList.isEmpty()) {
            System.out.println("No faculty hired yet.");
        } else {
            for (Faculty f : facultyList) {
                System.out.println(f.getId() + " | " + f.getName() +
                        " | " + f.getRole() +
                        " | Salary: $" + f.getSalary());
            }
        }
    }

    /**
     * Hires a faculty member, saves them to the database, and assigns to department.
     * Automatically creates department if it doesn't exist.
     */
    public void hireFaculty(Faculty faculty, String deptName) {
        // CRITICAL: Save faculty to the Person table in database
        personDAO.create(faculty);

        // Handle department
        department dept = departmentDAO.read(deptName);

        if (dept == null) {
            System.out.println("Department '" + deptName + "' not found. Creating it with default budget 1,000,000.");
            dept = new department(deptName, 1000000.0);
            departmentDAO.create(dept);
        }

        // Add faculty to department's list
        dept.addFaculty(faculty);

        // Add to in-memory list for quick display
        facultyList.add(faculty);

        System.out.println(faculty.getName() + " has been successfully hired and assigned to the " + deptName + " department.");
    }

    /**
     * Assigns a course to a faculty member (bidirectional association)
     */
    public void assignCourse(Faculty faculty, Course course) {
        faculty.addCourse(course);
        course.setInstructor(faculty);
        System.out.println("Course " + course.getCourseName() + " assigned to " + faculty.getName());
    }

    /**
     * Returns the in-memory list of faculty
     */
    public List<Faculty> getFacultyList() {
        return facultyList;
    }
}