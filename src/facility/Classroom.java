package facility;

public class Classroom extends Facility {
    private int capacity;

    public Classroom(String id, String location, int capacity) {
        super(id, location);
        this.capacity = capacity;
    }

    @Override
    public void reserve() {
        System.out.println("Classroom " + getId() + " reserved.");
    }
}