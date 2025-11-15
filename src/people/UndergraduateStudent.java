package people;

public class UndergraduateStudent extends Student {
    public UndergraduateStudent(String name, String id, String email, String phone) {
        super(name, id, email, phone);
    }

    @Override
    public String getRole() {
        return "Undergraduate Student";
    }
}