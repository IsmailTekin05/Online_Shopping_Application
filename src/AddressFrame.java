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
import java.sql.*;

public class AddressFrame extends JFrame {
    private JTable addressTable;
    private DefaultTableModel tableModel;
    private JTextField addressNameField, cityField, districtField, streetField, buildingField;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private Connection connection;
    private int customerID;
    
    public AddressFrame(int customerID) {
        this.customerID = customerID;
        initializeComponents();
        connectToDatabase();
        loadAddressData();
    }
    
    private void initializeComponents() {
        setTitle("Address Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        String[] columnNames = {"Address Name", "City", "District", "Street", "Building Number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        addressTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(addressTable);
        
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Address Information"));
        
        inputPanel.add(new JLabel("Address Name:"));
        addressNameField = new JTextField();
        inputPanel.add(addressNameField);
        
        inputPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        inputPanel.add(cityField);
        
        inputPanel.add(new JLabel("District:"));
        districtField = new JTextField();
        inputPanel.add(districtField);
        
        inputPanel.add(new JLabel("Street:"));
        streetField = new JTextField();
        inputPanel.add(streetField);
        
        inputPanel.add(new JLabel("Building Number:"));
        buildingField = new JTextField();
        inputPanel.add(buildingField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        inputPanel.add(buttonPanel);
        
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
     
        addButton.addActionListener(e -> addAddress());
        updateButton.addActionListener(e -> updateAddress());
        deleteButton.addActionListener(e -> deleteAddress());
        refreshButton.addActionListener(e -> loadAddressData());
        
        addressTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = addressTable.getSelectedRow();
                if (selectedRow != -1) {
                    addressNameField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    cityField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    districtField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    streetField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    buildingField.setText(tableModel.getValueAt(selectedRow, 4).toString());
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
    
    private void loadAddressData() {
        try {
            tableModel.setRowCount(0);
            String query = "SELECT address_name, city, district, street, building_number FROM address WHERE customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("address_name"),
                    rs.getString("city"),
                    rs.getString("district"),
                    rs.getString("street"),
                    rs.getString("building_number")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void addAddress() {
        try {
            String query = "INSERT INTO address (address_name, customerID, city, district, street, building_number) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, addressNameField.getText());
            stmt.setInt(2, customerID);
            stmt.setString(3, cityField.getText());
            stmt.setString(4, districtField.getText());
            stmt.setString(5, streetField.getText());
            stmt.setString(6, buildingField.getText());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Address added successfully!");
            loadAddressData();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding address: " + e.getMessage());
        }
    }
    
    private void updateAddress() {
        int selectedRow = addressTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an address to update");
            return;
        }
        
        try {
            String query = "UPDATE address SET city = ?, district = ?, street = ?, building_number = ? WHERE address_name = ? AND customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, cityField.getText());
            stmt.setString(2, districtField.getText());
            stmt.setString(3, streetField.getText());
            stmt.setString(4, buildingField.getText());
            stmt.setString(5, addressNameField.getText());
            stmt.setInt(6, customerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Address updated successfully!");
            loadAddressData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating address: " + e.getMessage());
        }
    }
    
    private void deleteAddress() {
        int selectedRow = addressTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an address to delete");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this address?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String query = "DELETE FROM address WHERE address_name = ? AND customerID = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, addressNameField.getText());
                stmt.setInt(2, customerID);
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Address deleted successfully!");
                loadAddressData();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting address: " + e.getMessage());
            }
        }
    }
    
    private void clearFields() {
        addressNameField.setText("");
        cityField.setText("");
        districtField.setText("");
        streetField.setText("");
        buildingField.setText("");
    }
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        AddressFrame frame = new AddressFrame(1); 
        frame.setVisible(true);
    });
}

}

