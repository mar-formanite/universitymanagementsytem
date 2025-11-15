package people;

import java.util.List;

public class TechnicalStaff extends Staff {
    public TechnicalStaff(String name, String id, String email, String phone, double salary, List<String> tasks) {
        super(name, id, email, phone, salary, tasks);
    }

    @Override
    public String getRole() {
        return "Technical Staff";
    }
}