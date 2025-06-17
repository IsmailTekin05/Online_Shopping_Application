import javax.swing.border.Border; 
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.LinearGradientPaint;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.sql.*;

/**
 *
 * @author dilara
 */
public class Products extends JFrame {
    private int customerID;
    
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/project_database";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678"; 

    private Connection connection;
    public static class Product {
        int productID;
        String productName;
        double price;
        String color;
        String size;
        String material;
        int stock;
        int sellerID;

        public Product(int productID, String productName, double price, String color,
                       String size, String material, int stock, int sellerID) {
            this.productID = productID;
            this.productName = productName;
            this.price = price;
            this.color = color;
            this.size = size;
            this.material = material;
            this.stock = stock;
            this.sellerID = sellerID;
        }
    }

    private List<Product> allProducts;
    private List<Product> filteredProducts;
    private JPanel productPanel;

    private String selectedColor = "All";
    private String selectedSize = "All";
    private String selectedMaterial = "All";

    private ButtonGroup colorGroup;
    private ButtonGroup sizeGroup;
    private ButtonGroup materialGroup;
     private ButtonGroup categoryGroup;
    private String selectedCategory = "All";

    public Products(int customerID) {
        this.customerID =customerID;
        initializeDatabase();
        if (connection != null) {
            initializeProducts();
            initializeGUI();
            filterProducts();
        }
    }

    private void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = DATABASE_URL + "?useSSL=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, USER, PASSWORD);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                            System.out.println("Veritabanı bağlantısı kapatıldı.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "MySQL JDBC Sürücüsü bulunamadı: " + e.getMessage(), "Sürücü Hatası", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Veritabanı bağlantısı başarısız: " + e.getMessage(), "Bağlantı Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeProducts() {
        allProducts = new ArrayList<>();

        String query = "SELECT productID, productName, price, color, size, material, stock, sellerID FROM product";

        try (java.sql.Statement stmt = connection.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                allProducts.add(new Product(
                    rs.getInt("productID"),
                    rs.getString("productName"),
                    rs.getDouble("price"),
                    rs.getString("color"),
                    rs.getString("size"),
                    rs.getString("material"),
                    rs.getInt("stock"),
                    rs.getInt("sellerID")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ürünler yüklenirken hata oluştu: " + e.getMessage(), "Veri Yükleme Hatası", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        filteredProducts = new ArrayList<>(allProducts);
    }

    private void initializeGUI() {
        setTitle("ShopNow - Online Shopping System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createHeaderPanel();
        createMainPanel();

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
private void createHeaderPanel() {
    JPanel headerPanel = new GradientHeaderPanel();
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftPanel.setOpaque(false);

    JButton myAccountButton = createStyledButton("My Account", new Color(147, 51, 234));
    myAccountButton.addActionListener(e -> {
   
        ProfileFrame accountWindow = new ProfileFrame(this.customerID);
        accountWindow.setVisible(true);
    });
    leftPanel.add(myAccountButton);

    
    JButton orderButton = createStyledButton("Order", new Color(147, 51, 234));
    orderButton.addActionListener(e -> {
        
        OrderFrame ordersWindow = new OrderFrame(this.customerID);
        ordersWindow.setVisible(true);
    });
    leftPanel.add(orderButton);
    
    
    JLabel logoLabel = new JLabel("ShopNow", SwingConstants.CENTER);
    logoLabel.setFont(new Font("Birthstone", Font.BOLD, 59));
    logoLabel.setForeground(Color.WHITE);

    
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightPanel.setOpaque(false);

    JButton gamesButton = createStyledButton("Games", new Color(34, 197, 94));
    gamesButton.addActionListener(e -> {
        GameFrame gamesWindow = new GameFrame(this.customerID);
        gamesWindow.setVisible(true);
    });
    rightPanel.add(gamesButton);

    
    JButton favoritesButton = createStyledButton("Favorites", new Color(239, 68, 68));
    favoritesButton.addActionListener(e -> {
        
        FavoriteFrame favoritesWindow = new FavoriteFrame(this.customerID);
        favoritesWindow.setVisible(true);
    });
    rightPanel.add(favoritesButton);
    JButton cartButton = createStyledButton("Cart", new Color(249, 115, 22));
    cartButton.addActionListener(e -> {
        CartFrame cartWindow = new CartFrame(this.customerID);
        cartWindow.setVisible(true);
    });
    rightPanel.add(cartButton);

    headerPanel.add(logoLabel, BorderLayout.CENTER);
    headerPanel.add(rightPanel, BorderLayout.EAST);
    headerPanel.add(leftPanel, BorderLayout.WEST);
    add(headerPanel, BorderLayout.NORTH);
}

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(178, 67));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

private void createMainPanel() {
    JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(new Color(245, 243, 248));

    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setOpaque(false);

    createFilterPanel(contentPanel); 
    createProductPanel(contentPanel); 

    JScrollPane categoryScrollPane = createCategoryPanel();
    mainPanel.add(categoryScrollPane, BorderLayout.NORTH);
    
    mainPanel.add(contentPanel, BorderLayout.CENTER);
    
    add(mainPanel, BorderLayout.CENTER);
}

private List<String> getDistinctValuesFromDB(String columnName) {
        List<String> values = new ArrayList<>();
        if (connection == null) {
            return values;
        }
        String query = "SELECT DISTINCT " + columnName + " FROM product WHERE " + columnName + " IS NOT NULL AND " + columnName + " != '' ORDER BY " + columnName + " ASC";

        try (java.sql.Statement stmt = connection.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                values.add(rs.getString(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Filtre seçenekleri yüklenemedi: " + e.getMessage(), "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }
        return values;
    }
    
    
private JScrollPane createCategoryPanel() {
    
    JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
    buttonContainerPanel.setBackground(new Color(235, 232, 248)); 

    categoryGroup = new ButtonGroup();
    
    List<String> categories = getDistinctValuesFromDB("productName");
    categories.add(0, "All");

    for (String category : categories) {
        JToggleButton categoryBtn = new JToggleButton(category);
        categoryBtn.setFont(new Font("Times New Roman", Font.BOLD, 16));
        categoryBtn.setFocusPainted(false);
        categoryBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (category.equals("All")) {
            categoryBtn.setSelected(true);
        }
        
        categoryBtn.addActionListener(e -> {
            selectedCategory = category;
            filterProducts();
        });
        
        categoryGroup.add(categoryBtn);
        buttonContainerPanel.add(categoryBtn);
    }
    
    JScrollPane scrollPane = new JScrollPane(buttonContainerPanel);

    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    
    scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

    return scrollPane; 
}
    private void createFilterPanel(JPanel parent) {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(250, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel filterTitle = new JLabel("Filters");
        filterTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        filterTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(filterTitle);
        filterPanel.add(Box.createVerticalStrut(20));

        colorGroup = new ButtonGroup();
        createRadioButtonGroup(filterPanel, "Color",
            new String[]{"All", "Black", "White", "Pink", "Red", "Orange", "Yellow","Grey","Brown",  "Green", "Blue", "Purple", "Gold", "Silver"},
            colorGroup, (e) -> {
                selectedColor = ((JRadioButton)e.getSource()).getText();
                filterProducts();
            });

        sizeGroup = new ButtonGroup();
        createRadioButtonGroup(filterPanel, "Size",
            new String[]{"All", "Standard", "XS","S" , "M", "L", "XL"},
            sizeGroup, (e) -> {
                selectedSize = ((JRadioButton)e.getSource()).getText();
                filterProducts();
            });
//wool =yün
        materialGroup = new ButtonGroup();
        createRadioButtonGroup(filterPanel, "Material",
            new String[]{"All", "Cotton", "Denim", "Wool", "Satin","Polyester","Gold", "Steel", "Plastic"},
            materialGroup, (e) -> {
                selectedMaterial = ((JRadioButton)e.getSource()).getText();
                filterProducts();
            });

        filterPanel.add(Box.createVerticalGlue()); 
        JButton clearButton = new JButton("Clear All Filters");
        clearButton.setBackground(new Color(235, 232, 248));
        clearButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        clearButton.addActionListener(e -> clearAllFilters());
        filterPanel.add(clearButton);

        parent.add(filterPanel, BorderLayout.EAST);
    }

    private void createRadioButtonGroup(JPanel parent, String title, String[] options, ButtonGroup group, ActionListener listener) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(titleLabel);

        for (String option : options) {
            JRadioButton radio = new JRadioButton(option);
            radio.setBackground(Color.WHITE);
            radio.setAlignmentX(Component.LEFT_ALIGNMENT);
            radio.addActionListener(listener);
            if (option.equals("All")) {
                radio.setSelected(true);
            }
            group.add(radio);
            parent.add(radio);
        }
        parent.add(Box.createVerticalStrut(15));
    }

    private void createProductPanel(JPanel parent) {
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3, 15, 15));
        productPanel.setBackground(new Color(245, 243, 248)); 
        productPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); 

        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setBackground(new Color(235, 232, 248));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll hızını ayarla

        parent.add(scrollPane, BorderLayout.CENTER);
    }
   


class RoundedBorder implements Border {
    private int radius;
    private Color color;

    
    RoundedBorder(int radius) {
        this.radius = radius;
        this.color = Color.GRAY; // Çerçeve rengi
    }
    
    RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius/2, this.radius/2, this.radius/2, this.radius/2);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(this.color);

        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

private void filterProducts() {
    filteredProducts.clear();

    for (Product product : allProducts) {
       
        boolean matchesCategory = selectedCategory.equals("All") || product.productName.equalsIgnoreCase(selectedCategory);
        
        boolean matchesColor = selectedColor.equals("All") || product.color.equalsIgnoreCase(selectedColor);
        boolean matchesSize = selectedSize.equals("All") || product.size.equalsIgnoreCase(selectedSize);
        boolean matchesMaterial = selectedMaterial.equals("All") || product.material.equalsIgnoreCase(selectedMaterial);

        if (matchesCategory && matchesColor && matchesSize && matchesMaterial) {
            filteredProducts.add(product);
        }
    }
    updateProductDisplay();
}

    private void updateProductDisplay() {
        productPanel.removeAll();

        if (filteredProducts.isEmpty()) {
            JLabel noProductsLabel = new JLabel("Bu kriterlere uygun ürün bulunamadı.", SwingConstants.CENTER);
            noProductsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noProductsLabel.setForeground(Color.GRAY);
            productPanel.setLayout(new BorderLayout());
            productPanel.add(noProductsLabel, BorderLayout.CENTER);
        } else {
            productPanel.setLayout(new GridLayout(0, 3, 15, 15));
            for (Product product : filteredProducts) {
                JPanel productCard = createProductCard(product);
                productPanel.add(productCard);
            }
        }

        productPanel.revalidate();
        productPanel.repaint();
    }
private JPanel createProductCard(Product product) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 215, 228)),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    card.setCursor(new Cursor(Cursor.HAND_CURSOR));
    card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
    
        ProductDetailForm detailFrame = new ProductDetailForm(product.productID, customerID);
        detailFrame.setVisible(true);
    }
});
    JLabel nameLabel = new JLabel(product.productName);
    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setOpaque(false);
    topPanel.add(nameLabel, BorderLayout.WEST);
    
    JLabel priceLabel = new JLabel(String.format("%,.2f TL", product.price));
    priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
    priceLabel.setForeground(new Color(79, 70, 229));
    topPanel.add(priceLabel, BorderLayout.EAST);
    
    card.add(topPanel, BorderLayout.NORTH);

  
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.setBackground(Color.WHITE);
    detailsPanel.add(createDetailLabel("Color: " + product.color));
    detailsPanel.add(createDetailLabel("Size: " + product.size));
    detailsPanel.add(createDetailLabel("Material: " + product.material));
    detailsPanel.add(createDetailLabel("Stock: " + product.stock));
    card.add(detailsPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    buttonPanel.setBackground(Color.WHITE);
    JButton addToCartBtn = new JButton("Add to Cart");
    addToCartBtn.setBackground(new Color(107,33,168));
    addToCartBtn.setForeground(Color.BLACK);
    addToCartBtn.setFocusPainted(false);
    addToCartBtn.addActionListener(e -> addProductToCart(product.productID));
    
    JButton favoritesBtn = new JButton("♥");
    favoritesBtn.setForeground(new Color(239, 68, 68));
    favoritesBtn.setFocusPainted(false);
    favoritesBtn.setPreferredSize(new Dimension(45, 28));
    favoritesBtn.addActionListener(e -> {
    addProductToFavorites(product.productID);});

    buttonPanel.add(favoritesBtn);
    buttonPanel.add(Box.createHorizontalStrut(5));
    buttonPanel.add(addToCartBtn);
    card.add(buttonPanel, BorderLayout.SOUTH);

    return card;
}

private void addProductToFavorites(int productID) {
    
    if (this.customerID == 0) { 
        JOptionPane.showMessageDialog(this, "Please log in to add favorites.", "Login Required", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        
        String query = "INSERT INTO favorites (customerID, productID) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, this.customerID);
        stmt.setInt(2, productID);
        
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Product added to your favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException e) {
        
        if (e.getErrorCode() == 1062) { 
            JOptionPane.showMessageDialog(this, "This product is already in your favorites.", "Already Favorited", JOptionPane.WARNING_MESSAGE);
        } else {
            
            JOptionPane.showMessageDialog(this, "Error adding to favorites: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
private void addProductToCart(int productID) {
    try {
        
        int cartID = -1;
        String checkQuery = "SELECT cartID FROM cart WHERE customerID = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
        checkStmt.setInt(1, this.customerID);
        ResultSet rs = checkStmt.executeQuery();
        
        if (rs.next()) {
            cartID = rs.getInt("cartID");
        } else {
            String insertQuery = "INSERT INTO cart (customerID) VALUES (?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, this.customerID);
            insertStmt.executeUpdate();
            
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                cartID = generatedKeys.getInt(1);
            }
        }

        if (cartID == -1) {
            JOptionPane.showMessageDialog(this, "Could not find or create a cart!", "Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        int quantityToAdd = 1; 
        String query = "INSERT INTO cart_item (cartID, productID, quantity) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, cartID);
        stmt.setInt(2, productID);
        stmt.setInt(3, quantityToAdd);
        stmt.setInt(4, quantityToAdd); 
        
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Product added to your cart!", "Success", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error adding product to cart: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setForeground(Color.GRAY);
        return label;
    }

    private void clearAllFilters() {
        
        selectedColor = "All";
        selectedSize = "All";
        selectedMaterial = "All";

        
        selectDefaultButton(colorGroup);
        selectDefaultButton(sizeGroup);
        selectDefaultButton(materialGroup);

        filterProducts();
    }
    
    
    private void selectDefaultButton(ButtonGroup group) {
        for (AbstractButton button : java.util.Collections.list(group.getElements())) {
            if (button.getText().equals("All")) {
                button.setSelected(true);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Products(1);
        });
    }
}


class GradientHeaderPanel extends JPanel implements ActionListener {

    
    private class Star {
        int x, y, size;
        float alpha; 
        Star(int x, int y, int size, float alpha) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.alpha = alpha;
        }
    }

    private Timer timer;
    private final List<Star> stars;
    private final Random random;
    private static final int MAX_STARS = 55;
    public GradientHeaderPanel() {
        setLayout(new BorderLayout());
        this.stars = new ArrayList<>();
        this.random = new Random();

        
        timer = new Timer(1, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


    Point2D startPoint = new Point2D.Float(0, 0);
    Point2D endPoint = new Point2D.Float(getWidth(), 0);

 
    Color[] colors = {
        new Color(46, 26, 70),     
        new Color(126, 34, 206),   
        new Color(46, 26, 70)};
    float[] fractions = {0.0f, 0.50f, 1.0f};

    
    LinearGradientPaint linearGradient = new LinearGradientPaint(startPoint, endPoint, fractions, colors);

    g2d.setPaint(linearGradient);
    g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Star star : stars) {
            
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, star.alpha));
   
            g2d.setColor(Color.WHITE);
            
            g2d.fillOval(star.x, star.y, star.size, star.size);
        }
      
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if (random.nextInt(10) > 7 && stars.size() < MAX_STARS) { 
        int x = random.nextInt(getWidth());
        int y = random.nextInt(getHeight());
        int size = random.nextInt(3) + 1;
        stars.add(new Star(x, y, size, 0f)); 
    }


    List<Star> starsToRemove = new ArrayList<>();
    for (Star star : stars) {
        star.alpha += 0.05f * (random.nextFloat() - 0.4f); 
        if (star.alpha > 1f) star.alpha = 1f;
        if (star.alpha < 0f) {
            star.alpha = 0f;
            starsToRemove.add(star); 
        }
    }
    stars.removeAll(starsToRemove); 
    repaint();
}
}