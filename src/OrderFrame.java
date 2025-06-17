import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderFrame extends JFrame {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private int customerID;
    private Connection connection;

    public OrderFrame(int customerID) {
        this.customerID = customerID;
        connectToDatabase();
        initializeComponents();
        loadOrderData();
    }

    
    OrderFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database", "root", "12345678");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void initializeComponents() {
        setTitle("My Orders"); 
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
       
        
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Order ID", "Order Date", "Shipping Address"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        add(scrollPane, BorderLayout.CENTER);
        
       
    }

    private void loadOrderData() {
        try {
            tableModel.setRowCount(0); 
            String query = "SELECT orderID, orderDate, address_name FROM orders WHERE customerID = ? ORDER BY orderDate DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("orderID"),
                    rs.getDate("orderDate"),
                    rs.getString("address_name")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage());
        }
    }
}