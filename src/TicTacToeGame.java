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

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerTurn = true;
    private Connection connection;
    private int customerID;

    public TicTacToeGame(int customerID) {
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
        setTitle("❌⭕ Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 32));
                final int row = i;
                final int col = j;
                button.addActionListener((ActionEvent e) -> handleClick(row, col));
                buttons[i][j] = button;
                add(button);
            }
        }
    }

    private void handleClick(int row, int col) {
        JButton btn = buttons[row][col];
        if (!btn.getText().equals("") || !playerTurn) return;

        btn.setText("X");
        playerTurn = false;

        if (checkWin("X")) {
            awardCoupon();
            return;
        }

        computerMove();
        if (checkWin("O")) {
            JOptionPane.showMessageDialog(this, "You lost! Try again tomorrow.");
    
            dispose();
        }
    }

    private void computerMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText("O");
                    playerTurn = true;
                    return;
                }
            }
        }
    }

    private boolean checkWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) return true;
            if (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol)) return true;
        }
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) return true;
        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol)) return true;
        return false;
    }

    private void awardCoupon() {
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
            String couponCode = "XOX-" + System.currentTimeMillis();
            int discount = 20;
            String insertCoupon = "INSERT INTO coupon (couponCode, discountAmount, expirationDate, gameID, sellerID) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 7 DAY), ?, ?)";
PreparedStatement insertStmt = connection.prepareStatement(insertCoupon);
insertStmt.setString(1, couponCode);
insertStmt.setInt(2, discount);
insertStmt.setInt(3, 4); 
insertStmt.setNull(4, Types.INTEGER); 
insertStmt.executeUpdate();

String insertPlay = "INSERT IGNORE INTO plays (customerID, gameID) VALUES (?, ?)";
        PreparedStatement playStmt = connection.prepareStatement(insertPlay);
        playStmt.setInt(1, customerID);
        playStmt.setInt(2, 4); // Tic Tac Toe oyunu
        playStmt.executeUpdate();
            

            JOptionPane.showMessageDialog(this, "You won the game! 🎉 Here's your 20% discount coupon: " + couponCode);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    
}
