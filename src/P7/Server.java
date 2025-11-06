package P7;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements BillService {
    private static final long serialVersionUID = 1L;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Electric_Bill";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";

    public Server() throws RemoteException {
        super();
    }

    @Override
    public List<Bill> getAllBills() throws RemoteException {
        List<Bill> bills = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Bill")) {
            
            while (rs.next()) {
                int id = rs.getInt("consumer_id");
                String name = rs.getString("consumer_name");
                String dueDate = rs.getString("bill_due_date");
                double amount = rs.getDouble("bill_amount");
                bills.add(new Bill(id, name, dueDate, amount));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all bills: " + e.getMessage());
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public Bill getBillById(int consumerId) throws RemoteException {
        Bill bill = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Bill WHERE consumer_id = ?")) {
            
            stmt.setInt(1, consumerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("consumer_name");
                String dueDate = rs.getString("bill_due_date");
                double amount = rs.getDouble("bill_amount");
                bill = new Bill(consumerId, name, dueDate, amount);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bill by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return bill;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099.");

            Server server = new Server();
            
            registry.rebind("BillService", server);
            System.out.println("BillServer is running...");
            System.out.println("Waiting for client requests...");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

