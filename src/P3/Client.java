package P3;

import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        // Create a DatagramSocket for sending and receiving
        DatagramSocket clientSocket = new DatagramSocket();
        
        // Server details
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 1234;
        
        // Request data (can be empty or minimal)
        byte[] sendData = new byte[100];
        
        // Create and send request packet to server
        DatagramPacket sendPacket = new DatagramPacket(
            sendData, 
            sendData.length, 
            serverAddress, 
            serverPort
        );
        
        clientSocket.send(sendPacket);
        System.out.println("Request sent to server...");
        
        // Create packet to receive response
        byte[] receiveData = new byte[100];
        DatagramPacket receivePacket = new DatagramPacket(
            receiveData, 
            receiveData.length
        );
        
        // Wait for server response
        clientSocket.receive(receivePacket);
        
        // Extract and display the date/time
        String serverDateTime = new String(
            receivePacket.getData(), 
            0, 
            receivePacket.getLength()
        );
        
        System.out.println("Server DateTime: " + serverDateTime);
        
        // Close the socket
        clientSocket.close();
    }
}

