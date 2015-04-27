package com.greenview;

import com.greenview.classes.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("App startup init");

        try {
            DbEnvironmentHandle DbEnvironment = new DbEnvironmentHandle();
            DbHandle transactionDb = new DbHandle(DbEnvironment,"transactions", true);

            Transaction loadedTransaction = Transaction.loadFromDatabase(13, transactionDb);

            System.out.println("Transaction loaded: " + loadedTransaction.getTransactionID());
            System.out.println("Sender: " + loadedTransaction.getSenderHex());
            System.out.println("Receiver: " + loadedTransaction.getReceiverHex());

            Scanner userInput = new Scanner(System.in);
            //PeerConnector peerConnector = new PeerConnector();

            try{

                Server server = new Server();
                

                Client client = new Client("Localhost",server.PORT);

                try{
                    ServerClient serverClient = server.acceptConnectionRequests();

                    client.sendMessage("TESTBERICHT!!!!");

                    serverClient.readIncomming();

                    serverClient.close();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }



                client.close();
                server.close();

                //peerConnector.sendMessage("testbericht");
                //peerConnector.checkForMessage();
                //peerConnector.exit();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }


            //System.out.println("Test input");
            //System.out.println(userInput.next());


            transactionDb.disconnect();
            DbEnvironment.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
