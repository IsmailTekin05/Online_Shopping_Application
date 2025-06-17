/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SellerDashboard extends JFrame {
    private int sellerID;
    private String sellerName;
    private Connection connection;
    
    private JTabbedPane tabbedPane;
    private JPanel couponPanel, productPanel, shippingPanel, incomePanel;
    
    private JTextField couponCodeField, discountAmountField;
    private JSpinner expirationDateSpinner;
    private JButton createCouponBtn, deleteCouponBtn;
    private JLabel currentCouponLabel;
    
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTextField productNameField, priceField, colorField, sizeField, materialField, stockField;
    private JButton addProductBtn, removeProductBtn, updateStockBtn;
    
    private JComboBox<String> shippingCompanyComboBox;
    private JButton changeShippingBtn;
    private JLabel currentShippingLabel;
    
    private JLabel totalIncomeLabel, monthlyIncomeLabel;
    private JTable orderHistoryTable;
    private DefaultTableModel orderHistoryTableModel;
    
    public SellerDashboard(int sellerID, String sellerName) {
    this.sellerID = sellerID;
    this.sellerName = sellerName;
    
    initializeDatabase();
    
    
    if (!testDatabaseConnection()) {
        JOptionPane.showMessageDialog(null, 
            "Failed to connect to database. Please check your connection settings.", 
            "Database Connection Error", 
            JOptionPane.ERROR_MESSAGE);
        System.exit(1); }
    
    setTitle("Seller Dashboard - " + sellerName);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 700);
    setLocationRelativeTo(null);
    initializeComponents();
    loadData();
}
   
    
    
    private void initializeDatabase() {
        try {
            
            String url = "jdbc:mysql://localhost:3306/project_database";
            String username = "root";
            String password = "12345678";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }
    
    private boolean testDatabaseConnection() {
        try {
        String testSql = "SELECT 1";
        PreparedStatement stmt = connection.prepareStatement(testSql);
        ResultSet rs = stmt.executeQuery();
        rs.close();
        stmt.close();
        return true;
        } catch (SQLException e) {
        System.out.println("Database connection test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
        }
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel titleLabel = new JLabel("Seller Dashboard - " + sellerName);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane();
        
        initializeCouponPanel();
        initializeProductPanel();
        initializeShippingPanel();
        initializeIncomePanel();
        
        tabbedPane.addTab("Coupons", couponPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Shipping", shippingPanel);
        tabbedPane.addTab("Income", incomePanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void initializeCouponPanel() {
        couponPanel = new JPanel(new BorderLayout());
        
        JPanel currentCouponPanel = new JPanel(new FlowLayout());
        currentCouponPanel.setBorder(BorderFactory.createTitledBorder("Current Coupon"));
        currentCouponLabel = new JLabel("No active coupon");
        currentCouponPanel.add(currentCouponLabel);
        couponPanel.add(currentCouponPanel, BorderLayout.NORTH);
        
        
        JPanel createCouponPanel = new JPanel(new GridBagLayout());
        createCouponPanel.setBorder(BorderFactory.createTitledBorder("Create New Coupon"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        createCouponPanel.add(new JLabel("Coupon Code:"), gbc);
        gbc.gridx = 1;
        couponCodeField = new JTextField(15);
        createCouponPanel.add(couponCodeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        createCouponPanel.add(new JLabel("Discount Amount:"), gbc);
        gbc.gridx = 1;
        discountAmountField = new JTextField(15);
        createCouponPanel.add(discountAmountField, gbc);
        
       
        
        gbc.gridx = 0; gbc.gridy = 3;
        createCouponPanel.add(new JLabel("Expiration Date:"), gbc);
        gbc.gridx = 1;
        expirationDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(expirationDateSpinner, "yyyy-MM-dd");
        expirationDateSpinner.setEditor(dateEditor);
        createCouponPanel.add(expirationDateSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        createCouponBtn = new JButton("Create Coupon");
        deleteCouponBtn = new JButton("Delete Current Coupon");
        buttonPanel.add(createCouponBtn);
        buttonPanel.add(deleteCouponBtn);
        createCouponPanel.add(buttonPanel, gbc);
        
        couponPanel.add(createCouponPanel, BorderLayout.CENTER);
        
        createCouponBtn.addActionListener(e -> createCoupon());
        deleteCouponBtn.addActionListener(e -> deleteCoupon());
    }
private void createCoupon() {
    try {
        String code = couponCodeField.getText().trim();
        String discountStr = discountAmountField.getText().trim();
        
        if (code.isEmpty() || discountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all coupon fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double discount = Double.parseDouble(discountStr);
        if (discount <= 0) {
            JOptionPane.showMessageDialog(this, "Discount amount must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date expirationDate = (Date) expirationDateSpinner.getValue();
        if (expirationDate.before(new Date())) {
            JOptionPane.showMessageDialog(this, "Expiration date must be in the future!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String sql = "INSERT INTO coupon (couponCode, discountAmount, expirationDate, sellerID) VALUES (?, ?, ?, ?)";
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, code);
        stmt.setDouble(2, discount);
        stmt.setDate(3, new java.sql.Date(expirationDate.getTime()));
        stmt.setInt(4, this.sellerID);
        
        int result = stmt.executeUpdate();
        
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Coupon created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            couponCodeField.setText("");
            discountAmountField.setText("");
            loadCurrentCoupon(); 
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter a valid number for the discount amount!", "Validation Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
        
        if (e.getErrorCode() == 1062) {
             JOptionPane.showMessageDialog(this, "This coupon code already exists. Please choose another one.", "Duplicate Code", JOptionPane.ERROR_MESSAGE);
        } else {
             JOptionPane.showMessageDialog(this, "Database error while creating coupon:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        e.printStackTrace();
    }
}
    
    private void initializeProductPanel() {
        productPanel = new JPanel(new BorderLayout());
        
        String[] columnNames = {"Product ID", "Name", "Price", "Color", "Size", "Material", "Stock"};
        productTableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productTableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);
        productPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel productFormPanel = new JPanel(new GridBagLayout());
        productFormPanel.setBorder(BorderFactory.createTitledBorder("Product Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        productFormPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        productNameField = new JTextField(15);
        productFormPanel.add(productNameField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        productFormPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 3;
        priceField = new JTextField(10);
        productFormPanel.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        productFormPanel.add(new JLabel("Color:"), gbc);
        gbc.gridx = 1;
        colorField = new JTextField(15);
        productFormPanel.add(colorField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        productFormPanel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 3;
        sizeField = new JTextField(10);
        productFormPanel.add(sizeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        productFormPanel.add(new JLabel("Material:"), gbc);
        gbc.gridx = 1;
        materialField = new JTextField(15);
        productFormPanel.add(materialField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2;
        productFormPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 3;
        stockField = new JTextField(10);
        productFormPanel.add(stockField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        JPanel buttonPanel = new JPanel();
        addProductBtn = new JButton("Add Product");
        removeProductBtn = new JButton("Remove Selected");
        updateStockBtn = new JButton("Update Stock");
        buttonPanel.add(addProductBtn);
        buttonPanel.add(removeProductBtn);
        buttonPanel.add(updateStockBtn);
        productFormPanel.add(buttonPanel, gbc);
        
        productPanel.add(productFormPanel, BorderLayout.SOUTH);
        
        addProductBtn.addActionListener(e -> addProduct());
        removeProductBtn.addActionListener(e -> removeProduct());
        updateStockBtn.addActionListener(e -> updateStock());
        
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateProductForm();
            }
        });
    }
    
    private void initializeShippingPanel() {
        shippingPanel = new JPanel(new BorderLayout());
        
        JPanel shippingFormPanel = new JPanel(new GridBagLayout());
        shippingFormPanel.setBorder(BorderFactory.createTitledBorder("Shipping Company Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        shippingFormPanel.add(new JLabel("Current Shipping Company:"), gbc);
        gbc.gridx = 1;
        currentShippingLabel = new JLabel("Loading...");
        currentShippingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        shippingFormPanel.add(currentShippingLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        shippingFormPanel.add(new JLabel("Change to:"), gbc);
        gbc.gridx = 1;
        shippingCompanyComboBox = new JComboBox<>();
        shippingFormPanel.add(shippingCompanyComboBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        changeShippingBtn = new JButton("Change Shipping Company");
        shippingFormPanel.add(changeShippingBtn, gbc);
        
        shippingPanel.add(shippingFormPanel, BorderLayout.CENTER);
        
        changeShippingBtn.addActionListener(e -> changeShippingCompany());
    }
    
    private void initializeIncomePanel() {
        incomePanel = new JPanel(new BorderLayout());
        
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Income Summary"));
        
        summaryPanel.add(new JLabel("Total Income:"));
        totalIncomeLabel = new JLabel("$0.00");
        totalIncomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryPanel.add(totalIncomeLabel);
        
        summaryPanel.add(new JLabel("This Month:"));
        monthlyIncomeLabel = new JLabel("$0.00");
        monthlyIncomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryPanel.add(monthlyIncomeLabel);
        
        incomePanel.add(summaryPanel, BorderLayout.NORTH);
        
        
        String[] columnNames = {"Order ID", "Date", "Product", "Quantity", "Total"};
        orderHistoryTableModel = new DefaultTableModel(columnNames, 0);
        orderHistoryTable = new JTable(orderHistoryTableModel);
        JScrollPane scrollPane = new JScrollPane(orderHistoryTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order History"));
        incomePanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadData() {
        loadProducts();
        loadShippingCompanies();
        loadCurrentCoupon();
        loadCurrentShippingCompany();
        loadIncomeData();
    }
    
    
    private void loadProducts() {
        try {
            String sql = "SELECT * FROM product WHERE sellerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, sellerID);
            ResultSet rs = stmt.executeQuery();
            
            productTableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("productID"),
                    rs.getString("productName"),
                    rs.getDouble("price"),
                    rs.getString("color"),
                    rs.getString("size"),
                    rs.getString("material"),
                    rs.getInt("stock")
                };
                productTableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }
    
    private void loadShippingCompanies() {
        try {
            String sql = "SELECT shipmentID, companyName FROM shipping_company";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            shippingCompanyComboBox.removeAllItems();
            while (rs.next()) {
                shippingCompanyComboBox.addItem(rs.getInt("shipmentID") + " - " + rs.getString("companyName"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading shipping companies: " + e.getMessage());
        }
    }
    
    
    private void loadCurrentShippingCompany() {
        try {
            String sql = "SELECT sc.companyName FROM seller s JOIN shipping_company sc ON s.shipmentID = sc.shipmentID WHERE s.sellerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, sellerID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                currentShippingLabel.setText(rs.getString("companyName"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading shipping company: " + e.getMessage());
        }
    }
private void loadIncomeData() {
    try {
        String totalIncomeSql = "SELECT SUM(p.price) as totalIncome " +
                                "FROM order_product op " +
                                "JOIN product p ON op.productID = p.productID " +
                                "WHERE p.sellerID = ?";
        PreparedStatement totalStmt = connection.prepareStatement(totalIncomeSql);
        totalStmt.setInt(1, sellerID);
        ResultSet totalRs = totalStmt.executeQuery();
        if (totalRs.next()) {
            totalIncomeLabel.setText(String.format("%,.2f TL", totalRs.getDouble("totalIncome")));
        }
        
        String monthlyIncomeSql = "SELECT SUM(p.price) as monthlyIncome " +
                                  "FROM order_product op " +
                                  "JOIN product p ON op.productID = p.productID " +
                                  "JOIN orders o ON op.orderID = o.orderID " +
                                  "WHERE p.sellerID = ? AND MONTH(o.orderDate) = MONTH(CURDATE()) AND YEAR(o.orderDate) = YEAR(CURDATE())";
        PreparedStatement monthlyStmt = connection.prepareStatement(monthlyIncomeSql);
        monthlyStmt.setInt(1, sellerID);
        ResultSet monthlyRs = monthlyStmt.executeQuery();
        if (monthlyRs.next()) {
            monthlyIncomeLabel.setText(String.format("%,.2f TL", monthlyRs.getDouble("monthlyIncome")));
        }
        
        String historySql = "SELECT o.orderID, o.orderDate, p.productName, 1 AS quantity, p.price AS total " +
                            "FROM order_product op " +
                            "JOIN product p ON op.productID = p.productID " +
                            "JOIN orders o ON op.orderID = o.orderID " +
                            "WHERE p.sellerID = ? ORDER BY o.orderDate DESC";
        PreparedStatement historyStmt = connection.prepareStatement(historySql);
        historyStmt.setInt(1, sellerID);
        ResultSet historyRs = historyStmt.executeQuery();
        
        orderHistoryTableModel.setRowCount(0);
        while (historyRs.next()) {
            orderHistoryTableModel.addRow(new Object[]{
                historyRs.getInt("orderID"),
                historyRs.getDate("orderDate"),
                historyRs.getString("productName"),
                historyRs.getInt("quantity"), 
                String.format("%,.2f TL", historyRs.getDouble("total"))
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading income data: " + e.getMessage());
        e.printStackTrace();
    }
}

private void loadCurrentCoupon() {
    try {
        String sql = "SELECT * FROM coupon WHERE sellerID = ? AND expirationDate >= CURDATE() ORDER BY expirationDate DESC LIMIT 1";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, sellerID);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            currentCouponLabel.setText("Code: " + rs.getString("couponCode") + 
                                     " | Discount:" + String.format("%.2f", rs.getDouble("discountAmount") )+"%" + 
                                     " | Expires: " + rs.getDate("expirationDate"));
            createCouponBtn.setEnabled(false);
            deleteCouponBtn.setEnabled(true);
        } else {
            currentCouponLabel.setText("No active coupon");
            createCouponBtn.setEnabled(true);
            deleteCouponBtn.setEnabled(false);
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Error loading current coupon:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void addProduct() {
    try {
        String name = productNameField.getText().trim();
        String priceStr = priceField.getText().trim();
        String color = colorField.getText().trim();
        String size = sizeField.getText().trim();
        String material = materialField.getText().trim();
        String stockStr = stockField.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            productNameField.requestFocus();
            return;
        }
        
        if (priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a price!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            priceField.requestFocus();
            return;
        }
        
        if (stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter stock quantity!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            stockField.requestFocus();
            return;
        }
        
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                priceField.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price (numbers only)!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            priceField.requestFocus();
            return;
        }
        
        int stock;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "Stock cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                stockField.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity (whole numbers only)!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            stockField.requestFocus();
            return;
        }
        
        String sql = "INSERT INTO product (productName, price, color, size, material, stock, sellerID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setDouble(2, price);
        stmt.setString(3, color.isEmpty() ? null : color);  
        stmt.setString(4, size.isEmpty() ? null : size);
        stmt.setString(5, material.isEmpty() ? null : material);
        stmt.setInt(6, stock);
        stmt.setInt(7, sellerID);
        
        int result = stmt.executeUpdate();
        
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearProductForm();
            loadProducts();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add product. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (SQLException e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Database error while adding product:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Unexpected error while adding product:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void deleteCoupon() {
    try {
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete the current coupon?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (result != JOptionPane.YES_OPTION) {
            return; 
        }
        
        String sql = "DELETE FROM coupon WHERE sellerID = ? AND expirationDate >= CURDATE()";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, sellerID);
        int deleteResult = stmt.executeUpdate();
        
        if (deleteResult > 0) {
            JOptionPane.showMessageDialog(this, "Coupon deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadCurrentCoupon(); 
        } else {
            JOptionPane.showMessageDialog(this, "No active coupon found to delete.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Database error while deleting coupon:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void removeProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to remove");
            return;
        }
        
        try {
            int productID = (Integer) productTableModel.getValueAt(selectedRow, 0);
            
            String sql = "DELETE FROM product WHERE productID = ? AND sellerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, productID);
            stmt.setInt(2, sellerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product removed successfully!");
            
            loadProducts();
            clearProductForm();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing product: " + e.getMessage());
        }
    }
    
    private void updateStock() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update");
            return;
        }
        
        try {
            int productID = (Integer) productTableModel.getValueAt(selectedRow, 0);
            String stockStr = stockField.getText().trim();
            
            if (stockStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter stock amount");
                return;
            }
            
            int newStock = Integer.parseInt(stockStr);
            
            String sql = "UPDATE product SET stock = ? WHERE productID = ? AND sellerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newStock);
            stmt.setInt(2, productID);
            stmt.setInt(3, sellerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Stock updated successfully!");
            
            loadProducts();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating stock: " + e.getMessage());
        }
    }
    
    private void changeShippingCompany() {
        String selection = (String) shippingCompanyComboBox.getSelectedItem();
        if (selection == null) {
            JOptionPane.showMessageDialog(this, "Please select a shipping company");
            return;
        }
        
        try {
            int shipmentID = Integer.parseInt(selection.split(" - ")[0]);
            
            String sql = "UPDATE seller SET shipmentID = ? WHERE sellerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, shipmentID);
            stmt.setInt(2, sellerID);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Shipping company changed successfully!");
            
            loadCurrentShippingCompany();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error changing shipping company: " + e.getMessage());
        }
    }
    
    private void populateProductForm() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            productNameField.setText((String) productTableModel.getValueAt(selectedRow, 1));
            priceField.setText(productTableModel.getValueAt(selectedRow, 2).toString());
            colorField.setText((String) productTableModel.getValueAt(selectedRow, 3));
            sizeField.setText((String) productTableModel.getValueAt(selectedRow, 4));
            materialField.setText((String) productTableModel.getValueAt(selectedRow, 5));
            stockField.setText(productTableModel.getValueAt(selectedRow, 6).toString());
        }
    }
    
    private void clearProductForm() {
        productNameField.setText("");
        priceField.setText("");
        colorField.setText("");
        sizeField.setText("");
        materialField.setText("");
        stockField.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SellerDashboard(1, "Example Seller").setVisible(true);
        });
    }
}

