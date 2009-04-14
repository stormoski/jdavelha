package rede;

import java.net.Socket;
import regras.JogoDaVelha;
import regras.eventos.OuvinteStatus;

public class JogoDaVelhaServer implements JogoDaVelha {
    private Socket cliente;

    public JogoDaVelhaServer(){
        cliente = new Socket();
    }

    public void iniciar(String jogador1, String jogador2) {
    }

    public void jogar(int posicao) {
    }

    public void atualizarStatus() {
    }

    public void addOuvinteStatus(OuvinteStatus ouvinte) {
    }

    public void removeOuvinteStatus(OuvinteStatus ouvinte) {
    }

}
