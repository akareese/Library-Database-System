package com.library.dao;

import com.library.model.Member;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public void addMember(Member member) {
        String sql = "INSERT INTO members (full_name, email, phone) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, member.getFullName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.executeUpdate();

            System.out.println("Member added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY full_name";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                members.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error loading members: " + e.getMessage());
        }

        return members;
    }

    public Member getMemberById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error finding member: " + e.getMessage());
        }

        return null;
    }

    private Member mapRow(ResultSet resultSet) throws SQLException {
        return new Member(
                resultSet.getInt("member_id"),
                resultSet.getString("full_name"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }
}