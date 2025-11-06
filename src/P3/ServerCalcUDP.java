package P3;

import java.io.*;
import java.net.*;

public class ServerCalcUDP {
    public static void main(String[] args) throws IOException {
        // Creating DatagramSocket on port 6666
        DatagramSocket socket = new DatagramSocket(6666);
        System.out.println("Server is running...");
        
        byte[] strm = null;
        DatagramPacket packtrec = null;
        DatagramPacket packtsend = null;
        
        try {
            while (true) {
                // Creating byte array to receive data
                strm = new byte[256];
                
                // Creating DatagramPacket to receive data from client
                packtrec = new DatagramPacket(strm, strm.length);
                
                // Receiving packet from client
                socket.receive(packtrec);
                
                // Converting received bytes to string
                String receivedData = new String(strm, 0, packtrec.getLength()).trim();
                System.out.println("Received from client: " + receivedData);
                
                // Parsing the input format: "num1 operator num2"
                String[] parts = receivedData.split(" ");
                
                int tot = 0;
                String res = "";
                
                try {
                    if (parts.length == 3) {
                        int data1 = Integer.parseInt(parts[0]);
                        char opt = parts[1].charAt(0);
                        int data2 = Integer.parseInt(parts[2]);
                        
                        // Performing calculation based on operation
                        switch(opt) {
                            case '+':
                                tot = data1 + data2;
                                res = data1 + " + " + data2 + " = " + tot;
                                break;
                            case '-':
                                tot = data1 - data2;
                                res = data1 + " - " + data2 + " = " + tot;
                                break;
                            case '*':
                                tot = data1 * data2;
                                res = data1 + " * " + data2 + " = " + tot;
                                break;
                            case '/':
                                if (data2 != 0) {
                                    tot = data1 / data2;
                                    res = data1 + " / " + data2 + " = " + tot;
                                } else {
                                    res = "Error: Division by zero!";
                                }
                                break;
                            default:
                                res = "Invalid operator! Use +, -, *, /";
                                break;
                        }
                    } else {
                        res = "Invalid format! Use: number1 operator number2";
                    }
                } catch (NumberFormatException e) {
                    res = "Error: Invalid number format!";
                }
                
                // Converting result to bytes
                strm = res.getBytes();
                
                // Getting client's port and IP address
                int port = packtrec.getPort();
                InetAddress clientAddr = packtrec.getAddress();
                
                // Creating DatagramPacket to send result back to client
                packtsend = new DatagramPacket(strm, strm.length, clientAddr, port);
                
                // Sending packet to client
                socket.send(packtsend);
                System.out.println("Sent to client: " + res + "\n");
            }
        } catch(Exception exp) {
            System.out.println(exp);
        }
    }
}

