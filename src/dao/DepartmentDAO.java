package dao;

import departments.department;  // <-- Correct import for your lowercase 'd' class
import departments.Budget;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    // CREATE - Add new department
    public void create(department department) {
        String sql = "INSERT INTO Department (name, budget_amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department.getName());
            pstmt.setDouble(2, department.getBudget().getAllocatedAmount());
            pstmt.executeUpdate();
            System.out.println("Department created: " + department.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - Get department by name
    public department read(String name) {
        String sql = "SELECT * FROM Department WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double budgetAmount = rs.getDouble("budget_amount");
                return new department(name, budgetAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE budget
    public void updateBudget(String name, double newBudget) {
        String sql = "UPDATE Department SET budget_amount = ? WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBudget);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Budget updated for department: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE department
    public void delete(String name) {
        String sql = "DELETE FROM Department WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Department deleted: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SEARCH departments by partial name
    public List<department> searchByName(String namePart) {
        List<department> departments = new ArrayList<>();
        String sql = "SELECT name FROM Department WHERE name LIKE ? ORDER BY name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                department dept = read(rs.getString("name"));
                if (dept != null) {
                    departments.add(dept);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
}