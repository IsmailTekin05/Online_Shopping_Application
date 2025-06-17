/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 90551
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PaymentFrame extends JFrame {
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private JTextField cardNumberField, cvcField, cardOwnerField, cardNameField;
    private JTextField expMonthField, expYearField;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private Connection connection;
    private int customerID;
    
    public PaymentFrame(int customerID) {
        this.customerID = customerID;
        initializeComponents();
        connectToDatabase();
        loadPaymentData();
    }
    
    private void initializeComponents() {
        setTitle("Payment Information");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
    
        String[] columnNames = {"Card Number", "Expiration Date", "CVC", "Card Owner", "Card Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        paymentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        
        
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Payment Information"));
        
        inputPanel.add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        inputPanel.add(cardNumberField);
        
        inputPanel.add(new JLabel("Expiration Month:"));
        expMonthField = new JTextField();
        inputPanel.add(expMonthField);
        
        inputPanel.add(new JLabel("Expiration Year:"));
        expYearField = new JTextField();
        inputPanel.add(expYearField);
        
        inputPanel.add(new JLabel("CVC:"));
        cvcField = new JTextField();
        inputPanel.add(cvcField);
        
        inputPanel.add(new JLabel("Card Owner:"));
        cardOwnerField = new JTextField();
        inputPanel.add(cardOwnerField);
        
        inputPanel.add(new JLabel("Card Name:"));
        cardNameField = new JTextField();
        inputPanel.add(cardNameField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Card");
        updateButton = new JButton("Update Card");
        deleteButton = new JButton("Delete Card");
        refreshButton = new JButton("Refresh");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        inputPanel.add(buttonPanel);
        
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
        addButton.addActionListener(e -> addPaymentInfo());
        updateButton.addActionListener(e -> updatePaymentInfo());
        deleteButton.addActionListener(e -> deletePaymentInfo());
        refreshButton.addActionListener(e -> loadPaymentData());
        
        paymentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = paymentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String cardNumber = tableModel.getValueAt(selectedRow, 0).toString();
                    String expDate = tableModel.getValueAt(selectedRow, 1).toString();
                    String[] dateParts = expDate.split("/");
                    
                    cardNumberField.setText(cardNumber);
                    if (dateParts.length == 2) {
                        expMonthField.setText(dateParts[0]);
                        expYearField.setText(dateParts[1]);
                    }
                    cvcField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    cardOwnerField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    cardNameField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                }
            }
        });
    }
    
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }
    
    private void loadPaymentData() {
        try {
            tableModel.setRowCount(0);
            String query = "SELECT cardNumber, expirationDate, cvc, card_owner, cardName FROM payment_info WHERE customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String cardNumber = rs.getString("cardNumber");
                String maskedCardNumber = maskCardNumber(cardNumber);
                
                Object[] row = {
                    maskedCardNumber,
                    rs.getString("expirationDate"),
                    "***", 
                    rs.getString("card_owner"),
                    rs.getString("cardName")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading payment data: " + e.getMessage());
        }
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() <= 4) return cardNumber;
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
    
    private void addPaymentInfo() {
        if (!validateInputs()) return;
        
        try {
            String expirationDate = expMonthField.getText() + "/" + expYearField.getText();
            String query = "INSERT INTO payment_info (cardNumber, expirationDate, cvc, card_owner, cardName, customerID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, cardNumberField.getText());
            stmt.setString(2, expirationDate);
            stmt.setString(3, cvcField.getText());
            stmt.setString(4, cardOwnerField.getText());
            stmt.setString(5, cardNameField.getText());
            stmt.setInt(6, customerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment information added successfully!");
            loadPaymentData();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding payment info: " + e.getMessage());
        }
    }
    
    private void updatePaymentInfo() {
        int selectedRow = paymentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a payment method to update");
            return;
        }
        
        if (!validateInputs()) return;
        
        try {
            String expirationDate = expMonthField.getText() + "/" + expYearField.getText();
            String query = "UPDATE payment_info SET expirationDate = ?, cvc = ?, card_owner = ?, cardName = ? WHERE cardNumber = ? AND customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, expirationDate);
            stmt.setString(2, cvcField.getText());
            stmt.setString(3, cardOwnerField.getText());
            stmt.setString(4, cardNameField.getText());
            stmt.setString(5, cardNumberField.getText());
            stmt.setInt(6, customerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment information updated successfully!");
            loadPaymentData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating payment info: " + e.getMessage());
        }
    }
    
    private void deletePaymentInfo() {
        int selectedRow = paymentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a payment method to delete");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this payment method?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String query = "DELETE FROM payment_info WHERE cardNumber = ? AND customerID = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, cardNumberField.getText());
                stmt.setInt(2, customerID);
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Payment information deleted successfully!");
                loadPaymentData();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting payment info: " + e.getMessage());
            }
        }
    }
    
    private boolean validateInputs() {
        if (cardNumberField.getText().trim().isEmpty() || 
            expMonthField.getText().trim().isEmpty() || 
            expYearField.getText().trim().isEmpty() ||
            cvcField.getText().trim().isEmpty() || 
            cardOwnerField.getText().trim().isEmpty() || 
            cardNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return false;
        }
        
        try {
            int month = Integer.parseInt(expMonthField.getText());
            int year = Integer.parseInt(expYearField.getText());
            if (month < 1 || month > 12) {
                JOptionPane.showMessageDialog(this, "Month must be between 1 and 12");
                return false;
            }
            if (year < 2024) {
                JOptionPane.showMessageDialog(this, "Year cannot be in the past");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Month and year must be numbers");
            return false;
        }
        
        return true;
    }
    
    private void clearFields() {
        cardNumberField.setText("");
        expMonthField.setText("");
        expYearField.setText("");
        cvcField.setText("");
        cardOwnerField.setText("");
        cardNameField.setText("");
    }
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        int exampleCustomerID = 1; 
        PaymentFrame frame = new PaymentFrame(exampleCustomerID);
        frame.setVisible(true);
    });
}

}
