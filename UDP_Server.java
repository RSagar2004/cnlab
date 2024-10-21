import java.net.*;
import java.io.*;

public class UDP_Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(9876); // Server listens on port 9876
        byte[] receiveBuffer = new byte[1024];
        byte[] sendBuffer;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Server started. Waiting for client messages...");

        while (true) {
            // Receiving data from client
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(receivePacket); // Block until data is received
            String clientMessage = new String(receivePacket.getData(), 0,
                    receivePacket.getLength());
            System.out.println("Client: " + clientMessage);
            // Server typing and sending response
            System.out.print("Server: ");

            String serverMessage = reader.readLine();
            sendBuffer = serverMessage.getBytes();
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                    clientAddress, clientPort);
            serverSocket.send(sendPacket); // Send the message to client

            // Exit on typing "exit"
            if (serverMessage.equalsIgnoreCase("exit")) {
                System.out.println("Server exited.");
                break;
            }
        }
        serverSocket.close();
    }
}