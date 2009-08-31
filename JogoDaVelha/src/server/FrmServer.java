package server;

import eventos.OuvinteStatusServer;
import eventos.Status;

public class FrmServer extends javax.swing.JFrame {
    private JogoDaVelhaServer server;

    public FrmServer() {
        initComponents();
        server = new JogoDaVelhaServer();
    }

    public void iniciar() {
        server.addOuvinteStatus(new OuvinteStatusServer() {
            public void mudouEstadoJogo(Status statusJogo) {
                atualizarMensagem(statusJogo.getJogadorCorrente() + " jogou na posição " +
                        statusJogo.getPosicaoPressionada());

                String msg = "Posições:\n";
                
                for(int i = 0; i <= 8; i++){
                    if(i % 3 == 0 && i != 8 && i != 0){
                        msg += "\n";
                    }

                    msg += statusJogo.getPosicao(i).replace(" ", "-");
                }

                atualizarMensagem(msg);
            }

            public void conectou(String jogador, String ip) {
                atualizarMensagem("O jogador " + jogador + ", IP " + ip + " conectou.");
            }

            public void acabouJogo(Status statusJogo) {
                atualizarMensagem("O jogo acabou.");
            }

            public void posicaoOcupada(Status statusJogo) {
                atualizarMensagem("A posição " + statusJogo.getPosicaoPressionada() + " está ocupada.");
            }

            public void desconectou(String jogador, String ip) {
                atualizarMensagem("O jogador " + jogador + ", ip " + ip + ".");
            }

            public void erro(String mensagem) {
                atualizarMensagem("Erro: " + mensagem);
            }

            public void atualizarMensagem(String msg) {
                txtLog.append(">> " + msg);
                txtLog.append("\n");
                txtLog.setCaretPosition(txtLog.getText().length());
            }
        });

        server.iniciar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollTxt = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        btnEncerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(".:: Server");

        txtLog.setColumns(20);
        txtLog.setRows(5);
        scrollTxt.setViewportView(txtLog);

        btnEncerrar.setText("Encerrar Server");
        btnEncerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addComponent(btnEncerrar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEncerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEncerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncerrarActionPerformed
        //TODO: implementar
    }//GEN-LAST:event_btnEncerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEncerrar;
    private javax.swing.JScrollPane scrollTxt;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

}
