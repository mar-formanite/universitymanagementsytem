package people;

import java.util.List;

public abstract class Person {
    private String name;
    private String id;
    private String email;
    private String phone;

    public Person(String name, String id, String email, String phone) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    // Encapsulation: Getters for private fields
    public String getName() { return name; }
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Abstract methods (Abstraction)
    public abstract void register();
    public abstract void login();
    public abstract List<String> getPermissions();
    public abstract double calculateFeesOrSalary(); // Polymorphic: Fees for students, salary for others

    // Polymorphic methods
    public abstract String getRole();
    public abstract double calculateWorkload();
    public abstract void accessLibrary();
    public abstract void displayDashboard();
}
