package eventos;

public interface OuvinteStatus {
    void mudouEstadoJogo(Status statusJogo);

    void acabouJogo(Status statusJogo);

    void posicaoOcupada(Status statusJogo);

    void atualizarMensagem(String msg);
}
