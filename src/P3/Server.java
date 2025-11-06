package P3;

import java.net.*;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws Exception {
        // Create a DatagramSocket bound to port 1234
        DatagramSocket serverSocket = new DatagramSocket(1234);
        byte[] receiveData = new byte[100];
        
        System.out.println("DateTime RPC Server started on port 1234...");
        
        while (true) {
            // Create a DatagramPacket to receive incoming requests
            DatagramPacket receivePacket = new DatagramPacket(
                receiveData, 
                receiveData.length
            );
            
            // Wait for a client request
            serverSocket.receive(receivePacket);
            
            // Extract client's address and port
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            
            // Get current system date and time
            Date currentDateTime = new Date();
            String dateTimeString = currentDateTime.toString();
            
            // Convert string to bytes for transmission
            byte[] sendData = dateTimeString.getBytes();
            
            // Create a response packet directed to the client
            DatagramPacket sendPacket = new DatagramPacket(
                sendData, 
                sendData.length, 
                clientAddress, 
                clientPort
            );
            
            // Send the response
            serverSocket.send(sendPacket);
            
            System.out.println("Sent DateTime to " + clientAddress + 
                             ":" + clientPort);
        }
    }
}

