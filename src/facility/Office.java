package facility;

import people.Faculty;

public class Office extends Facility {
    private Faculty assignedFaculty; // Association: One-to-One with Faculty

    public Office(String id, String location, Faculty faculty) {
        super(id, location);
        this.assignedFaculty = faculty;
    }

    @Override
    public void reserve() {
        System.out.println("Office " + getId() + " reserved.");
    }

    public Faculty getAssignedFaculty() {
        return assignedFaculty;
    }
}