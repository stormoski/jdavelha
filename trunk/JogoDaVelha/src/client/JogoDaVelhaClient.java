package client;

import java.net.Socket;
import eventos.OuvinteStatus;
import eventos.Status;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class JogoDaVelhaClient {

    private Socket cliente;
    private BufferedReader entrada;
    private BufferedWriter saida;
    private Status status;
    private List<OuvinteStatus> ouvintes;
    private boolean acabouJogo;

    public JogoDaVelhaClient() {
        ouvintes = new ArrayList<OuvinteStatus>();
    }

    public void iniciar(){
        try{
            cliente = new Socket("localhost", 1234);
            saida = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            saida.flush();
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            this.leitor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            this.liberarRecursos();
        }
    }

    private void liberarRecursos(){
        try{
            saida.close();
            entrada.close();
            cliente.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void leitor(){
        try{
            //acabouJogo = entrada.readLine().contains("acabou");

            while(!acabouJogo()){
                String[] mensagem = entrada.readLine().split("|");

                if(mensagem[0].equals("statusGeral")){
                    atualizarStatusGeral(mensagem[1]);
                } else if(mensagem[0].equals("posicoes")){
                    String[] posicoes = mensagem[1].split(",");
                    status.setPosicoes(posicoes);
                    fireMudouStatusJogo();
                } else if(mensagem[0].equals("jogadorCorrente")){
                    status.setJogadorCorrente(mensagem[1]);
                    fireMudouStatusJogo();
                } else if(mensagem[0].equals("iniciarJogo")){
                    fireComecouJogo();
                } else if(mensagem[0].equals("jogar")){
                    obterRespostaJogada(mensagem[1]);
                }
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void atualizarStatusGeral(String statusGeral){
        if(statusGeral.equals("conectado")){
            this.fireAtualizarMensagem("Você está conectado no server");
        } else if(statusGeral.equals("acabou")){
            this.fireAcabouJogo();
        }
    }

    private void obterRespostaJogada(String resposta){
        if(resposta.equals("sucesso")){
        } else if(resposta.equals("posicaoOcupada")){
            this.firePosicaoOcupada();
        }
    }

    public void jogar(Integer posicao) throws IOException {
        saida.write("jogar|" + posicao);
    }

    public void addOuvinteStatus(OuvinteStatus ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void removeOuvinteStatus(OuvinteStatus ouvinte) {
        ouvintes.remove(ouvinte);
    }

    private boolean acabouJogo() {
        return this.acabouJogo;
    }

    private void fireMudouStatusJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.mudouEstadoJogo(status);
        }
    }

    private void fireComecouJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.comecouJogo(status);
        }
    }

    private void fireAcabouJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.acabouJogo(status);
        }
    }

    private void firePosicaoOcupada(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.posicaoOcupada(status);
        }
    }

    private void fireAtualizarMensagem(String msg){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.atualizarMensagem(msg);
        }
    }
}