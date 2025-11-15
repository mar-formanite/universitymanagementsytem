package people;

import java.util.List;

public class Administrator extends Staff {
    public Administrator(String name, String id, String email, String phone, double salary, List<String> tasks) {
        super(name, id, email, phone, salary, tasks);
    }

    @Override
    public String getRole() {
        return "Administrator";
    }
}
