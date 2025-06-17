package sql;

import java.sql.*;

public class AuthService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    public boolean isValidCustomer(String email, String password) {
        String sql = "SELECT is_valid_customer_login(?, ?) AS result";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("result");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidSeller(String sellerName, int sellerID) {
        String sql = "SELECT is_valid_seller_login(?, ?) AS result";

        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, sellerName);
            stmt.setInt(2, sellerID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("result");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int insertCustomer(String name, String phone, String email, String password, 
                          String heightStr, String ageStr, String referrerIdStr) {
    int height = -1;
    int age = -1;
    int referrerId = -1;

    try {
        height = Integer.parseInt(heightStr);
    } catch (NumberFormatException e) {
        height = -1;
    }

    try {
        age = Integer.parseInt(ageStr);
    } catch (NumberFormatException e) {
        age = -1;
    }

    String sql = "{CALL insert_customer(?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        CallableStatement stmt = conn.prepareCall(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, phone);
        stmt.setString(3, email);
        stmt.setString(4, password);

        stmt.setInt(5, height);
        stmt.setInt(6, age);

        // Handle referrerId properly (nullable)
        if (referrerIdStr == null || referrerIdStr.isEmpty() || referrerIdStr.equalsIgnoreCase("Referrer ID")) {
            stmt.setNull(7, Types.INTEGER);
        } else {
        try {
                referrerId = Integer.parseInt(referrerIdStr);
                stmt.setInt(7, referrerId);
        } catch (NumberFormatException e) {
                stmt.setNull(7, Types.INTEGER);
            }
        }

        stmt.registerOutParameter(8, Types.INTEGER);

        stmt.execute();

        return stmt.getInt(8);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // error
}


    public int insertSeller(String sellerName, String experienceStr, String ratingStr, String shipmentIdStr) {
        int experience = -1;
        double rating = 0.0;
        int shipmentId = -1;

        try {
            experience = Integer.parseInt(experienceStr);
        } catch (NumberFormatException e) {
            experience = 0;
        }

        try {
            rating = Double.parseDouble(ratingStr);
        } catch (NumberFormatException e) {
            rating = 0.0;
        }

        try {
            shipmentId = Integer.parseInt(shipmentIdStr);
        } catch (NumberFormatException e) {
            shipmentId = 1;
        }

        String sql = "{CALL insert_seller(?, ?, ?, ?, ?)}";
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        CallableStatement stmt = conn.prepareCall(sql)) {

        stmt.setString(1, sellerName);         // IN: p_name
        stmt.setInt(2, experience);            // IN: p_experience
        stmt.setDouble(3, rating);             // IN: p_rating
        stmt.setInt(4, shipmentId);            // IN: p_shipment_id
        stmt.registerOutParameter(5, Types.INTEGER);  // OUT: p_seller_id

        stmt.execute();

        return stmt.getInt(5);  // return OUT value

    } catch (SQLException e) {
        e.printStackTrace();
    }
        return -1; // error
    }

}
