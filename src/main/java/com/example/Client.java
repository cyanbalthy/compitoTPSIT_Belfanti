package com.example;
import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";    //indirizzo del server
    int portaServer = 6789;              //porta x servizio
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    BufferedReader inDalServer;                  //stream di input
    DataOutputStream outVersoServer;                //stream di output

    public Socket connetti (){
        try{
            //input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            //creo un socket
            mioSocket = new Socket (nomeServer, portaServer);
            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println(stringaRicevutaDalServer);
        }catch(UnknownHostException e){
            System.err.println("host non riconosciuto"); //messaggio errore
        }catch(Exception e){
            System.out.println(e.getMessage()); //messaggio errore
            System.out.println("errore durante la connessione");
            System.exit(1);
        }
        
        return mioSocket;
    }

    public void comunica(){
        for(;;){
            try{
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println(stringaRicevutaDalServer );
            stringaUtente = tastiera.readLine();
            outVersoServer.writeBytes(stringaUtente + "\n");
            if(stringaUtente.equalsIgnoreCase("LISTA")){
                for(;;){
                    stringaRicevutaDalServer = inDalServer.readLine();
                    if(stringaRicevutaDalServer.equalsIgnoreCase("fine lista")){
                        break;
                    }
                    System.out.println(stringaRicevutaDalServer );
                }
            }else{
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println(stringaRicevutaDalServer);
            }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
