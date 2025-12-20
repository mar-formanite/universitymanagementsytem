package dao;

import courses.Transcript;
import util.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;

public class TranscriptDAO {

    public void addGrade(String studentId, String courseCode, String grade) {
        String sql = "INSERT INTO Transcript (student_id, course_code, grade) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE grade = VALUES(grade)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseCode);
            pstmt.setString(3, grade);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double calculateGPA(String studentId) {
        // Simple GPA calculation: A=4, B=3, C=2, D=1, F=0
        String sql = "SELECT AVG(CASE grade " +
                "WHEN 'A' THEN 4.0 " +
                "WHEN 'B' THEN 3.0 " +
                "WHEN 'C' THEN 2.0 " +
                "WHEN 'D' THEN 1.0 " +
                "WHEN 'F' THEN 0.0 " +
                "ELSE 0.0 END) AS gpa FROM Transcript WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("gpa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Transcript getTranscript(String studentId) {
        Transcript transcript = new Transcript();
        String sql = "SELECT course_code, grade FROM Transcript WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                transcript.addGrade(rs.getString("course_code"), rs.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transcript;
    }
}