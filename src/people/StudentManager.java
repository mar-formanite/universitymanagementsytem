package people;

import dao.PersonDAO;               // Import the DAO
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManager {

    // We now use PersonDAO to interact with the database
    private PersonDAO personDAO = new PersonDAO();

    // Register / Create a new student → INSERT into DB
    public void registerStudent(Student student) {
        personDAO.create(student);
        System.out.println("Registered new student: " + student.getName());
    }

    // Find student by ID → SELECT from DB
    public Student findStudentById(String id) {
        Person person = personDAO.read(id);
        if (person instanceof Student) {
            return (Student) person;
        }
        return null;
    }

    // Delete student by ID → DELETE from DB (fixes your previous error)
    public void deleteStudent(String id) {
        personDAO.delete(id);
        System.out.println("Deleted student with ID: " + id);
    }

    // Update student (optional but useful for future menu options)
    public void updateStudent(Student student) {
        personDAO.update(student);   // You can implement update() in PersonDAO later if needed
        System.out.println("Updated student: " + student.getName());
    }

    // Display all students → SELECT all persons and filter students
    public void displayAllStudents() {
        System.out.println("=== All Students ===");

        List<Person> allPersons = personDAO.getAll();
        List<Student> students = allPersons.stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
        } else {
            for (Student s : students) {
                System.out.println(s.getId() + " | " + s.getName() + " | " + s.getRole());
            }
        }
    }
}