package P2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== UDP Calculator ===\n");
        
        while (true) {
            System.out.print("Enter equation (or 'exit'): ");
            String input = scanner.nextLine();
            
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            
            byte[] sendBuffer = input.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer, 
                sendBuffer.length, 
                serverAddress, 
                5000
            );
            
            socket.send(sendPacket);
            
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            
            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Result: " + result + "\n");
        }
        
        socket.close();
        scanner.close();
    }
}

