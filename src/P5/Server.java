package P5;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Server extends UnicastRemoteObject implements EquationSolver {
    
    public Server() throws RemoteException {
        super();
    }
    
    @Override
    public double solve(String equation) throws RemoteException {
        try {
            System.out.println("Server received equation: " + equation);
            double result = evaluateExpression(equation);
            System.out.println("Server calculated result: " + result);
            return result;
        } catch (Exception e) {
            throw new RemoteException("Error solving equation: " + e.getMessage());
        }
    }
    
    private double evaluateExpression(String expr) {
        // Remove spaces
        expr = expr.replaceAll("\\s+", "");
        
        // Simple evaluation for basic operations
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
        }
        
        if (expr.contains("-")) {
            String[] parts = expr.split("-");
            if (parts.length == 2 && !parts[0].isEmpty()) {
                return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
            }
        }
        
        if (expr.contains("*")) {
            String[] parts = expr.split("\\*");
            return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
        }
        
        if (expr.contains("/")) {
            String[] parts = expr.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        }
        
        return Double.parseDouble(expr);
    }
    
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            
            // Create and start RMI registry programmatically
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry created on port 1099");
            
            Server calc = new Server();
            Naming.rebind("Calculator", calc);
            
            System.out.println("Calculator Server is ready and waiting for clients...");
        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }
}
