package facility;

public class Lab extends Facility {
    private String equipment;

    public Lab(String id, String location, String equipment) {
        super(id, location);
        this.equipment = equipment;
    }

    @Override
    public void reserve() {
        System.out.println("Lab " + getId() + " reserved.");
    }
}
