package client;

import java.net.Socket;
import eventos.OuvinteStatus;
import eventos.Status;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private Socket cliente;
    private BufferedReader entrada;
    private BufferedWriter saida;
    private Status status;
    private List<OuvinteStatus> ouvintes;

    public Client() {
        //try {
            ouvintes = new ArrayList<OuvinteStatus>();
        //} catch (IOException ex) {
        //    ex.printStackTrace();
        //}
    }

    public void iniciar(){
        try{
            cliente = new Socket("localhost", 1234);
            saida = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            saida.flush();
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void jogar(Integer posicao) throws IOException {
        saida.write("jogar|" + posicao);
    }

    public void atualizarStatus() {
    }

    public void addOuvinteStatus(OuvinteStatus ouvinte) {
    }

    public void removeOuvinteStatus(OuvinteStatus ouvinte) {
    }

    private void fireMudouStatusJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.mudouEstadoJogo(status);
        }
    }

    private void fireEmpatouJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.empatouJogo(status);
        }
    }

    private void fireAcabouJogo(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.acabouJogo(status);
        }
    }

    private void firePosicaoOcupada(){
        for(OuvinteStatus ouvinte : ouvintes){
            ouvinte.posicaoOcupada(status);
        }
    }
}