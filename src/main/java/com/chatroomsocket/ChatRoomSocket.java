/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.chatroomsocket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomSocket extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton privateButton;
    private DefaultListModel<String> clientListModel;
    private JList<String> clientList;
    private List<ClientInfo> clients;

    public ChatRoomSocket() {
        setTitle("Chat Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        privateButton = new JButton("Send Private");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        privateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendPrivateMessage();
            }
        });
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        inputPanel.add(privateButton, BorderLayout.WEST);
        add(inputPanel, BorderLayout.SOUTH);

        clientListModel = new DefaultListModel<>();
        clientList = new JList<>(clientListModel);
        add(new JScrollPane(clientList), BorderLayout.EAST);

        clients = new ArrayList<>();

        setSize(400, 300);
        setLocationRelativeTo(null);

        startServer();

        setVisible(true);
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            appendMessage("Server started on port 9999...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                appendMessage("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void appendMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatArea.append(message + "\n");
            }
        });
    }

    private void sendMessage() {
        String message = inputField.getText();
        broadcastMessage("Server", message);
        inputField.setText("");
    }

    private void sendPrivateMessage() {
        String selectedClient = clientList.getSelectedValue();
        if (selectedClient != null) {
            String message = inputField.getText();
            sendPrivateMessageToClient(selectedClient, message);
            inputField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No client selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void broadcastMessage(String sender, String message) {
        appendMessage(sender + ": " + message);
        for (ClientInfo client : clients) {
            sendMessageToClient(client.getSocket(), sender + ": " + message);
        }
    }

    private void sendPrivateMessageToClient(String clientName, String message) {
        appendMessage("Private message to " + clientName + ": " + message);
        for (ClientInfo client : clients) {
            if (client.getName().equals(clientName)) {
                sendMessageToClient(client.getSocket(), "Private message from " + client.getName() + ": " + message);
                break;
            }
        }
    }

    private void sendMessageToClient(Socket clientSocket, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientName = in.readLine();
                appendMessage("Client registered: " + clientName);

                clients.add(new ClientInfo(clientName, clientSocket));
                updateClientList();

                String message;
                while ((message = in.readLine()) != null) {
                    broadcastMessage(clientName, message);
                }

                in.close();
                clientSocket.close();

                clients.removeIf(client -> client.getName().equals(clientName));
                updateClientList();
                appendMessage("Client disconnected: " + clientName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void updateClientList() {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    clientListModel.clear();
                    for (ClientInfo client : clients) {
                        clientListModel.addElement(client.getName());
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChatRoomSocket server = new ChatRoomSocket();
                server.pack();
            }
        });
    }
}

class ClientInfo {
    private String name;
    private Socket socket;

    public ClientInfo(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }
}
