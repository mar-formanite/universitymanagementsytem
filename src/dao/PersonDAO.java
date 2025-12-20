package dao;

import people.*;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    // CREATE
    public void create(Person person) {
        String sql = "INSERT INTO Person (id, name, email, phone, role, gpa, salary, dissertation_topic, advisor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, person.getId());
            pstmt.setString(2, person.getName());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getPhone());
            pstmt.setString(5, person.getRole());

            if (person instanceof Student) {
                Student s = (Student) person;
                pstmt.setDouble(6, s.getGPA());
                pstmt.setDouble(7, 0.0);
                if (person instanceof PhDStudent) {
                    PhDStudent phd = (PhDStudent) person;
                    pstmt.setString(8, phd.getDissertationTopic());
                    pstmt.setString(9, phd.getAdvisor() != null ? phd.getAdvisor().getId() : null);
                } else if (person instanceof GraduateStudent) {
                    GraduateStudent grad = (GraduateStudent) person;
                    pstmt.setString(8, null);
                    pstmt.setString(9, grad.getAdvisor() != null ? grad.getAdvisor().getId() : null);
                } else {
                    pstmt.setString(8, null);
                    pstmt.setString(9, null);
                }
            } else {
                pstmt.setDouble(6, 0.0);
                double salary = 0.0;
                if (person instanceof Faculty) salary = ((Faculty) person).getSalary();
                else if (person instanceof Staff) salary = ((Staff) person).getSalary();
                pstmt.setDouble(7, salary);
                pstmt.setString(8, null);
                pstmt.setString(9, null);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - very important method
    public Person read(String id) {
        String sql = "SELECT * FROM Person WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                double salary = rs.getDouble("salary");

                switch (role) {
                    case "Undergraduate Student":
                        return new UndergraduateStudent(name, id, email, phone);
                    case "Graduate Student":
                        String advId = rs.getString("advisor_id");
                        Professor advisor = advId != null ? (Professor) read(advId) : null;
                        return new GraduateStudent(name, id, email, phone, advisor);
                    case "PhD Student":
                        advId = rs.getString("advisor_id");
                        advisor = advId != null ? (Professor) read(advId) : null;
                        String topic = rs.getString("dissertation_topic");
                        return new PhDStudent(name, id, email, phone, advisor, topic);
                    case "Professor":
                        return new Professor(name, id, email, phone, salary);
                    case "Assistant Professor":
                        return new AssistantProfessor(name, id, email, phone, salary);
                    case "Lecturer":
                        return new Lecturer(name, id, email, phone, salary);
                    case "Administrator":
                    case "Librarian":
                    case "Technical Staff":
                        return new Administrator(name, id, email, phone, salary, new ArrayList<>());
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public void update(Person person) {
        // You can implement similar to create but with UPDATE query
    }

    // DELETE
    public void delete(String id) {
        String sql = "DELETE FROM Person WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET ALL
    public List<Person> getAll() {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT id FROM Person";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Person p = read(rs.getString("id"));
                if (p != null) list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}