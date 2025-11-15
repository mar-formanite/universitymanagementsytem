package researchproject;

import people.Professor;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject {
    private String title;
    private List<Professor> collaborators; // Association: Many-to-Many with Professors

    public ResearchProject(String title) {
        this.title = title;
        this.collaborators = new ArrayList<>();
    }

    public void addCollaborator(Professor professor) {
        collaborators.add(professor);
    }

    public List<Professor> getCollaborators() {
        return collaborators;
    }
}

