package P3;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientCalcUDP {
    public static void main(String[] args) throws IOException {
        Scanner inp = new Scanner(System.in);
        
        // Creating DatagramSocket for UDP connection
        DatagramSocket datagsokt = new DatagramSocket();
        
        // Fetching localhost address
        InetAddress addr = InetAddress.getLocalHost();
        
        byte[] strm = null;
        
        try {
            while (true) {
                System.out.println("\n========== Calculator Menu ==========");
                System.out.println("Enter calculation in format: number1 operator number2");
                System.out.println("Operators: + (Addition), - (Subtraction), * (Multiplication), / (Division)");
                System.out.println("Type 'exit' to quit");
                System.out.println("=====================================");
                System.out.print("Enter your calculation: ");
                
                String input = inp.nextLine();
                
                // Exit if user enters exit
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    break;
                }
                
                // Converting string input to bytes
                strm = input.getBytes();
                
                // Creating DatagramPacket to send request to server
                DatagramPacket packtsend = new DatagramPacket(strm, strm.length, addr, 6666);
                
                // Sending packet to server
                datagsokt.send(packtsend);
                
                // Creating byte array to receive result
                strm = new byte[256];
                DatagramPacket packtrec = new DatagramPacket(strm, strm.length);
                
                // Receiving result from server
                datagsokt.receive(packtrec);
                
                // Displaying the result
                String result = new String(strm, 0, packtrec.getLength()).trim();
                System.out.println("Result: " + result);
            }
        } catch(Exception exp) {
            System.out.println("Error: " + exp.getMessage());
        } finally {
            datagsokt.close();
            inp.close();
            System.out.println("Connection closed.");
        }
    }
}

