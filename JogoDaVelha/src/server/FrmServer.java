package server;

import eventos.OuvinteStatusServer;
import eventos.Status;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class FrmServer extends javax.swing.JFrame {
    private JogoDaVelhaServer server;

    public FrmServer() {
        initComponents();
        server = new JogoDaVelhaServer();
    }

    private void iniciar() {
        server.addOuvinteStatus(new OuvinteStatusServer() {
            public void mudouEstadoJogo(Status statusJogo) {
                String msg = ">> " + statusJogo.getJogadorCorrente() + " jogou na posição " + 
                        statusJogo.getPosicaoPressionada() + "\n>> Posições:\n";
                
                for(int i = 1; i <= 9; i++){
                    msg += i % 3 == 0 ? statusJogo.getPosicao(i - 1) : "\n";
                }

                atualizarMensagem(msg.subSequence(0, msg.length() - 1).toString());
            }

            public void comecouJogo(Status statusJogo) {
                
            }

            public void acabouJogo(Status statusJogo) {
                
            }

            public void posicaoOcupada(Status statusJogo) {
                
            }

            public void novaConexao(String jogador, String ip) {
                atualizarMensagem(">> O jogador " + jogador + ", IP " + ip + " conectou.");
                ((DefaultListModel)lstJogadores.getModel()).addElement("");
            }

            public void desconectou(String jogador, String ip) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void erro(String mensagem) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void atualizarMensagem(String msg) {
                txtLog.append(msg);
                txtLog.append("\n");
            }
        });

        server.iniciar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollTxt = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        scrollList = new javax.swing.JScrollPane();
        lstJogadores = new javax.swing.JList();
        btnKick = new javax.swing.JButton();
        btnEncerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(".:: Server");

        txtLog.setColumns(20);
        txtLog.setRows(5);
        scrollTxt.setViewportView(txtLog);

        scrollList.setViewportView(lstJogadores);

        btnKick.setText("Kickar Jogador");

        btnEncerrar.setText("Encerrar Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollList, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnKick)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                        .addComponent(btnEncerrar)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollList, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEncerrar)
                    .addComponent(btnKick))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FrmServer janela = new FrmServer();
        janela.setVisible(true);
        janela.iniciar();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEncerrar;
    private javax.swing.JButton btnKick;
    private javax.swing.JList lstJogadores;
    private javax.swing.JScrollPane scrollList;
    private javax.swing.JScrollPane scrollTxt;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

}
