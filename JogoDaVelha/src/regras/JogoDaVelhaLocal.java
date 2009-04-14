package regras;

import java.util.ArrayList;
import java.util.List;

import regras.eventos.Status;
import regras.eventos.OuvinteStatus;

public class JogoDaVelhaLocal implements JogoDaVelha {
    private String[] posicoes;
    private String[] jogadores;
    private int jogadorCorrente;
    private Status status;
    private List<OuvinteStatus> ouvintes;

    public JogoDaVelhaLocal(){
        posicoes = new String[9];
        jogadores = new String[2];
        ouvintes = new ArrayList<OuvinteStatus>();
    }

    public void iniciar(String jogador1, String jogador2) {        
        for(int i = 0; i < posicoes.length; i++){
            posicoes[i] = "";
        }

        jogadores[0] = jogador1;
        jogadores[1] = jogador2;
        jogadorCorrente = (int) (Math.random() * 2);
        status = new Status(jogadores[jogadorCorrente], posicoes);
        
        this.atualizarStatus();
    }

    public void jogar(int posicao) {
        if(this.posicaoOcupada(posicao)){
            this.firePosicaoOcupada();
            return;
        }
        
        posicoes[posicao] = jogadores[jogadorCorrente];
        jogadorCorrente = (jogadorCorrente + 1) % 2;
        status.setPosicaoPressionada(posicao);
        status.setJogadorCorrente(jogadores[jogadorCorrente]);
        
        this.atualizarStatus();

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

    public String getJogadorCorrente(){
        return this.jogadores[jogadorCorrente];
    }
    
    private boolean posicaoOcupada(int posicao){
        return !posicoes[posicao].equals("");
    }

    public void atualizarStatus() {
        this.fireMudouStatusJogo();
    }
    
    public void addOuvinteStatus(OuvinteStatus ouvinte){
        ouvintes.add(ouvinte);
    }
    
    public void removeOuvinteStatus(OuvinteStatus ouvinte){
        ouvintes.remove(ouvinte);
    }
    
    private void fireMudouStatusJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.mudouEstadoJogo(status);
        }
    }

    private void fireEmpatouJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.empatouJogo(status);
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
}