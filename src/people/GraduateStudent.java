package people;

import people.Professor;

public class GraduateStudent extends Student {
    private Professor advisor; // Aggregation: Advisor exists independently

    public GraduateStudent(String name, String id, String email, String phone, Professor advisor) {
        super(name, id, email, phone);
        this.advisor = advisor;
    }

    public Professor getAdvisor() {
        return advisor;
    }

    @Override
    public String getRole() {
        return "Graduate Student";
    }
}
