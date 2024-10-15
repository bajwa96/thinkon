package com.thinkon.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.thinkon.model.User;
import com.thinkon.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public User createUser(User user) {
		
		String sql = "INSERT INTO users (username, firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = jdbcTemplate.getDataSource().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getPhoneNumber());
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error creating user", e);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try (Connection conn = jdbcTemplate.getDataSource().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNumber(rs.getString("phoneNumber"));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error retrieving all users", e);
		}
		return users;
	}

	@Override
	public User getUserById(Long id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = jdbcTemplate.getDataSource().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getLong("id"));
					user.setUsername(rs.getString("username"));
					user.setFirstName(rs.getString("firstName"));
					user.setLastName(rs.getString("lastName"));
					user.setEmail(rs.getString("email"));
					user.setPhoneNumber(rs.getString("phoneNumber"));
					return user;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error retrieving user by ID", e);
		}
		return null;
	}

	@Override
	public User updateUser(Long id, User userDetails) {
		String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ?, email = ?, phoneNumber = ? WHERE id = ?";
		try (Connection conn = jdbcTemplate.getDataSource().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userDetails.getUsername());
			pstmt.setString(2, userDetails.getFirstName());
			pstmt.setString(3, userDetails.getLastName());
			pstmt.setString(4, userDetails.getEmail());
			pstmt.setString(5, userDetails.getPhoneNumber());
			pstmt.setLong(6, id);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				userDetails.setId(id);
				return userDetails;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error updating user", e);
		}
		return null;
	}

	@Override
	public boolean deleteUser(Long id) {
		String sql = "DELETE FROM users WHERE id = ?";
		try (Connection conn = jdbcTemplate.getDataSource().getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			throw new RuntimeException("Error deleting user", e);
		}
	}
    
}
