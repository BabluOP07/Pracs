package P2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(5000);
        System.out.println("Server started on port 5000...\n");
        
        byte[] receiveBuffer = new byte[1024];
        
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            
            String equation = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + equation);
            
            double result = evaluate(equation);
            System.out.println("Result: " + result + "\n");
            
            String response = String.valueOf(result);
            byte[] sendBuffer = response.getBytes();
            
            DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer, 
                sendBuffer.length, 
                receivePacket.getAddress(), 
                receivePacket.getPort()
            );
            
            socket.send(sendPacket);
        }
    }
    
    private static double evaluate(String expr) {
        expr = expr.replaceAll("\\s+", "");
        
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
        }
        if (expr.contains("-")) {
            String[] parts = expr.split("-");
            return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
        }
        if (expr.contains("*")) {
            String[] parts = expr.split("\\*");
            return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
        }
        if (expr.contains("/")) {
            String[] parts = expr.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        }
        return 0;
    }
}

