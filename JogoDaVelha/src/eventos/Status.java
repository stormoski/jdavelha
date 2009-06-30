package eventos;

public class Status {
    private String jogadorCorrente;
    private String[] posicoes;
    private int posicaoPressionada;
    private String status;
    
    public Status(String jogadorCorrente, String[] posicoes){
        this.setJogadorCorrente(jogadorCorrente);
        this.setPosicoes(posicoes);
    }

    public String getJogadorCorrente() {
        return jogadorCorrente;
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

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
