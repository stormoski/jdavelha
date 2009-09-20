package server;

import eventos.Status;
import eventos.OuvinteStatusServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class JogoDaVelhaServer {
    private String[] jogadores;
    private Map<String, Socket> mapaJogadores;
    private Map<String, PrintWriter> mapaEscritores;
    private Map<String, BufferedReader> mapaLeitores;
    private int jogadorCorrente;
    private Status status;
    private List<OuvinteStatusServer> ouvintes;
    private ServerSocket server;
    private boolean acabouJogo;

    public JogoDaVelhaServer() {
        try{
            jogadores = new String[2];
            mapaJogadores = new HashMap<String, Socket>();
            mapaEscritores = new HashMap<String, PrintWriter>();
            mapaLeitores = new HashMap<String, BufferedReader>();
            ouvintes = new ArrayList<OuvinteStatusServer>();
            server = new ServerSocket(1234);
            status = new Status();
            acabouJogo = false;
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void iniciar() {
        try{
            mapaJogadores.put("X", server.accept());
            this.inicializarJogador("X");
            jogadores[0] = "X";

            mapaJogadores.put("O", server.accept());
            this.inicializarJogador("O");
            jogadores[1] = "O";
            
            jogadorCorrente = (int) (Math.random() * 2);

            this.atualizarStatus();
            this.leitor();
        } catch(IOException ex){
            this.fireErro(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void inicializarJogador(String jogador) {
        try {
            BufferedReader leitor = new BufferedReader(new InputStreamReader(mapaJogadores.get(jogador).getInputStream()));
            mapaLeitores.put(jogador, leitor);
            
            PrintWriter escritor = new PrintWriter(new OutputStreamWriter(mapaJogadores.get(jogador).getOutputStream()), true);
            mapaEscritores.put(jogador, escritor);

            this.fireConectou(jogador, mapaJogadores.get(jogador).getInetAddress().getHostAddress());
            this.escrever(jogador, "jogador|" + jogador);
        } catch (IOException ex) {
            ex.printStackTrace();
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
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void leitor(){
        try {
            while(!acabouJogo()) {
                String linha = getReader().readLine();

                if(linha.split("\\|")[0].equals("jogar")) {
                    jogar(Integer.parseInt(linha.split("\\|")[1]));
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            this.liberarRecursos();
        }
    }

    private void jogar(int posicao) {
        if(this.posicaoOcupada(posicao)){
            this.firePosicaoOcupada(posicao + 1);
            return;
        }
        
        status.getPosicoes()[posicao] = jogadores[jogadorCorrente];
        jogadorCorrente = (jogadorCorrente + 1) % 2;
        
        this.atualizarStatus(posicao);

        if(this.existeGanhador()){
            this.fireAcabouJogo();
        } else if(this.todasPosicoesOcupadas()){
            this.fireEmpatouJogo();
        }

        this.escrever(jogadores[jogadorCorrente], "statusGeral|seuTurno");
        this.escrever(jogadores[(jogadorCorrente + 1) % 2], "statusGeral|acabouTurno");
    }

    private boolean todasPosicoesOcupadas(){
        for(String s : status.getPosicoes()){
            if(s == null || s.equals(" ")){
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
        if(status.getPosicoes()[posicao1] == null || status.getPosicoes()[posicao1].equals(" ") ||
                status.getPosicoes()[posicao2] == null || status.getPosicoes()[posicao2].equals(" ") ||
                    status.getPosicoes()[posicao3] == null || status.getPosicoes()[posicao3].equals(" ")){
            return false;
        } else {
            return status.getPosicoes()[posicao1].equals(status.getPosicoes()[posicao2]) && status.getPosicoes()[posicao2].equals(status.getPosicoes()[posicao3]);
        }
    }

    private boolean acabouJogo(){
        return this.acabouJogo;
    }

    private void escrever(String jogador, String mensagem) {
        try{
            getWriter(jogador).println(mensagem);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void escrever(String mensagem) {
        for(String s : jogadores) {
            this.escrever(s, mensagem);
        }
    }

    private PrintWriter getWriter(String jogador){
        return mapaEscritores.get(jogador);
    }

    private BufferedReader getReader(){
        return this.getReader(jogadores[jogadorCorrente]);
    }

    private BufferedReader getReader(String jogador){
        return mapaLeitores.get(jogador);
    }

    public String getJogadorCorrente(){
        return this.jogadores[jogadorCorrente];
    }
    
    private boolean posicaoOcupada(int posicao){
        return !status.getPosicoes()[posicao].equals(" ");
    }

    public void atualizarStatus(int posicao) {
        status.setPosicaoPressionada(posicao);
        
        this.atualizarStatus();
        this.fireMudouStatusJogo();
    }

    public void atualizarStatus() {
        status.setJogadorCorrente(this.getJogadorCorrente());
        String msg = "";
        
        for(String posicao : status.getPosicoes()) {
            msg += posicao + ",";
        }

        this.escrever("posicoes|" + msg.subSequence(0, msg.length()-1).toString());
        this.escrever("jogadorCorrente|" + status.getJogadorCorrente());
        this.escrever(jogadores[jogadorCorrente], "statusGeral|seuTurno");
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
        this.escrever("statusGeral|empatou");
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.empatou();
        }
    }

    private void fireAcabouJogo(){
        this.escrever("statusGeral|acabou");
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.acabouJogo(status);
        }
    }

    private void firePosicaoOcupada(int posicao){
        this.escrever(jogadores[jogadorCorrente], "posicaoOcupada|" + posicao);
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.posicaoOcupada(String.valueOf(posicao));
        }
    }

    private void fireConectou(String jogador, String ip){
        this.escrever(jogador, "statusGeral|conectou");
        for(OuvinteStatusServer ouvinte : ouvintes){
            ouvinte.conectou(jogador, ip);
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