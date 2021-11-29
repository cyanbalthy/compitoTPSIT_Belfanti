package com.example;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ArrayList<String> listaNote = new ArrayList<>();

    public Socket attendi(){
        try{
            System.out.println("1 SERVER partito in esecuzione ...");
            server = new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader (client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
            outVersoClient.writeBytes("connessione effettuata \n");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server !");
            System.exit(1);
        }
        return client;
    }

    public void comunica(){
        for(;;){
            try{
                outVersoClient.writeBytes("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate \n");
                stringaRicevuta = inDalClient.readLine();
                if(stringaRicevuta.equalsIgnoreCase("LISTA")){
                    for(int i=0;i<listaNote.size();i+=1){
                        outVersoClient.writeBytes(listaNote.get(i) + "\n");
                    }
                    outVersoClient.writeBytes("fine lista\n");
                }else{
                listaNote.add(stringaRicevuta);
                outVersoClient.writeBytes("nota Salvata \n");
                }
                
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
