package client;

import java.net.Socket;
import eventos.OuvinteStatus;
import eventos.OuvinteStatusClient;
import eventos.Status;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class JogoDaVelhaClient {

    private Socket cliente;
    private BufferedReader leitor;
    private PrintWriter escritor;
    private Status status;
    private List<OuvinteStatusClient> ouvintes;
    private boolean acabouJogo;

    public JogoDaVelhaClient() {
        ouvintes = new ArrayList<OuvinteStatusClient>();
    }

    public void iniciar(){
        try{
            cliente = new Socket("localhost", 1234);
            escritor = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream()), true);

            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            this.leitor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void liberarRecursos(){
        try{
            escritor.close();
            leitor.close();
            cliente.close();
        } catch(IOException ex){
            ex.printStackTrace();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void leitor(){
        try{
            //acabouJogo = entrada.readLine().contains("acabou");

            while(!acabouJogo()){
                String texto = leitor.readLine();
                String[] mensagem = texto.split("\\|");

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
        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            this.liberarRecursos();
        }
    }

    private void atualizarStatusGeral(String statusGeral){
        if(statusGeral.equals("conectado")){
            this.fireAtualizarMensagem("Você está conectado no server");
        } else if(statusGeral.equals("acabou")){
            this.fireAcabouJogo();
        } else if(statusGeral.equals("seuTurno")){
            this.fireSeuTurno();
        } else if(statusGeral.equals("acabouTurno")){
            this.fireAcabouTurno();
        }
    }

    private void obterRespostaJogada(String resposta){
        if(resposta.equals("sucesso")){
        } else if(resposta.equals("posicaoOcupada")){
            this.firePosicaoOcupada();
        }
    }

    public void jogar(Integer posicao) throws IOException {
        escritor.println("jogar|" + posicao);
    }

    public void addOuvinteStatus(OuvinteStatusClient ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void removeOuvinteStatus(OuvinteStatusClient ouvinte) {
        ouvintes.remove(ouvinte);
    }

    private boolean acabouJogo() {
        return this.acabouJogo;
    }

    private void fireMudouStatusJogo(){
        for(OuvinteStatusClient ouvinte : ouvintes){
            ouvinte.mudouEstadoJogo(status);
        }
    }

    private void fireComecouJogo(){
        for(OuvinteStatusClient ouvinte : ouvintes){
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

    private void fireSeuTurno(){
        for(OuvinteStatusClient ouvinte : ouvintes){
            ouvinte.seuTurno();
        }
    }

    private void fireAcabouTurno(){
        for(OuvinteStatusClient ouvinte : ouvintes){
            ouvinte.acabouTurno();
        }
    }
}