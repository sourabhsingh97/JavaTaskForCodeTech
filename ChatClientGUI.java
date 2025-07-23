import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI extends JFrame {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;

    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private String userName;

    public ChatClientGUI() {
        setTitle("Java Chat - Client");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);

        inputField = new JTextField();
        inputField.addActionListener(e -> sendMessage());

        add(scroll, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        connectToServer();
        setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            userName = JOptionPane.showInputDialog(this, "Enter your name:");
            out.println(userName);

            // Receive messages from server
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("Disconnected from server.\n");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}
