package eventos;

public interface OuvinteStatus {
    void mudouEstadoJogo(Status statusJogo);

    public void conectou(String jogador);

    void acabouJogo(Status statusJogo);

    void posicaoOcupada(Status statusJogo);

    void atualizarMensagem(String msg);
}
