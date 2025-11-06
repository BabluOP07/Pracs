package P6;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements LibraryService {
    private static final long serialVersionUID = 1L;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Library";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";

    public Server() throws RemoteException {
        super();
    }

    @Override
    public List<Book> getAllBooks() throws RemoteException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Book")) {
            
            while (rs.next()) {
                int id = rs.getInt("Book_id");
                String name = rs.getString("Book_name");
                String author = rs.getString("Book_author");
                books.add(new Book(id, name, author));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all books: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(int bookId) throws RemoteException {
        Book book = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE Book_id = ?")) {
            
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("Book_name");
                String author = rs.getString("Book_author");
                book = new Book(bookId, name, author);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching book by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return book;
    }


    public static void main(String[] args) {
        try {
            // Create RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099.");

            // Create instance of server
            Server server = new Server();
            
            // Bind the service to the registry
            registry.rebind("LibraryService", server);
            System.out.println("LibraryServer is running...");
            System.out.println("Waiting for client requests...");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
