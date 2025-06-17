import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class CartFrame extends JFrame {
    private JTable couponTable;
    private DefaultTableModel couponTableModel;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JButton updateButton, removeButton, checkoutButton, applyCouponButton, removeCouponButton;
    private JLabel totalLabel, discountLabel;
    
    private Connection connection;
    private int customerID;
    private int cartID;
    private double currentSubTotal = 0;
    private String appliedCouponCode = null;
    private double appliedDiscountPercentage = 0;

    public CartFrame(int customerID) {
        this.customerID = customerID;
        connectToDatabase();
        createCartIfNotExists();
        initializeComponents();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed!\n" + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createCartIfNotExists() {
        try (PreparedStatement checkStmt = connection.prepareStatement("SELECT cartID FROM cart WHERE customerID = ?")) {
            checkStmt.setInt(1, customerID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                cartID = rs.getInt("cartID");
            } else {
                try (PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO cart (customerID) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setInt(1, customerID);
                    insertStmt.executeUpdate();
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        cartID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing cart: " + e.getMessage());
        }
    }

    private void initializeComponents() {
        setTitle("Shopping Cart");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 243, 248));

        // Tema Renkleri
        Color headerColor = new Color(235, 232, 240);
        Color panelColor = Color.WHITE;
        Color textColor = Color.BLACK;
        Color headerTextColor = new Color(55, 65, 81);
        Color primaryButtonColor = new Color(126, 34, 206);
        Color destructiveButtonColor = new Color(220, 38, 38);

        // sol taraf
        JPanel leftPanel = new JPanel(new BorderLayout(0, 5));
        leftPanel.setOpaque(false);
        couponTableModel = new DefaultTableModel(new String[]{"Code", "Discount"}, 0);
        couponTable = new JTable(couponTableModel);
        styleTable(couponTable, headerColor, panelColor, textColor, headerTextColor);
        leftPanel.add(new JScrollPane(couponTable), BorderLayout.CENTER);
        
        JPanel couponButtonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        applyCouponButton = new JButton("Apply Coupon");
        removeCouponButton = new JButton("Remove Coupon");
        removeCouponButton.setEnabled(false);
        couponButtonPanel.add(applyCouponButton);
        couponButtonPanel.add(removeCouponButton);
        leftPanel.add(couponButtonPanel, BorderLayout.SOUTH);

        // Sağ taraf
        tableModel = new DefaultTableModel(new String[]{"Product", "Price", "Qty", "Total"}, 0);
        cartTable = new JTable(tableModel);
        styleTable(cartTable, headerColor, panelColor, textColor, headerTextColor);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, new JScrollPane(cartTable));
        splitPane.setResizeWeight(0.35);
        add(splitPane, BorderLayout.CENTER);

        // Alt taraf
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.setOpaque(false);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.setOpaque(false);
        totalLabel = new JLabel("Subtotal: 0.00 TL");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        discountLabel = new JLabel("Discount: 0.00 TL");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        discountLabel.setForeground(destructiveButtonColor);
        totalPanel.add(totalLabel);
        totalPanel.add(discountLabel);
        bottomPanel.add(totalPanel, BorderLayout.CENTER);

        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionButtonPanel.setOpaque(false);
        updateButton = new JButton("Update Quantity");
        removeButton = new JButton("Remove from Cart");
        checkoutButton = new JButton("Proceed to Checkout");
        styleButton(checkoutButton, primaryButtonColor);
        
        actionButtonPanel.add(updateButton);
        actionButtonPanel.add(removeButton);
        actionButtonPanel.add(checkoutButton);
        bottomPanel.add(actionButtonPanel, BorderLayout.WEST);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        applyCouponButton.addActionListener(e -> applyCoupon());
        removeCouponButton.addActionListener(e -> removeCoupon());
        updateButton.addActionListener(e -> updateQuantity());
        removeButton.addActionListener(e -> removeFromCart());
        checkoutButton.addActionListener(e -> checkout());
        
        loadCartData();
        loadAvailableCoupons();
    }
    

    private void styleTable(JTable table, Color headerBg, Color tableBg, Color fg, Color headerFg) {
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(headerBg);
        table.getTableHeader().setForeground(headerFg);
        table.setBackground(tableBg);
        table.setForeground(fg);
        table.setGridColor(headerBg);
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(209, 196, 233));
        table.setSelectionForeground(Color.BLACK);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
    
private void loadAvailableCoupons() {
    try {
        couponTableModel.setRowCount(0);
        String query = "SELECT couponCode, discountAmount, expirationDate FROM coupon WHERE expirationDate >= CURDATE()";
        
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            couponTableModel.addRow(new Object[]{
                rs.getString("couponCode"),
                "%" + rs.getInt("discountAmount"),
                rs.getDate("expirationDate").toString()
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading coupons: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void loadCartData() {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT p.productName, p.price, ci.quantity, (p.price * ci.quantity) as total FROM cart_item ci JOIN product p ON ci.productID = p.productID WHERE ci.cartID = ?")) {
            tableModel.setRowCount(0);
            this.currentSubTotal = 0;
            stmt.setInt(1, cartID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("productName"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getDouble("total")
                });
                this.currentSubTotal += rs.getDouble("total");
            }
            updateTotalLabel();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading cart data: " + e.getMessage());
        }
    }

    private void applyCoupon() {
        int selectedRow = couponTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a coupon to apply.", "No Coupon Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        appliedCouponCode = couponTableModel.getValueAt(selectedRow, 0).toString();
        String discountStr = couponTableModel.getValueAt(selectedRow, 1).toString().replace("%", "");
        appliedDiscountPercentage = Double.parseDouble(discountStr) / 100.0;
        updateTotalLabel();
        applyCouponButton.setEnabled(false);
        removeCouponButton.setEnabled(true);
    }

    private void removeCoupon() {
        appliedCouponCode = null;
        appliedDiscountPercentage = 0;
        updateTotalLabel();
        applyCouponButton.setEnabled(true);
        removeCouponButton.setEnabled(false);
    }
    
    private void updateTotalLabel() {
        double discountAmount = this.currentSubTotal * this.appliedDiscountPercentage;
        double finalTotal = this.currentSubTotal - discountAmount;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
        totalLabel.setText("Subtotal: " + currencyFormat.format(this.currentSubTotal));
        discountLabel.setText("Discount: -" + currencyFormat.format(discountAmount));
        setTitle("Shopping Cart | Final Total: " + currencyFormat.format(finalTotal));
    }

    private void updateQuantity() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to update.");
            return;
        }
        String newQuantityStr = JOptionPane.showInputDialog(this, "Enter new quantity:", tableModel.getValueAt(selectedRow, 2));
        if (newQuantityStr == null) return;
        try {
            int newQuantity = Integer.parseInt(newQuantityStr);
            if (newQuantity <= 0) {
                removeFromCart(); 
                return;
            }
            String productName = tableModel.getValueAt(selectedRow, 0).toString();
            try (PreparedStatement stmt = connection.prepareStatement("UPDATE cart_item ci JOIN product p ON ci.productID = p.productID SET ci.quantity = ? WHERE ci.cartID = ? AND p.productName = ?")) {
                stmt.setInt(1, newQuantity);
                stmt.setInt(2, cartID);
                stmt.setString(3, productName);
                stmt.executeUpdate();
                loadCartData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating quantity: " + e.getMessage());
        }
    }

    private void removeFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Remove this item from cart?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String productName = tableModel.getValueAt(selectedRow, 0).toString();
            try (PreparedStatement stmt = connection.prepareStatement("DELETE ci FROM cart_item ci JOIN product p ON ci.productID = p.productID WHERE ci.cartID = ? AND p.productName = ?")) {
                stmt.setInt(1, cartID);
                stmt.setString(2, productName);
                stmt.executeUpdate();
                loadCartData();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error removing item: " + e.getMessage());
            }
        }
    }
private void checkout() {
    if (tableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Cart is empty!");
        return;
    }

    AddressSelectionDialog addressDialog = new AddressSelectionDialog(this, connection, this.customerID);
    addressDialog.setVisible(true); 
    String selectedAddress = addressDialog.getSelectedAddress();

    if (selectedAddress == null) {
        JOptionPane.showMessageDialog(this, "Checkout cancelled: No address selected.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    PaymentSelectionDialog paymentDialog = new PaymentSelectionDialog(this, connection, this.customerID);
    paymentDialog.setVisible(true);
    String selectedCard = paymentDialog.getSelectedCardNumber();

    if (selectedCard == null) {
        JOptionPane.showMessageDialog(this, "Checkout cancelled: No payment method selected.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    try {
        
        String stockCheckQuery = "SELECT p.productName FROM cart_item ci JOIN product p ON ci.productID = p.productID WHERE ci.cartID = ? AND ci.quantity > p.stock";
        PreparedStatement stockStmt = connection.prepareStatement(stockCheckQuery);
        stockStmt.setInt(1, cartID);
        ResultSet rs = stockStmt.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Insufficient stock for product: " + rs.getString(1), "Stock Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CallableStatement stmt = connection.prepareCall("{CALL checkout_cart(?, ?, ?)}");
        stmt.setInt(1, this.cartID);
        stmt.setString(2, this.appliedCouponCode); 
        stmt.setString(3, selectedAddress);       
        stmt.execute();
        
        JOptionPane.showMessageDialog(this, "Order placed successfully using card ending in " + selectedCard + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        loadCartData(); 
        removeCoupon();
        loadAvailableCoupons();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error during final checkout: " + e.getMessage());
        e.printStackTrace();
    }
}
}