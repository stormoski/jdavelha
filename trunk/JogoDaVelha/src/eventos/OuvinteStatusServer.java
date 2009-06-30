package eventos;

public interface OuvinteStatusServer extends OuvinteStatus {
    void novaConexao(String jogador, String ip);

    void desconectou(String jogador, String ip);

    void erro(String mensagem);
}
