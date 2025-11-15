package courses;

import java.util.Map;
import java.util.HashMap;

public class Transcript {
    private Map<String, String> grades; // Course code to grade

    public Transcript() {
        this.grades = new HashMap<>();
    }

    public void addGrade(String courseCode, String grade) {
        grades.put(courseCode, grade);
    }

    public Map<String, String> getGrades() {
        return grades;
    }
}