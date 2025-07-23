import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Set<ClientHandler> clientHandlers;
    private String clientName;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) throws IOException {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            out.println("Enter your name:");
            clientName = in.readLine();
            broadcast(clientName + " joined the chat");

            String message;
            while ((message = in.readLine()) != null) {
                broadcast(clientName + ": " + message);
            }

        } catch (IOException e) {
            System.out.println(clientName + " disconnected");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientHandlers.remove(this);
            broadcast(clientName + " left the chat");
        }
    }

    private void broadcast(String message) {
        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                client.out.println(message);
            }
        }
    }
}
