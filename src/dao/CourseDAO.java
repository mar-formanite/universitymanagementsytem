package dao;

import courses.Course;
import people.Faculty;
import people.Student;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private PersonDAO personDAO = new PersonDAO();

    public void create(Course course) {
        String sql = "INSERT INTO Course (course_code, course_name, capacity, instructor_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCapacity());
            pstmt.setString(4, course.getInstructor() != null ? course.getInstructor().getId() : null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course read(String courseCode) {
        String sql = "SELECT * FROM Course WHERE course_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("course_name");
                int capacity = rs.getInt("capacity");
                String instrId = rs.getString("instructor_id");
                Faculty instructor = instrId != null ? (Faculty) personDAO.read(instrId) : null;

                return new Course(courseCode, name, capacity, instructor) {
                    @Override public double calculateFinalGrade(Student student) { return 0; }
                    @Override public boolean checkPrerequisites(Student student) { return true; }
                    @Override public void generateSyllabus() { }
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void enrollStudent(String studentId, String courseCode) {
        String sql = "INSERT INTO Student_Course (student_id, course_code) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(String courseCode) {
        String sql = "DELETE FROM Course WHERE course_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            pstmt.executeUpdate();
            System.out.println("Deleted course: " + courseCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropStudent(String studentId, String courseCode) {
        String sql = "DELETE FROM Student_Course WHERE student_id = ? AND course_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getEnrolledStudents(String courseCode) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT p.id FROM Student_Course sc JOIN Person p ON sc.student_id = p.id WHERE sc.course_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student s = (Student) personDAO.read(rs.getString("id"));
                if (s != null) students.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Course> searchByName(String namePart) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT course_code FROM Course WHERE course_name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Course c = read(rs.getString("course_code"));
                if (c != null) list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}