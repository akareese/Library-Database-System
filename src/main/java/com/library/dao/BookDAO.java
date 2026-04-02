package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, genre, isbn, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getTotalCopies());
            statement.setInt(6, book.getAvailableCopies());
            statement.executeUpdate();

            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                books.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        return books;
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error finding book: " + e.getMessage());
        }

        return null;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ? ORDER BY title";
        String searchTerm = "%" + keyword + "%";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, searchTerm);
            statement.setString(2, searchTerm);
            statement.setString(3, searchTerm);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error searching books: " + e.getMessage());
        }

        return books;
    }

    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, genre = ?, isbn = ?, total_copies = ?, available_copies = ? WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getTotalCopies());
            statement.setInt(6, book.getAvailableCopies());
            statement.setInt(7, book.getBookId());

            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "Book updated successfully." : "No book found with that ID.");
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted > 0 ? "Book deleted successfully." : "No book found with that ID.");
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    public boolean updateAvailableCopies(int bookId, int newAvailableCopies) {
        String sql = "UPDATE books SET available_copies = ? WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newAvailableCopies);
            statement.setInt(2, bookId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating available copies: " + e.getMessage());
            return false;
        }
    }

    private Book mapRow(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getInt("book_id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getString("genre"),
                resultSet.getString("isbn"),
                resultSet.getInt("total_copies"),
                resultSet.getInt("available_copies")
        );
    }
}