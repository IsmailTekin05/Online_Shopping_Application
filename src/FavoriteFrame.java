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

public class FavoriteFrame extends JFrame {
    private JTable favoritesTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> productComboBox;
    private JButton addButton, removeButton, refreshButton, addToCartButton;
    private Connection connection;
    private int customerID;
    
    public FavoriteFrame(int customerID) {
        this.customerID = customerID;
        initializeComponents();
        connectToDatabase();
        loadFavoritesData();
        loadAvailableProducts();
    }

    FavoriteFrame() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    private void initializeComponents() {
        setTitle("My Favorites");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        String[] columnNames = {"Product Name", "Price", "Color", "Size", "Stock", "Seller"};
        tableModel = new DefaultTableModel(columnNames, 0);
        favoritesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(favoritesTable);
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Favorites Management"));
        
        inputPanel.add(new JLabel("Select Product to Add:"));
        productComboBox = new JComboBox<>();
        inputPanel.add(productComboBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add to Favorites");
        removeButton = new JButton("Remove from Favorites");
        refreshButton = new JButton("Refresh");
        addToCartButton = new JButton("Add Selected to Cart");
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(addToCartButton);
        
        inputPanel.add(buttonPanel);
        
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
       
        addButton.addActionListener(e -> addToFavorites());
        removeButton.addActionListener(e -> removeFromFavorites());
        refreshButton.addActionListener(e -> {
            loadFavoritesData();
            loadAvailableProducts();
        });
        addToCartButton.addActionListener(e -> addSelectedToCart());
    }
    
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }
    
    private void loadFavoritesData() {
        try {
            tableModel.setRowCount(0);
            String query = "SELECT p.productName, p.price, p.color, p.size, p.stock, s.sellerName " +
                          "FROM favorites f " +
                          "JOIN product p ON f.productID = p.productID " +
                          "JOIN seller s ON p.sellerID = s.sellerID " +
                          "WHERE f.customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("productName"),
                    "$" + rs.getDouble("price"),
                    rs.getString("color"),
                    rs.getString("size"),
                    rs.getInt("stock"),
                    rs.getString("sellerName")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading favorites: " + e.getMessage());
        }
    }
    
    private void loadAvailableProducts() {
        try {
            productComboBox.removeAllItems();
            String query = "SELECT p.productID, p.productName, p.price " +
                          "FROM product p " +
                          "WHERE p.productID NOT IN (SELECT productID FROM favorites WHERE customerID = ?) " +
                          "AND p.stock > 0";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String item = rs.getInt("productID") + " - " + rs.getString("productName") + " ($" + rs.getDouble("price") + ")";
                productComboBox.addItem(item);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }
    
    private void addToFavorites() {
        if (productComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a product to add to favorites");
            return;
        }
        
        try {
            String selectedProduct = productComboBox.getSelectedItem().toString();
            int productID = Integer.parseInt(selectedProduct.split(" - ")[0]);
            
            String query = "INSERT INTO favorites (productID, customerID) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, productID);
            stmt.setInt(2, customerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product added to favorites!");
            loadFavoritesData();
            loadAvailableProducts();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { 
                JOptionPane.showMessageDialog(this, "This product is already in your favorites!");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding to favorites: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid product selection");
        }
    }
    
    private void removeFromFavorites() {
        int selectedRow = favoritesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to remove from favorites");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Remove this product from favorites?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String productName = tableModel.getValueAt(selectedRow, 0).toString();
                
                String query = "DELETE f FROM favorites f " +
                              "JOIN product p ON f.productID = p.productID " +
                              "WHERE f.customerID = ? AND p.productName = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, customerID);
                stmt.setString(2, productName);
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Product removed from favorites!");
                loadFavoritesData();
                loadAvailableProducts();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error removing from favorites: " + e.getMessage());
            }
        }
    }
    
    private void addSelectedToCart() {
        int selectedRow = favoritesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to add to cart");
            return;
        }
        
        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:", "1");
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return;
        }
        
        try {
            int quantity = Integer.parseInt(quantityStr);
            String productName = tableModel.getValueAt(selectedRow, 0).toString();
            
         
            String getCartQuery = "SELECT cartID FROM cart WHERE customerID = ?";
            PreparedStatement getCartStmt = connection.prepareStatement(getCartQuery);
            getCartStmt.setInt(1, customerID);
            ResultSet cartRs = getCartStmt.executeQuery();
            
            int cartID;
            if (cartRs.next()) {
                cartID = cartRs.getInt("cartID");
            } else {
                String createCartQuery = "INSERT INTO cart (customerID) VALUES (?)";
                PreparedStatement createCartStmt = connection.prepareStatement(createCartQuery, Statement.RETURN_GENERATED_KEYS);
                createCartStmt.setInt(1, customerID);
                createCartStmt.executeUpdate();
                
                ResultSet generatedKeys = createCartStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    cartID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to create cart");
                }
            }
            
            
            String addToCartQuery = "INSERT INTO cart_item (cartID, productID, quantity) " +
                                   "SELECT ?, p.productID, ? FROM product p WHERE p.productName = ? " +
                                   "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
            PreparedStatement addToCartStmt = connection.prepareStatement(addToCartQuery);
            addToCartStmt.setInt(1, cartID);
            addToCartStmt.setInt(2, quantity);
            addToCartStmt.setString(3, productName);
            addToCartStmt.setInt(4, quantity);
            
            addToCartStmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product added to cart!");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding to cart: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity entered");
        }
    }
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        int exampleCustomerID = 1; 
        FavoriteFrame frame = new FavoriteFrame(exampleCustomerID);
        frame.setVisible(true);
    });
}

}
