package client.rede;

import java.net.Socket;
import server.JogoDaVelha;
import server.eventos.OuvinteStatus;

public class JogoDaVelhaServerProxy implements JogoDaVelha {
    private Socket cliente;

    public JogoDaVelhaServerProxy(){
        cliente = new Socket();
    }

    public void iniciar(String jogador1, String jogador2){
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