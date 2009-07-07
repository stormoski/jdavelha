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
    private boolean acabouJogo;

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
            acabouJogo = entrada.readLine().contains("acabou");

            while(!this.acabouJogo()){
                String[] mensagem = entrada.readLine().split("|");

                if(mensagem[0].equals("status")){
                    if(mensagem[1].equals("conectado")){
                        
                    } else if(mensagem[1].equals("acabou")){

                    }
                } else if(mensagem[0].equals("")){
                }
            }
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
        ouvintes.add(ouvinte);
    }

    public void removeOuvinteStatus(OuvinteStatus ouvinte) {
        ouvintes.remove(ouvinte);
    }

    private boolean acabouJogo() {
        return this.acabouJogo;
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