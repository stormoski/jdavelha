package eventos;

public class Status {
    private String jogadorCorrente;
    private String[] posicoes;
    private int posicaoPressionada;
        
    public Status(String jogadorCorrente, String[] posicoes){
        this.setJogadorCorrente(jogadorCorrente);
        this.setPosicoes(posicoes);
    }

    public Status(){
        posicoes = new String[9];
        for(int i = 0; i < posicoes.length; i++){
            posicoes[i] = "";
        }
    }

    public String getJogadorCorrente() {
        return jogadorCorrente;
    }

    public String[] getPosicoes(){
        return this.posicoes;
    }

    public void setJogadorCorrente(String jogadorCorrente) {
        this.jogadorCorrente = jogadorCorrente;
    }

    public String getPosicao(int posicao) {
        return posicoes[posicao];
    }

    public void setPosicoes(String[] posicoes) {
        this.posicoes = posicoes;
    }
    
    public int getPosicaoPressionada(){
        return this.posicaoPressionada;
    }

    public void setPosicaoPressionada(int posicaoPressionada){
        this.posicaoPressionada = posicaoPressionada;
    }
}
