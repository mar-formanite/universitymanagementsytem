package people;
abstract class Person {
    private String name;
    private String id;
    private String email;
    private String phone_number;

    //constructor
    public Person(String name, String id, String email, String phone_number){
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone_number = phone_number;
    }

    // Getter
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone_number() {
        return phone_number;
    }

    //setter
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public abstract String getRole();
}
