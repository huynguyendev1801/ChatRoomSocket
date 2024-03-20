/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.chatroomsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Client extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    //Tạo socket từ client
    private Socket clientSocket;
    //Tạo thuộc tính để đọc dữ liệu
    private BufferedReader in;
    //Tạo thuộc tính để ghi dữ liệu
    private PrintWriter out;
    public Client() {
        initComponents();
    }
    //Phương thức kết nối đến server
private void connectToServer() {
       try {
            //Hiển thị hộp thoại nhập địa chỉ IP của Server
            String serverAddress = JOptionPane.showInputDialog(this, "Nhập địa chỉ IP của máy chủ:", "Server Address",
                    JOptionPane.QUESTION_MESSAGE);
            //Khởi tạo socket với cổng 8888 và serverAddress
            clientSocket = new Socket(serverAddress, 8888);
            appendMessage("Đã kết nối đến Server: " + serverAddress);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            //Hiển thị hộp thoại nhập tên của client
            String clientName = JOptionPane.showInputDialog(this, "Nhập tên của bạn:", "Client Name",
                    JOptionPane.QUESTION_MESSAGE);
            //Gửi tên client đến server
            out.println(clientName);
            //Khởi chạy luồn messege
            Thread messageThread = new Thread(new MessageReceiver());
            messageThread.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //Phương thức hiển thị tin nhắn lên khung chat
    private void appendMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatArea.append(message + "\n");
            }
        });
    }
    //Phương thức gửi tin nhắn
    private void sendMessage(String message) {
        out.println(message);
    }
    //class để lấy tin nhắn từ server
    private class MessageReceiver implements Runnable {
        public void run() {
            try {
                String message;
                //Kiểm tra nếu stream không trống thì hiển thị tin nhắn
                while ((message = in.readLine()) != null) {
                    appendMessage(message);
                }
                in.close();
                out.close();
                clientSocket.close();
                appendMessage("Ngắt kết nối từ Server");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        txtMessage = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        btnDongKetNoi = new javax.swing.JButton();
        btnSendPrivate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnKetNoi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(68, 71, 90));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(248, 248, 242));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Client");

        chatArea.setColumns(20);
        chatArea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        txtMessage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnSend.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSend.setText("Gửi");
        btnSend.setEnabled(false);
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        btnDongKetNoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDongKetNoi.setText("Đóng kết nối");
        btnDongKetNoi.setEnabled(false);
        btnDongKetNoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongKetNoiActionPerformed(evt);
            }
        });

        btnSendPrivate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSendPrivate.setText("Gửi riêng");
        btnSendPrivate.setEnabled(false);
        btnSendPrivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendPrivateActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(248, 248, 242));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(248, 248, 242));
        jLabel2.setText("Tin nhắn:");

        jLabel3.setBackground(new java.awt.Color(248, 248, 242));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(248, 248, 242));
        jLabel3.setText("Tin nhắn chung:");

        btnKetNoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKetNoi.setText("Kết nối");
        btnKetNoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetNoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnDongKetNoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnKetNoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSendPrivate)
                        .addGap(26, 26, 26)
                        .addComponent(btnSend))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMessage))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend)
                    .addComponent(btnDongKetNoi)
                    .addComponent(btnSendPrivate)
                    .addComponent(btnKetNoi))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//Nút đóng kết nối
    private void btnDongKetNoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongKetNoiActionPerformed
        // TODO add your handling code here:
 try {
        // Đóng các luồng và socket
        in.close();
        out.close();
        clientSocket.close();
        //Hiển thị thông điệp ngắt kết nối
        appendMessage("Ngắt kết nối từ Server");
        
        // Vô hiệu hóa các nút Gửi và Gửi riêng
        btnSend.setEnabled(false);
        btnSendPrivate.setEnabled(false);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
 //Tắt vô hiệu hóa nút kết nối
        this.btnKetNoi.setEnabled(true);
        //Vô hiệu hóa nút đóng kết nối
        this.btnDongKetNoi.setEnabled(false);
    }//GEN-LAST:event_btnDongKetNoiActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        //Nút gửi tin nhắn
         String message = txtMessage.getText();
        sendMessage(message);
          this.txtMessage.setText("");
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnSendPrivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendPrivateActionPerformed
        // TODO add your handling code here:
         String message = txtMessage.getText();
         //Nút gửi tin nhắn riêng, thêm prefix @ để xử lý từ phía server nhận biết đây là tin nhắn riêng
        sendMessage("@" + message);
        this.txtMessage.setText("");
    }//GEN-LAST:event_btnSendPrivateActionPerformed

    private void btnKetNoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetNoiActionPerformed
        // TODO add your handling code here:
        //Nút kết nối đến server, gọi hàm kết nối
        connectToServer();
        //Tắt vô hiệu hóa nút gửi
        this.btnSend.setEnabled(true);
        //Tắt vô hiệu hóa nút gửi riêng
        this.btnSendPrivate.setEnabled(true);
        //Tắt vô hiệu hóa nút đóng kết nối
        this.btnDongKetNoi.setEnabled(true);
        //Vô hiệu hóa nút kết nối
        this.btnKetNoi.setEnabled(false);
    }//GEN-LAST:event_btnKetNoiActionPerformed

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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDongKetNoi;
    private javax.swing.JButton btnKetNoi;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendPrivate;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}
