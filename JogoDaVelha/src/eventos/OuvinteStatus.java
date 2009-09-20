package eventos;

public interface OuvinteStatus {
    void mudouEstadoJogo(Status statusJogo);

    void acabouJogo(Status statusJogo);

    void posicaoOcupada(String posicao);

    void atualizarMensagem(String msg);
}
