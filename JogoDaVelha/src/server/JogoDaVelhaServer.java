package server;

import eventos.Status;
import eventos.OuvinteStatusServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class JogoDaVelhaServer {
    private String[] posicoes;
    private String[] jogadores;
    private Map<String, Socket> mapaJogadores;
    private int jogadorCorrente;
    private Status status;
    private List<OuvinteStatusServer> ouvintes;
    private ServerSocket server;
    private boolean acabouJogo;

    public JogoDaVelhaServer() {
        try{
            posicoes = new String[9];
            jogadores = new String[2];
            mapaJogadores = new HashMap<String, Socket>();
            ouvintes = new ArrayList<OuvinteStatusServer>();
            server = new ServerSocket(1234);
            acabouJogo = false;
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void iniciar() {
        try{
            for(int i = 0; i < posicoes.length; i++){
                posicoes[i] = "";
            }

            mapaJogadores.put("X", server.accept());
            this.getWriter("X").flush();
            this.getReader("X");
            this.fireNovaConexao("X", mapaJogadores.get("X").getInetAddress().getHostAddress());

            this.escrever("X", "statusGeral|conectado");

            mapaJogadores.put("O", server.accept());
            this.getWriter("O").flush();
            this.getReader("O");
            this.fireNovaConexao("O", mapaJogadores.get("O").getInetAddress().getHostAddress());

            this.escrever("O", "statusGeral|conectado");
            this.escrever("statusGeral|iniciarJogo");

            jogadores[0] = "X";
            jogadores[1] = "O";
            jogadorCorrente = (int) (Math.random() * 2);
            status = new Status();

            this.atualizarStatus();
            
            this.leitor();
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        } finally {
            this.liberarRecursos();
        }
    }

    private void liberarRecursos(){
        try{
            for(String s : jogadores){
                this.getReader(s).close();
                this.getWriter(s).close();
            }

            server.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void leitor(){
        try {
            while(!acabouJogo()) {
                String linha = getReader().readLine();

                if(linha.split("|")[0].equals("jogar")) {
                    jogar(Integer.parseInt(linha.split("|")[1]));
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void jogar(int posicao) {
        
        if(this.posicaoOcupada(posicao)){
            this.firePosicaoOcupada(posicao);
            return;
        }
        
        posicoes[posicao] = jogadores[jogadorCorrente];
        jogadorCorrente = (jogadorCorrente + 1) % 2;
        
        this.atualizarStatus(posicao);

        if(this.existeGanhador()){
            this.fireAcabouJogo();
        } else if(this.todasPosicoesOcupadas()){
            this.fireEmpatouJogo();
        }
    }

    private boolean todasPosicoesOcupadas(){
        for(String s : posicoes){
            if(s == null || s.equals("")){
                return false;
            }
        }

        return true;
    }

    private boolean existeGanhador(){
        if (this.compararSequencia(0, 3, 6) ||
                this.compararSequencia(0, 1, 2) ||
                    this.compararSequencia(0, 4, 8) ||
                        this.compararSequencia(1, 4, 7) ||
                            this.compararSequencia(2, 5, 8) ||
                                this.compararSequencia(2, 4, 6) ||
                                    this.compararSequencia(3, 4, 5) ||
                                        this.compararSequencia(6, 7, 8)) {
            return true;
        }

        return false;
    }

    private boolean compararSequencia(int posicao1, int posicao2, int posicao3){
        if(posicoes[posicao1] == null || posicoes[posicao1].equals("") ||
                posicoes[posicao2] == null || posicoes[posicao2].equals("") ||
                    posicoes[posicao3] == null || posicoes[posicao3].equals("")){
            return false;
        } else {
            return posicoes[posicao1].equals(posicoes[posicao2]) && posicoes[posicao2].equals(posicoes[posicao3]);
        }
    }

    private boolean acabouJogo(){
        return this.acabouJogo;
    }

    private void escrever(String jogador, String mensagem) {
        try{
            getWriter(jogador).write(mensagem);
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void escrever(String mensagem) {
        for(String s : jogadores) {
            this.escrever(s, mensagem);
        }
    }

    private BufferedWriter getWriter(){
        return this.getWriter(jogadores[jogadorCorrente]);
    }

    private BufferedWriter getWriter(String jogador){
        BufferedWriter writer = null;

        try{
            Socket socket = mapaJogadores.get(jogador);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        }

        return writer;
    }

    private BufferedReader getReader(){
        return this.getReader(jogadores[jogadorCorrente]);
    }

    private BufferedReader getReader(String jogador){
        BufferedReader reader = null;

        try{
            Socket socket = mapaJogadores.get(jogador);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        }

        return reader;
    }

    public String getJogadorCorrente(){
        return this.jogadores[jogadorCorrente];
    }
    
    private boolean posicaoOcupada(int posicao){
        return !posicoes[posicao].equals("");
    }

    public void atualizarStatus(int posicao) {
        status.setPosicaoPressionada(posicao);
        status.setJogadorCorrente(jogadores[jogadorCorrente]);
        
        this.atualizarStatus();
    }

    public void atualizarStatus() {
        this.escrever("posicoes|");
        String msg = "";
        for(String posicao : status.getPosicoes()) {
            msg += posicao + ",";
        }
        this.escrever(msg.subSequence(0, msg.length()-1).toString());
        this.escrever("jogadorCorrente|" + status.getJogadorCorrente());

        this.fireMudouStatusJogo();
    }
    
    public void addOuvinteStatus(OuvinteStatusServer ouvinte){
        ouvintes.add(ouvinte);
    }
    
    public void removeOuvinteStatus(OuvinteStatusServer ouvinte){
        ouvintes.remove(ouvinte);
    }
    
    private void fireMudouStatusJogo(){
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.mudouEstadoJogo(status);
        }
    }

    private void fireEmpatouJogo(){
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.comecouJogo(status);
        }
    }

    private void fireAcabouJogo(){
        this.escrever("statusGeral|acabou");
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.acabouJogo(status);
        }
    }

    private void firePosicaoOcupada(int posicao){
        this.escrever("posicaoOcupada|" + posicao);
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.posicaoOcupada(status);
        }
    }

    private void fireNovaConexao(String jogador, String ip){
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.novaConexao(jogador, ip);
        }
    }

    private void fireDesconectou(String jogador, String ip){
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.desconectou(jogador, ip);
        }
    }

    private void fireErro(String mensagem){
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.erro(mensagem);
        }
    }
}