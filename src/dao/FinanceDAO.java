package dao;

import finance.FinanceAccount;
import finance.SalaryAccount;
import finance.ScholarshipAccount;
import finance.TuitionAccount;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinanceDAO {

    /**
     * Creates a new finance account (Tuition, Salary, or Scholarship)
     */
    public void create(FinanceAccount account, String ownerId) {
        String sql = "INSERT INTO FinanceAccount (account_id, balance, type, owner_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getAccountId());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setString(3, account.getClass().getSimpleName());
            pstmt.setString(4, ownerId);

            pstmt.executeUpdate();
            System.out.println("Finance account created successfully:");
            System.out.println("   • Account ID: " + account.getAccountId());
            System.out.println("   • Type: " + account.getClass().getSimpleName());
            System.out.println("   • Initial Balance: $" + account.getBalance());
            System.out.println("   • Owner ID: " + ownerId);
        } catch (SQLException e) {
            System.out.println("Error creating finance account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reads a single finance account by ID
     */
    public FinanceAccount read(String accountId) {
        String sql = "SELECT * FROM FinanceAccount WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");

                return switch (type) {
                    case "TuitionAccount" -> new TuitionAccount(accountId, balance);
                    case "SalaryAccount" -> new SalaryAccount(accountId, balance);
                    case "ScholarshipAccount" -> new ScholarshipAccount(accountId, balance);
                    default -> {
                        System.out.println("Unknown account type: " + type);
                        yield null;
                    }
                };
            } else {
                System.out.println("No account found with ID: " + accountId);
            }
        } catch (SQLException e) {
            System.out.println("Error reading account: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the balance of an existing account (used for payments/deposits)
     */
    public void updateBalance(String accountId, double newBalance) {
        String sql = "UPDATE FinanceAccount SET balance = ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Balance updated successfully:");
                System.out.println("   • Account ID: " + accountId);
                System.out.println("   • New Balance: $" + String.format("%.2f", newBalance));
            } else {
                System.out.println("No account found with ID: " + accountId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a finance account
     */
    public void delete(String accountId) {
        String sql = "DELETE FROM FinanceAccount WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountId);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Finance account deleted successfully: " + accountId);
            } else {
                System.out.println("No account found with ID: " + accountId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets all accounts belonging to a specific person (student/faculty)
     */
    public List<FinanceAccount> getAccountsByOwner(String ownerId) {
        List<FinanceAccount> accounts = new ArrayList<>();
        String sql = "SELECT account_id FROM FinanceAccount WHERE owner_id = ? ORDER BY type";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("=== Financial Accounts for Owner ID: " + ownerId + " ===");
            boolean found = false;

            while (rs.next()) {
                FinanceAccount acc = read(rs.getString("account_id"));
                if (acc != null) {
                    accounts.add(acc);
                    found = true;
                    System.out.println("• " + acc.getAccountId() +
                            " | " + acc.getClass().getSimpleName() +
                            " | Balance: $" + String.format("%.2f", acc.getBalance()));
                }
            }

            if (!found) {
                System.out.println("No financial accounts found for this person.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
            e.printStackTrace();
        }
        return accounts;
    }
}