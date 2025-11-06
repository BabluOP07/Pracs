package P4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static ProductService productService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Connect to the RMI registry on port 8081
            Registry registry = LocateRegistry.getRegistry("localhost", 8081);

            // Look up the remote service
            productService = (ProductService) registry.lookup("ProductService");

            System.out.println("Connected to ProductService!");
            System.out.println("\n========== Product Database Client ==========\n");

            boolean running = true;
            while (running) {
                System.out.println("\n1. Display all products");
                System.out.println("2. Search by product ID");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        displayAllProducts();
                        break;
                    case 2:
                        searchProductById(scanner);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Disconnecting...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Client Exception: " + e.toString());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void displayAllProducts() {
        try {
            List<Product> products = productService.getProducts();
            if (products.isEmpty()) {
                System.out.println("No products found!");
            } else {
                System.out.println("\n========== All Products ==========");
                for (Product product : products) {
                    System.out.println(product);
                }
                System.out.println("==================================");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void searchProductById(Scanner scanner) {
        try {
            System.out.print("Enter Product ID: ");
            int id = scanner.nextInt();

            Product product = productService.getProductById(id);
            if (product != null) {
                System.out.println("\n========== Product Found ==========");
                System.out.println(product);
                System.out.println("==================================");
            } else {
                System.out.println("Product not found!");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
