/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author 90551
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Random;

public class GiftBoxGame extends JFrame {
    private Connection connection;
    private int customerID;

    public GiftBoxGame(int customerID) {
        this.customerID = customerID;
        connectToDatabase();
        initializeComponents();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void initializeComponents() {
        setTitle("🎁 Gift Box Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton openButton = new JButton("Open the Gift Box!");
        openButton.setFont(new Font("Arial", Font.BOLD, 18));
        openButton.addActionListener(this::handleOpen);

        add(openButton, BorderLayout.CENTER);
    }

private void handleOpen(ActionEvent e) {
    
    try {
        
    String query = "SELECT can_play_today(?, ?) AS canPlay";
    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setInt(1, customerID);
    stmt.setInt(2,3);
    ResultSet rs = stmt.executeQuery();

    if (rs.next() && !rs.getBoolean("canPlay")) {
        JOptionPane.showMessageDialog(this, "You can only play this game once per day.");
        return;
    }
     

    
    int chance = new Random().nextInt(100);
            int discount;
            if (chance < 10) discount = 30; 
            else if (chance < 40) discount = 15; 
            else discount = 5; 

            String couponCode = "GIFT-" + System.currentTimeMillis();

            String insertCoupon = "INSERT INTO coupon (couponCode, discountAmount, expirationDate, gameID, sellerID) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 7 DAY), ?, ?)";
PreparedStatement insertStmt = connection.prepareStatement(insertCoupon);
insertStmt.setString(1, couponCode);
insertStmt.setInt(2, discount);
insertStmt.setInt(3, 3); // gameID = 2 for Dice Roll
insertStmt.setNull(4, Types.INTEGER); 
insertStmt.executeUpdate();

String insertPlay = "INSERT IGNORE INTO plays (customerID, gameID) VALUES (?, ?)";
        PreparedStatement playStmt = connection.prepareStatement(insertPlay);
        playStmt.setInt(1, customerID);
        playStmt.setInt(2, 3); 
        playStmt.executeUpdate();



            JOptionPane.showMessageDialog(this, "🎁 You opened a gift box and won a %" + discount + " discount coupon! Code: " + couponCode);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}