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
import java.sql.*;

class AddressSelectionDialog extends JDialog {
    private JList<String> addressList;
    private String selectedAddress = null;

    public AddressSelectionDialog(Frame owner, Connection connection, int customerID) {
        super(owner, "Select Address", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        addressList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(addressList);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            selectedAddress = addressList.getSelectedValue();
            dispose();
        });

        add(scrollPane, BorderLayout.CENTER);
        add(selectButton, BorderLayout.SOUTH);

        try {
            String query = "SELECT address_name FROM address WHERE customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listModel.addElement(rs.getString("address_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading addresses: " + e.getMessage());
        }

        setVisible(true);
    }

    public String getSelectedAddress() {
        return selectedAddress;
    }
}
