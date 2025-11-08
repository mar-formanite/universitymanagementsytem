package people;

import java.util.ArrayList;
import java.util.List;

public class Faculty extends Person{
    private double salary;
    private List<String> assignedCourses = new ArrayList<>();

    public Faculty(String name, String id, String email, String phone,double salary) {
        super(name, id, email, phone);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
    protected void setSalary(double salary) {this.salary = salary;}
    public  void assignCourse(String course){
        assignedCourses.add(courseCode);
    }
    @Override
    public String getRole(){
        return "Faculty";
    }
    @Override
    public String toString(){
        return getName() + " - "+ getRole() + "| Courses:" + assignedCourses;
    }
}
