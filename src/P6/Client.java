package P6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {
        try {
            // Get registry reference
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Look up the remote object
            LibraryService library = (LibraryService) registry.lookup("LibraryService");
            
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("\n=== Library Management System ===");
                System.out.println("1. View All Books");
                System.out.println("2. Search Book by ID");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        // Get all books
                        System.out.println("\n--- All Books in Library ---");
                        List<Book> books = library.getAllBooks();
                        if (books.isEmpty()) {
                            System.out.println("No books found in the library.");
                        } else {
                            for (Book book : books) {
                                System.out.println(book);
                            }
                        }
                        break;
                        
                    case 2:
                        // Search book by ID
                        System.out.print("Enter Book ID: ");
                        int bookId = scanner.nextInt();
                        Book book = library.getBookById(bookId);
                        if (book != null) {
                            System.out.println("\nBook Found:");
                            System.out.println(book);
                        } else {
                            System.out.println("Book with ID " + bookId + " not found.");
                        }
                        break;
                        
                    case 3:
                        System.out.println("Exiting... Goodbye!");
                        scanner.close();
                        System.exit(0);
                        
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

