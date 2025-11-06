package people;
import java.util.*;
public class StudentManager {
    private List<Student> students = new ArrayList<>();

    public void registerStudent(String name, String id, String email, String phone_number){
        Student newStudent = new Student(name, id, email, phone_number);
        students.add(newStudent);
        System.out.println("Registered new Student:" + name);
    }
    public Student findStudentbyId(String id){
        for (Student s : students){
            if (s.getId().equals(id))
                return s;
        }
        return null;
    }
    public void displayAllStudents(){
        for (Student s : students) System.out.println(s);
    }
}
