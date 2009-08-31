package eventos;

public interface OuvinteStatusServer extends OuvinteStatus {
    void conectou(String jogador, String ip);

    void desconectou(String jogador, String ip);

    void erro(String mensagem);

    void empatou();
}
