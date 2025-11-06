package P5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Get registry reference
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object
            EquationSolver eq = (EquationSolver) registry.lookup("Calculator");

            // Create a scanner to read user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== Calculator Client ===");
            System.out.println("Enter an equation (e.g., '10 + 5') or type 'exit' to quit.");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();

                // Check if the user wants to exit
                if ("exit".equalsIgnoreCase(input.trim())) {
                    break;
                }

                try {
                    // Call the remote method and print the result
                    double result = eq.solve(input);
                    System.out.println("Equation: " + input + " = " + result);
                } catch (Exception e) {
                    System.err.println("Error solving equation: " + e.getMessage());
                }
            }

            System.out.println("Client shutting down.");
            scanner.close();

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
