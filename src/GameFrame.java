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

public class GameFrame extends JFrame {
    private Connection connection;
    private int customerID;

    public GameFrame(int customerID) {
        this.customerID = customerID;
        connectToDatabase();
        loadCoupons();
        initializeComponents();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }
    private void loadCoupons() {
    try {
        String query = """
            SELECT DISTINCT c.couponCode, c.discountAmount, c.expirationDate
            FROM coupon c
            JOIN game g ON c.gameID = g.gameID
            JOIN plays p ON p.gameID = g.gameID
            WHERE p.customerID = ?
            """;

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, customerID);
        ResultSet rs = stmt.executeQuery();

        StringBuilder sb = new StringBuilder("Your Coupons:\n");
        boolean hasCoupons = false;

        while (rs.next()) {
            hasCoupons = true;
            sb.append("- ").append(rs.getString("couponCode"))
              .append(" (%").append(rs.getDouble("discountAmount"))
              .append(" off, valid until ").append(rs.getDate("expirationDate")).append(")\n");
        }

        if (hasCoupons) {
            JOptionPane.showMessageDialog(this, sb.toString());
        } else {
            JOptionPane.showMessageDialog(this, "You don't have any coupons yet. Play a game to win!");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Could not load coupons: " + e.getMessage());
    }
}



    private void initializeComponents() {
        setTitle("Play a Game to Win Coupons!");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Choose a Game"));

JButton spinWheelButton = new JButton("🎡 Spin the Wheel");
JButton diceButton = new JButton("🎲 Roll the Dice");
JButton giftBoxButton = new JButton("🎁 Open the Gift Box");
JButton ticTacToeButton = new JButton("❌⭕ Play XOX");

spinWheelButton.addActionListener(e -> new SpinWheelGame(customerID).setVisible(true));
diceButton.addActionListener(e -> new DiceRollGame(customerID).setVisible(true));
giftBoxButton.addActionListener(e -> new GiftBoxGame(customerID).setVisible(true));
ticTacToeButton.addActionListener(e -> new TicTacToeGame(customerID).setVisible(true));


        mainPanel.add(spinWheelButton);
        mainPanel.add(diceButton);
        mainPanel.add(giftBoxButton);
        mainPanel.add(ticTacToeButton);

        add(mainPanel);
    }

  

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame(1); 
            frame.setVisible(true);
        });
    }
}
