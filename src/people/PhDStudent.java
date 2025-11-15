package people;

import interfaces.Researchable;

public class PhDStudent extends GraduateStudent implements Researchable {
    private String dissertationTopic;

    public PhDStudent(String name, String id, String email, String phone, Professor advisor, String dissertationTopic) {
        super(name, id, email, phone, advisor);
        this.dissertationTopic = dissertationTopic;
    }

    // Researchable interface
    @Override
    public void publishPaper(String paperTitle) {
        System.out.println("PhD Student published: " + paperTitle);
    }

    @Override
    public void conductResearch(String topic) {
        System.out.println("Conducting research on: " + topic);
    }

    @Override
    public void applyForGrant(double amount) {
        System.out.println("Applied for grant: $" + amount);
    }

    @Override
    public void accessLibrary() {
        System.out.println("Extended borrowing period for PhD students.");
    }

    @Override
    public String getRole() {
        return "PhD Student";
    }
}