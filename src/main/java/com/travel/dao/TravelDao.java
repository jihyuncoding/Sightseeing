package com.travel.dao;

import com.travel.vo.TravelVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {
    private static final String URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String USER = "root";
    private static final String PASSWORD = "qwer1234!";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public int getTotalPages(int pageSize) throws SQLException {
        String sql = "SELECT COUNT(*) FROM travel";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int totalCount = rs.getInt(1);
                    return (int) Math.ceil((double) totalCount / pageSize);
                }
            }
        }
        return 0;
    }

    public void insertTravel(TravelVO travel) throws SQLException {
        String sql = "INSERT INTO travel (district, title, description, address, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, travel.getDistrict());
            pstmt.setString(2, travel.getTitle());
            pstmt.setString(3, travel.getDescription());
            pstmt.setString(4, travel.getAddress());
            pstmt.setString(5, travel.getPhone());
            pstmt.executeUpdate();
        }
    }

    public List<TravelVO> getAllTravels(int page, int pageSize) throws SQLException {
        List<TravelVO> travels = new ArrayList<>();
        String sql = "SELECT * FROM travel LIMIT ? OFFSET ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    travels.add(mapResultSetToTravel(rs));
                }
            }
        }
        return travels;
    }

    public List<TravelVO> getTravelsByDistrict(String district) throws SQLException {
        List<TravelVO> travels = new ArrayList<>();
        String sql = "SELECT * FROM travel WHERE district = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, district);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    travels.add(mapResultSetToTravel(rs));
                }
            }
        }
        return travels;
    }

    public List<TravelVO> searchTravels(String keyword) throws SQLException {
        List<TravelVO> travels = new ArrayList<>();
        String sql = "SELECT * FROM travel WHERE title LIKE ? OR description LIKE ? OR address LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    travels.add(mapResultSetToTravel(rs));
                }
            }
        }
        return travels;
    }

    private TravelVO mapResultSetToTravel(ResultSet rs) throws SQLException {
        return new TravelVO(
            rs.getInt("no"),
            rs.getString("district"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("address"),
            rs.getString("phone")
        );
    }
}
