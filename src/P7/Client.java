package P7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BillService billService = (BillService) registry.lookup("BillService");
            
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("\n=== Electric Bill System ===");
                System.out.println("1. View All Bills");
                System.out.println("2. Search Bill by Consumer ID");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        System.out.println("\n--- All Bills ---");
                        List<Bill> bills = billService.getAllBills();
                        if (bills.isEmpty()) {
                            System.out.println("No bills found.");
                        } else {
                            for (Bill bill : bills) {
                                System.out.println(bill);
                            }
                        }
                        break;
                        
                    case 2:
                        System.out.print("Enter Consumer ID: ");
                        int consumerId = scanner.nextInt();
                        Bill bill = billService.getBillById(consumerId);
                        if (bill != null) {
                            System.out.println("\nBill Found:");
                            System.out.println(bill);
                        } else {
                            System.out.println("Bill not found.");
                        }
                        break;
                        
                    case 3:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        
                    default:
                        System.out.println("Invalid choice.");
                }
            }
            
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
