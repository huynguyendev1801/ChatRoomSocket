/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.chatroomsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;


public class Server extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    //Tạo model cho JList kiểu String để lưu trữ danh sách client và hiển thị lên JList
    private DefaultListModel<String> clientListModel;
    //Tạo danh sách đối tượng clients thuộc class ClientHandler
    private List<ClientHandler> clients;
    //Tạo class clientHandler để xử yêu cầu từ phía client
    class ClientHandler implements Runnable {
        //Tạo thuộc tính Socket
        private final Socket clientSocket;
        //Tạo thuộc tính lưu tên client
        private String clientName;
        //Tạo biến để đọc dữ liệu
        private BufferedReader in;
        //Tạo biến để ghi dữ liệu
        private PrintWriter out;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        public String getClientName() {
            return clientName;
        }
        public void sendMessage(String message) {
            out.println(message);
        }
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                //Đọc tên client
                clientName = in.readLine();
                appendMessage("Tên Client đã kết nối: " + clientName);
                //Cập nhật và hiển thị danh sách client
                updateClientList();
                String message;
                //Xử lý tin nhắn từ client
                while ((message = in.readLine()) != null) {
                    //Kiểu tra điều kiện nếu prefix là @ thì là tin nhắn riêng được cấu hình từ client
                    if (message.startsWith("@")) {
                        String privateMessage = message.replace("@", "");
                        sendPrivateMessage(clientName, privateMessage);
                    } else {
                        //Không phải tin nhắn riêng thì sẽ xử lý tin nhắn gửi chung từ client
                        broadcastMessage(clientName, message);
                    }
                }
                in.close();
                out.close();
                clientSocket.close();
                clients.remove(this);
                updateClientList();
                appendMessage("Client đã ngắt kết nối: " + clientName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
        private void startServer() {
        try {
            //Khởi tạo List clients
            clients = new ArrayList<>();
            //Khởi tạo clientModel để hiển thị lên JList
            clientListModel = new DefaultListModel<>();
            //Đặt cổng cho server là 8888 có thể thay đổi thành cổng khác
            ServerSocket serverSocket = new ServerSocket(8888);
            appendMessage("Server đã bắt đầu trên cổng 8888...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                appendMessage("Client: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                //Thêm client mới vào List clients
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                //khởi chạy luồng để xử lý cho client mới
                clientThread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        //Phương thức thêm tin nhắn vào khung chat
    private void appendMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatArea.append(message + "\n");
            }
        });
    }
    //Phương thức để quảng bá tin nhắn đến tất cả client trong List Clients
    private void broadcastMessage(String sender, String message) {
        appendMessage(sender + ": " + message);
        for (ClientHandler client : clients) {
            client.sendMessage(sender + ": " + message);
        }
    }
  //Xử lý tin nhắn gửi riêng từ Client
    private void sendPrivateMessage(String sender, String message) {
         appendMessage("Tin nhắn riêng từ "+ sender + ": " + message);
    }
      //Phương thức để gửi tin nhắn riêng từ server đến một client được chọn trên JList
private void sendPrivateMessageToClient(String sender, String recipient, String message) {
        appendMessage("Tin nhắn riêng từ " + sender + " to " + recipient + ": " + message);
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(recipient)) {
                client.sendMessage("Tin nhắn riêng từ " + sender + ": " + message);
                break;
            }
        }
    }
//Cập nhật JList client
    private void updateClientList() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                clientListModel.clear();
                for (ClientHandler client : clients) {
                    clientListModel.addElement(client.getClientName());
                }
                 listClient.setModel(clientListModel);
            }
        });
    }
    public Server() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listClient = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        btnStartServer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        txtMessage = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        btnSendPrivate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(68, 71, 90));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 250, 123)));

        listClient.setBackground(new java.awt.Color(98, 114, 164));
        listClient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 250, 123)));
        jScrollPane1.setViewportView(listClient);

        jPanel2.setBackground(new java.awt.Color(98, 114, 164));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 250, 123)));

        btnStartServer.setBackground(new java.awt.Color(139, 233, 253));
        btnStartServer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnStartServer.setText("Bắt đầu");
        btnStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnStartServer, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStartServer)
                .addGap(43, 43, 43))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(248, 248, 242));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Máy chủ");

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chatArea.setRows(5);
        jScrollPane2.setViewportView(chatArea);

        txtMessage.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnSend.setBackground(new java.awt.Color(139, 233, 253));
        btnSend.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSend.setText("Gửi");
        btnSend.setEnabled(false);
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        btnSendPrivate.setBackground(new java.awt.Color(139, 233, 253));
        btnSendPrivate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSendPrivate.setText("Gửi riêng");
        btnSendPrivate.setEnabled(false);
        btnSendPrivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendPrivateActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(248, 248, 242));
        jLabel2.setText("Danh sách Client:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(248, 248, 242));
        jLabel3.setText("Tin nhắn chung:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(248, 248, 242));
        jLabel4.setText("Chức năng");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(248, 248, 242));
        jLabel5.setText("Tin nhắn:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnSendPrivate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSend)
                            .addComponent(btnSendPrivate))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartServerActionPerformed
                //Vô hiệu hóa nút Start
                this.btnStartServer.setEnabled(false);
                //Tắt vô hiệu hóa nút gửi
                this.btnSend.setEnabled(true);
                //Tắt vô hiệu hóa nút gửi riêng
                this.btnSendPrivate.setEnabled(true);
                //Khởi chạy server trên một luồng riêng
                Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    startServer();
                }
            });
            serverThread.start();
    }//GEN-LAST:event_btnStartServerActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        //Gửi tin nhắn đến tất cả client
        broadcastMessage("Server", this.txtMessage.getText());
        this.txtMessage.setText("");
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnSendPrivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPrivateActionPerformed
        // TODO add your handling code here:
        //Gửi tin nhắn riêng
        String message = this.txtMessage.getText();
        String recipient = this.listClient.getSelectedValue();
                sendPrivateMessageToClient("Server", recipient, message);
        this.txtMessage.setText("");
    }//GEN-LAST:event_btnSendPrivateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendPrivate;
    private javax.swing.JButton btnStartServer;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listClient;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}
