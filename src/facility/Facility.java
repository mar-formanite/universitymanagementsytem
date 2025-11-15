package facility;

public abstract class Facility {
    private String id;
    private String location;

    public Facility(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getId() { return id; }
    public String getLocation() { return location; }

    public abstract void reserve(); // Abstraction
}