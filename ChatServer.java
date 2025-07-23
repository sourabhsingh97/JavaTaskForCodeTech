import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234;
    private static Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
