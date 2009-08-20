package eventos;

public interface OuvinteStatusClient extends OuvinteStatus {
    void conectou();
    
    void seuTurno();

    void acabouTurno();

    void seuJogadorEh(String jogador);
}
