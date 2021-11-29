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
            server.close();  //chiudo il server per inibire gli altri client
            inDalClient = new BufferedReader(new InputStreamReader (client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
            outVersoClient.writeBytes("connessione effettuata \n"); //messaggio di connessione effettuata 
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
                stringaRicevuta = inDalClient.readLine(); //messaggio dal client
                if(stringaRicevuta.equalsIgnoreCase("LISTA")){                  //se viene scritto lista
                    for(int i=0;i<listaNote.size();i+=1){
                        outVersoClient.writeBytes(listaNote.get(i) + "\n");     //for di invio delle note scritte in lista 
                    }
                    outVersoClient.writeBytes("fine lista\n");                  //messaggio per dire che non c'è altro in lista
                }else{     //se è un qualsiasi altro messaggio
                listaNote.add(stringaRicevuta); //aggiunge il messaggio in lista
                outVersoClient.writeBytes("nota Salvata \n");//messaggio che dice che la nota è salvata con successo
                }
                
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
