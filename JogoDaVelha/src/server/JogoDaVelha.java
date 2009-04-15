package server;

import eventos.OuvinteStatus;

public interface JogoDaVelha {
    void iniciar(String jogador1, String jogador2);
    
    void jogar(int posicao);
    
    void atualizarStatus();

    void addOuvinteStatus(OuvinteStatus ouvinte);

    void removeOuvinteStatus(OuvinteStatus ouvinte);
}
