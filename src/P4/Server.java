package P4;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ProductService {
    private static final long serialVersionUID = 1L;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/productdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";

    public Server() throws RemoteException {
        super();
    }

    @Override
    public List<Product> getProducts() throws RemoteException {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all products: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductById(int id) throws RemoteException {
        Product product = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE id = ?")) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                product = new Product(id, name, price);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return product;
    }

    public static void main(String[] args) {
        try {
            // Create RMI registry on port 8081
            Registry registry = LocateRegistry.createRegistry(8081);
            System.out.println("RMI registry started on port 8081.");

            // Create instance of server
            Server server = new Server();
            
            // Bind the service to the registry
            registry.rebind("ProductService", server);
            System.out.println("ProductServer is running...");
            System.out.println("Waiting for client requests...");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
