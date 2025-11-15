package courses;

import java.util.List;
import java.util.ArrayList;

public class Syllabus {
    private String courseCode;
    private List<String> topics;

    public Syllabus(String courseCode) {
        this.courseCode = courseCode;
        this.topics = new ArrayList<>();
    }

    public void addTopic(String topic) {
        topics.add(topic);
    }

    public List<String> getTopics() {
        return topics;
    }

    public String getCourseCode() {
        return courseCode;
    }
}