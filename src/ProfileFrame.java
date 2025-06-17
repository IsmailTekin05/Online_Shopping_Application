import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ProfileFrame extends JFrame {
    private Connection connection;
    private int customerID;

    private JTextField customerNameField, phoneNumberField, emailField, heightField, ageField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> referrerComboBox;
    
    private JTable addressTable;
    private DefaultTableModel addressTableModel;

    private JTable paymentTable;
    private DefaultTableModel paymentTableModel;

    public ProfileFrame(int customerID) {
        this.customerID = customerID;
        connectToDatabase();
        initializeComponents(); 
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed!\n" + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeComponents() {
        setTitle("My Account");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("👤 Profile Information", createProfileInfoPanel());
        tabbedPane.addTab("🏠 My Addresses", createAddressPanel());
        tabbedPane.addTab("💳 Payment Information", createPaymentPanel());
        
        add(tabbedPane);
        loadReferrers();
        loadCustomerData();
        loadAddressData();
        loadPaymentData();
    }
    
    private JPanel createProfileInfoPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; mainPanel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1; customerNameField = new JTextField(25); mainPanel.add(customerNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1; phoneNumberField = new JTextField(); mainPanel.add(phoneNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; emailField = new JTextField(); mainPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("Height (cm):"), gbc);
        gbc.gridx = 1; heightField = new JTextField(); mainPanel.add(heightField, gbc);
        
        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1; ageField = new JTextField(); mainPanel.add(ageField, gbc);
        
        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("Referrer:"), gbc);
        gbc.gridx = 1; referrerComboBox = new JComboBox<>(); mainPanel.add(referrerComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1; passwordField = new JPasswordField(); mainPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; mainPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1; confirmPasswordField = new JPasswordField(); mainPanel.add(confirmPasswordField, gbc);
        
        JButton updateButton = new JButton("Update Profile");
        JButton changePasswordButton = new JButton("Change Password");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.add(updateButton);
        buttonPanel.add(changePasswordButton);

        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        updateButton.addActionListener(e -> updateProfile());
        changePasswordButton.addActionListener(e -> changePassword());

        return mainPanel;
    }

    private JPanel createAddressPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        addressTableModel = new DefaultTableModel(new String[]{"Address Name", "City", "District", "Street"}, 0);
        addressTable = new JTable(addressTableModel);
        mainPanel.add(new JScrollPane(addressTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton manageAddressesButton = new JButton("Manage Addresses");
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);
        buttonPanel.add(manageAddressesButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        manageAddressesButton.addActionListener(e -> new AddressFrame(this.customerID).setVisible(true));
        refreshButton.addActionListener(e -> loadAddressData());

        return mainPanel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        paymentTableModel = new DefaultTableModel(new String[]{"Card Nickname", "Card Owner", "Card Number"}, 0);
        paymentTable = new JTable(paymentTableModel);
        mainPanel.add(new JScrollPane(paymentTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton managePaymentsButton = new JButton("Manage Payment Methods");
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);
        buttonPanel.add(managePaymentsButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        managePaymentsButton.addActionListener(e -> new PaymentFrame(this.customerID).setVisible(true));
        refreshButton.addActionListener(e -> loadPaymentData());

        return mainPanel;
    }

    private void loadCustomerData() {
        String query = "SELECT customer_name, phone_number, email, height, age, referrerID FROM customer WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customerNameField.setText(rs.getString("customer_name"));
                phoneNumberField.setText(rs.getString("phone_number"));
                emailField.setText(rs.getString("email"));
                heightField.setText(String.valueOf(rs.getInt("height")));
                ageField.setText(String.valueOf(rs.getInt("age")));
                
                int referrerID = rs.getInt("referrerID");
                if (!rs.wasNull()) {
                    for (int i = 0; i < referrerComboBox.getItemCount(); i++) {
                        String item = referrerComboBox.getItemAt(i);
                        if (item != null && item.startsWith(referrerID + " -")) {
                            referrerComboBox.setSelectedIndex(i);
                            break;
                        }
                    }
                } else {
                    referrerComboBox.setSelectedItem("None");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading customer data: " + e.getMessage());
        }
    }

    private void loadAddressData() {
        if(addressTableModel == null) return;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT address_name, city, district, street FROM address WHERE customerID = ?")) {
            addressTableModel.setRowCount(0);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                addressTableModel.addRow(new Object[]{
                    rs.getString("address_name"), rs.getString("city"),
                    rs.getString("district"), rs.getString("street")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading addresses: " + e.getMessage());
        }
    }

    private void loadPaymentData() {
        if(paymentTableModel == null) return;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT cardName, card_owner, cardNumber FROM payment_info WHERE customerID = ?")) {
            paymentTableModel.setRowCount(0);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String cardNumber = rs.getString("cardNumber");
                String maskedCardNumber = "**** **** **** " + (cardNumber.length() > 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber);
                paymentTableModel.addRow(new Object[]{
                    rs.getString("cardName"), rs.getString("card_owner"), maskedCardNumber
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading payment info: " + e.getMessage());
        }
    }
    
    private void loadReferrers() {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT customerID, customer_name FROM customer WHERE customerID != ?")) {
            referrerComboBox.removeAllItems();
            referrerComboBox.addItem("None");
            stmt.setInt(1, this.customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                referrerComboBox.addItem(rs.getInt("customerID") + " - " + rs.getString("customer_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading referrers: " + e.getMessage());
        }
    }

    private void updateProfile() {
        String query = "UPDATE customer SET customer_name = ?, phone_number = ?, email = ?, height = ?, age = ?, referrerID = ? WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerNameField.getText());
            stmt.setString(2, phoneNumberField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setInt(4, Integer.parseInt(heightField.getText()));
            stmt.setInt(5, Integer.parseInt(ageField.getText()));
            
            String selectedReferrer = (String) referrerComboBox.getSelectedItem();
            if (selectedReferrer != null && !selectedReferrer.equals("None")) {
                stmt.setInt(6, Integer.parseInt(selectedReferrer.split(" - ")[0]));
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.setInt(7, this.customerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating profile: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Height and Age must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changePassword() {
        String newPassword = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match or are empty.", "Password Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE customer SET password = ? WHERE customerID = ?")) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, this.customerID);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Password changed successfully!");
            passwordField.setText("");
            confirmPasswordField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error changing password: " + e.getMessage());
        }
    }
}