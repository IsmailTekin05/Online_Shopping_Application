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

public class DiceRollGame extends JFrame {
    private Connection connection;
    private int customerID;

    public DiceRollGame(int customerID) {
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
        setTitle("🎲 Roll the Dice Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton rollButton = new JButton("Roll the Dice!");
        rollButton.setFont(new Font("Arial", Font.BOLD, 18));
        rollButton.addActionListener(this::handleRoll);

        add(rollButton, BorderLayout.CENTER);
    }

    
private void handleRoll(ActionEvent e) {
    
   
     try {
    String query = "SELECT can_play_today(?, ?) AS canPlay";
    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setInt(1, customerID);
    stmt.setInt(2,2);
    ResultSet rs = stmt.executeQuery();

    if (rs.next() && !rs.getBoolean("canPlay")) {
        JOptionPane.showMessageDialog(this, "You can only play this game once per day.");
        return;
    }

    
    int roll = new Random().nextInt(6) + 1; // 1 to 6
    int discount = (roll % 2 == 0) ? 15 : 5; 
    
    String couponCode = "DICE-" + System.currentTimeMillis();
    String insertCoupon = "INSERT INTO coupon (couponCode, discountAmount, expirationDate, gameID, sellerID) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 7 DAY), ?, ?)";
PreparedStatement insertStmt = connection.prepareStatement(insertCoupon);
insertStmt.setString(1, couponCode);
insertStmt.setInt(2, discount);
insertStmt.setInt(3, 2); // gameID = 2 for Dice Roll
insertStmt.setNull(4, Types.INTEGER); 
insertStmt.executeUpdate();



String insertPlay = "INSERT IGNORE INTO plays (customerID, gameID) VALUES (?, ?)";
PreparedStatement playStmt = connection.prepareStatement(insertPlay);
playStmt.setInt(1, customerID);
playStmt.setInt(2, 2); // gameID = 2 → Dice Roll
playStmt.executeUpdate();


    JOptionPane.showMessageDialog(this, "You rolled a " + roll + "! (Coupon feature is currently disabled)");
    dispose(); }
     catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
}
}}