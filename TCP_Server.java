import java.io.*;
import java.net.*;

public class TCP_Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            // Create a server socket listening on port 9876
            serverSocket = new ServerSocket(9876);
            System.out.println("Server started. Waiting for a client...");
            // Accept client connection
            clientSocket = serverSocket.accept();

            System.out.println("Client connected!");
            // Input and output streams for client communication
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            // Read the file name requested by the client
            String fileName = inFromClient.readLine();
            System.out.println("Client requested file: " + fileName);
            // Attempt to open the requested file
            File file = new File(fileName);
            if (file.exists() && !file.isDirectory()) {
                // If the file exists, send back the file contents
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                outToClient.println("FILE_FOUND");
                while ((line = fileReader.readLine()) != null) {
                    outToClient.println(line);
                }
                fileReader.close();
                System.out.println("File sent successfully.");
            } else {
                // If the file doesn't exist, inform the client
                outToClient.println("FILE_NOT_FOUND");
                System.out.println("Requested file not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the connections
            try {
                if (clientSocket != null)
                    clientSocket.close();
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}