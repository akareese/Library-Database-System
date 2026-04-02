package com.library.dao;

import com.library.model.Loan;
import com.library.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void issueLoan(Loan loan) {
        String sql = "INSERT INTO loans (book_id, member_id, issue_date, due_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, loan.getBookId());
            statement.setInt(2, loan.getMemberId());
            statement.setDate(3, Date.valueOf(loan.getIssueDate()));
            statement.setDate(4, Date.valueOf(loan.getDueDate()));
            statement.executeUpdate();

            System.out.println("Book issued successfully.");
        } catch (SQLException e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }

    public boolean returnLoan(int loanId, LocalDate returnDate) {
        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ? AND return_date IS NULL";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(returnDate));
            statement.setInt(2, loanId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error returning loan: " + e.getMessage());
            return false;
        }
    }

    public Loan getActiveLoanById(int loanId) {
        String sql = "SELECT * FROM loans WHERE loan_id = ? AND return_date IS NULL";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, loanId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error finding active loan: " + e.getMessage());
        }

        return null;
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans ORDER BY issue_date DESC";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                loans.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error loading loans: " + e.getMessage());
        }

        return loans;
    }

    public List<Loan> getOverdueLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE due_date < CURDATE() AND return_date IS NULL ORDER BY due_date";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                loans.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error loading overdue loans: " + e.getMessage());
        }

        return loans;
    }

    private Loan mapRow(ResultSet resultSet) throws SQLException {
        Date returnDateValue = resultSet.getDate("return_date");

        return new Loan(
                resultSet.getInt("loan_id"),
                resultSet.getInt("book_id"),
                resultSet.getInt("member_id"),
                resultSet.getDate("issue_date").toLocalDate(),
                resultSet.getDate("due_date").toLocalDate(),
                returnDateValue == null ? null : returnDateValue.toLocalDate()
        );
    }
}