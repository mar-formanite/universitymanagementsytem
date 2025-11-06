package people;
import java.util.*;
public class Student extends Person {
    private Map< String, String> grades = new HashMap<>();
    private double gpa;

    public Student(String name, String id, String email, String phone_number){
        super(name,id,email,phone_number);
    }
    public void updateGrade(String courseCode , String grade){
        grades.put(courseCode,grade);
        calculateGPA();
    }
    private void calculateGPA(){
        double total =0;
        for (String grade : grades.values()){
            switch (grade){
                case "A" :total += 4; break;
                case "B" :total += 3; break;
                case "C" :total += 2; break;
                case "D" :total += 1; break;
                default: total += 0;
            }
        }
        gpa = grades.isEmpty() ? 0 : total / grades.size();
    }

    public double getGpa() {
        return gpa;
    }
    @Override
    public String getRole(){
        return "Student";
    }
    @Override
    public String toString(){
        return getName() + "(" + getId() + ") - GPA:" +gpa;
    }
}
