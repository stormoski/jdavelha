package main;

import client.FrmJogo;
import server.FrmServer;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnIniciar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        rbtClient = new javax.swing.JRadioButton();
        rbtServer = new javax.swing.JRadioButton();
        lblHost = new javax.swing.JLabel();
        txtHost = new javax.swing.JTextField();
        chbLocal = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(".:: Iniciar");

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Escolha o modo de inicialização"));

        buttonGroup1.add(rbtClient);
        rbtClient.setText("Client");
        rbtClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtClientActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtServer);
        rbtServer.setSelected(true);
        rbtServer.setText("Server");
        rbtServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtServerActionPerformed(evt);
            }
        });

        lblHost.setText("Host");

        txtHost.setEnabled(false);

        chbLocal.setText("Local");
        chbLocal.setEnabled(false);
        chbLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbLocalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtClient)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(lblHost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chbLocal))
                    .addComponent(rbtServer))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addComponent(rbtServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                .addComponent(rbtClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHost)
                    .addComponent(chbLocal))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnIniciar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        txtHost.setText(null);
        txtHost.requestFocus();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        this.setVisible(false);
        
        if(rbtClient.isSelected()) {
            final FrmJogo janela = new FrmJogo();
            janela.setVisible(true);
            new Thread(new Runnable() {
                public void run() {
                    janela.iniciar(txtHost.getText());
                }
            }).start();
        } else {
            final FrmServer janela = new FrmServer();
            janela.setVisible(true);
            new Thread(new Runnable() {
                public void run() {
                    janela.iniciar();
                }
            }).start();
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void chbLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbLocalActionPerformed
        if(chbLocal.isSelected()) {
            txtHost.setText("localhost");
            txtHost.setEnabled(false);
            btnIniciar.requestFocus();
        } else {
            txtHost.setEnabled(true);
            txtHost.requestFocus();
        }
    }//GEN-LAST:event_chbLocalActionPerformed

    private void rbtServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtServerActionPerformed
        if(rbtServer.isSelected()) {
            txtHost.setText(null);
            txtHost.setEnabled(false);
            chbLocal.setEnabled(false);
            btnIniciar.requestFocus();
        }
    }//GEN-LAST:event_rbtServerActionPerformed

    private void rbtClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtClientActionPerformed
        if(rbtClient.isSelected()) {
            txtHost.setEnabled(true);
            chbLocal.setEnabled(true);
            txtHost.requestFocus();
        }
    }//GEN-LAST:event_rbtClientActionPerformed

    public static void main(String args[]) {
        Main m = new Main();
        m.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnIniciar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chbLocal;
    private javax.swing.JLabel lblHost;
    private javax.swing.JPanel panel;
    private javax.swing.JRadioButton rbtClient;
    private javax.swing.JRadioButton rbtServer;
    private javax.swing.JTextField txtHost;
    // End of variables declaration//GEN-END:variables

}
