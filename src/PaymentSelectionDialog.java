import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentSelectionDialog extends JDialog {
    private JList<String> paymentList;
    private String selectedCardNumber = null;

    public PaymentSelectionDialog(Frame owner, Connection connection, int customerID) {
        super(owner, "Select Payment Method", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        paymentList = new JList<>(listModel);
        paymentList.setFont(new Font("Arial", Font.PLAIN, 14));
        
        add(new JScrollPane(paymentList), BorderLayout.CENTER);

        JButton selectButton = new JButton("Select This Card");
        selectButton.addActionListener(e -> {
            String selection = paymentList.getSelectedValue();
            if (selection != null) {
                selectedCardNumber = selection.split("\\(\\*\\*\\*\\* ")[1].replace(")", "");
            }
            dispose();
        });

        add(selectButton, BorderLayout.SOUTH);

        try {
            String query = "SELECT cardNumber, cardName FROM payment_info WHERE customerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String cardNumber = rs.getString("cardNumber");
                String cardName = rs.getString("cardName");
                String masked = "**** **** **** " + (cardNumber.length() > 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber);
                listModel.addElement(String.format("%s (**** %s)", cardName, masked.substring(masked.length() - 4)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading payment methods: " + e.getMessage());
        }
    }

    public String getSelectedCardNumber() {
        return selectedCardNumber;
    }
}