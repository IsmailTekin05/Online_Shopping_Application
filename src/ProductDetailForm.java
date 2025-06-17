import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class ProductDetailForm extends JFrame {

    private int productID;
    private int customerID;
    private Connection connection;

    
    private JLabel averageProductRatingLabel;
    private JTextArea reviewsArea;
    private StarRatingPanel productRatingPanel;
    private StarRatingPanel sellerRatingPanel;
    private JTextArea myReviewTextArea;
    private JTabbedPane actionTabPane; 

    public ProductDetailForm(int productID, int customerID) {
        this.productID = productID;
        this.customerID = customerID;
        connectToDatabase();
        initializeUI();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void initializeUI() {
        setTitle("Product Details & Reviews");
        setSize(850, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
        
        loadPageContent();
    }

    private void loadPageContent() {
        getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        try (PreparedStatement productStmt = connection.prepareStatement("SELECT p.*, s.sellerName, s.sellerID, s.rating as seller_rating FROM product p JOIN seller s ON p.sellerID = s.sellerID WHERE p.productID = ?")) {
            productStmt.setInt(1, productID);
            ResultSet productRs = productStmt.executeQuery();

            if (productRs.next()) {
                gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.weighty = 0; gbc.gridheight = 1;
                mainPanel.add(createActionPanel(productRs.getInt("sellerID")), gbc);

                gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
                mainPanel.add(createProductInfoPanel(productRs), gbc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weighty = 1.0;
        mainPanel.add(createReviewListPanel(), gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        checkIfAlreadyReviewed();

        revalidate();
        repaint();
    }
    
    private JPanel createProductInfoPanel(ResultSet productRs) throws SQLException {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel productNameLabel = new JLabel(productRs.getString("productName"));
        productNameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        infoPanel.add(productNameLabel);
        
        averageProductRatingLabel = new JLabel("Loading product rating...");
        averageProductRatingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        averageProductRatingLabel.setForeground(new Color(252, 186, 3));
        infoPanel.add(averageProductRatingLabel);
        loadAverageProductRating();
        
        infoPanel.add(Box.createVerticalStrut(10));
        JLabel priceLabel = new JLabel(String.format("%,.2f TL", productRs.getDouble("price")));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(20));

        infoPanel.add(new JLabel("Seller: " + productRs.getString("sellerName")));
        JLabel sellerRatingLabel = new JLabel(String.format("Seller Rating: %.2f / 5.0", productRs.getDouble("seller_rating")));
        infoPanel.add(sellerRatingLabel);
        
        return infoPanel;
    }

    private JTabbedPane createActionPanel(int sellerId) {
        actionTabPane = new JTabbedPane();

        JPanel productReviewTab = new JPanel(new BorderLayout(5, 5));
        productReviewTab.setBorder(new EmptyBorder(10,10,10,10));
        productReviewTab.add(new JLabel("Your Rating for this Product (1-5):"), BorderLayout.NORTH);
        productRatingPanel = new StarRatingPanel(5);
        productReviewTab.add(productRatingPanel, BorderLayout.CENTER);
        
        JPanel productCommentPanel = new JPanel(new BorderLayout());
        myReviewTextArea = new JTextArea(5, 20);
        productCommentPanel.add(new JScrollPane(myReviewTextArea), BorderLayout.CENTER);
        JButton submitProductReviewBtn = new JButton("Submit Product Review");
        submitProductReviewBtn.addActionListener(e -> submitProductReview());
        productCommentPanel.add(submitProductReviewBtn, BorderLayout.SOUTH);
        productReviewTab.add(productCommentPanel, BorderLayout.SOUTH);
        
        // Satıcı Puanlama Sekmesi
        JPanel sellerRatingTab = new JPanel(new BorderLayout(5,5));
        sellerRatingTab.setBorder(new EmptyBorder(10,10,10,10));
        sellerRatingTab.add(new JLabel("Your Rating for the Seller (1-10):"), BorderLayout.NORTH);
        sellerRatingPanel = new StarRatingPanel(10);
        sellerRatingTab.add(sellerRatingPanel, BorderLayout.CENTER);
        JButton submitSellerRatingBtn = new JButton("Submit Seller Rating");
        submitSellerRatingBtn.addActionListener(e -> submitSellerRating(sellerId));
        sellerRatingTab.add(submitSellerRatingBtn, BorderLayout.SOUTH);

        actionTabPane.addTab("Review Product", productReviewTab);
        actionTabPane.addTab("Rate Seller", sellerRatingTab);
        
        return actionTabPane;
    }

    private JPanel createReviewListPanel() {
        JPanel reviewsMainPanel = new JPanel(new BorderLayout());
        reviewsMainPanel.setBorder(BorderFactory.createTitledBorder("Customer Reviews"));
        reviewsArea = new JTextArea(10, 0);
        reviewsArea.setEditable(false);
        reviewsArea.setFont(new Font("Arial", Font.PLAIN, 13));
        reviewsArea.setLineWrap(true);
        reviewsArea.setWrapStyleWord(true);
        reviewsMainPanel.add(new JScrollPane(reviewsArea), BorderLayout.CENTER);
        loadReviews();
        return reviewsMainPanel;
    }
    
    private void loadAverageProductRating(){
        String query = "SELECT AVG(stars) as avg_rating, COUNT(*) as review_count FROM review WHERE productID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int reviewCount = rs.getInt("review_count");
                if (reviewCount > 0) {
                    double avgRating = rs.getDouble("avg_rating");
                    averageProductRatingLabel.setText(String.format("★★★★★ (%.1f / 5.0 from %d reviews)", avgRating, reviewCount));
                } else {
                    averageProductRatingLabel.setText("★★★★★ (No reviews yet)");
                }
            }
        } catch (SQLException e) {
            averageProductRatingLabel.setText("Rating not available");
            e.printStackTrace();
        }
    }

    private void loadReviews() {
        String query = "SELECT r.stars, r.comment, c.customer_name FROM review r JOIN customer c ON r.customerID = c.customerID WHERE r.productID = ? ORDER BY r.rating_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                int stars = rs.getInt("stars");
                String starString = "★".repeat(stars) + "☆".repeat(5 - stars);
                sb.append("Rating: ").append(starString).append("\n");
                sb.append("By: ").append(rs.getString("customer_name")).append("\n");
                sb.append("\"").append(rs.getString("comment")).append("\"\n");
                sb.append("-------------------------------------------------\n");
            }
            reviewsArea.setText(sb.length() == 0 ? "This product has no reviews yet." : sb.toString());
            reviewsArea.setCaretPosition(0);
        } catch (SQLException e) {
            reviewsArea.setText("Could not load reviews.");
            e.printStackTrace();
        }
    }

    private void submitProductReview() {
        int stars = productRatingPanel.getSelectedRating();
        String comment = myReviewTextArea.getText().trim();
        if (stars == 0) {
            JOptionPane.showMessageDialog(this, "Please select a star rating for the product.");
            return;
        }
        
        String query = "INSERT INTO review (productID, customerID, stars, comment, rating_date) VALUES (?, ?, ?, ?, CURDATE())";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.productID);
            stmt.setInt(2, this.customerID);
            stmt.setInt(3, stars);
            stmt.setString(4, comment);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thank you! Your review has been submitted.");
            loadPageContent(); 
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "You have already reviewed this product.");
            } else {
                JOptionPane.showMessageDialog(this, "Error submitting review: " + e.getMessage());
            }
        }
    }
    
private void submitSellerRating(int sellerId) {
    try {
        
        String checkQuery = "SELECT COUNT(*) as review_count FROM review r " +
                            "JOIN product p ON r.productID = p.productID " +
                            "WHERE r.customerID = ? AND p.sellerID = ?";
        
        PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
        checkStmt.setInt(1, this.customerID);
        checkStmt.setInt(2, sellerId);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getInt("review_count") > 0) {
            JOptionPane.showMessageDialog(this, "You have already reviewed a product from this seller, so you cannot rate them again.", "Already Rated", JOptionPane.INFORMATION_MESSAGE);
            return; 
        }
        
       

        int ratingOutOf10 = sellerRatingPanel.getSelectedRating();
        if (ratingOutOf10 == 0) {
            JOptionPane.showMessageDialog(this, "Please select a star rating for the seller.");
            return;
        }
        
        
        double ratingOutOf5 = ratingOutOf10 / 2.0;

        
        String getOldRatingQuery = "SELECT rating, (SELECT COUNT(*) FROM review r JOIN product p ON r.productID = p.productID WHERE p.sellerID = ?) as review_count FROM seller WHERE sellerID = ?";
        PreparedStatement oldRatingStmt = connection.prepareStatement(getOldRatingQuery);
        oldRatingStmt.setInt(1, sellerId);
        oldRatingStmt.setInt(2, sellerId);
        ResultSet oldRatingRs = oldRatingStmt.executeQuery();

        if(oldRatingRs.next()){
            double currentRating = oldRatingRs.getDouble("rating");
            int ratingCount = oldRatingRs.getInt("review_count"); 

            double newAverageRating = ((currentRating * ratingCount) + ratingOutOf5) / (ratingCount + 1);

            String updateQuery = "UPDATE seller SET rating = ? WHERE sellerID = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
            updateStmt.setDouble(1, newAverageRating);
            updateStmt.setInt(2, sellerId);
            updateStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thank you for rating the seller!");
            loadPageContent(); 
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error submitting seller rating: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void checkIfAlreadyReviewed() {
        if (actionTabPane == null) return; 
        String query = "SELECT COUNT(*) FROM review WHERE productID = ? AND customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productID);
            stmt.setInt(2, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                actionTabPane.setEnabledAt(0, false);
                actionTabPane.setTitleAt(0, "Review (Submitted)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}