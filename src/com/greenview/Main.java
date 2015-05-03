package com.greenview;

import com.greenview.classes.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            DbEnvironmentHandle DbEnvironment = new DbEnvironmentHandle();
            DbHandle transactionDb = new DbHandle(DbEnvironment,"transactions", true);
            DbHandle transactionGroupDb = new DbHandle(DbEnvironment,"transactionGroup",true);

            Transaction loadedTransaction = Transaction.loadFromDatabase(13, transactionDb);

            //System.out.println("Transaction loaded: " + loadedTransaction.getTransactionID());
            //System.out.println("Sender: " + loadedTransaction.getSenderHex());
            //System.out.println("Receiver: " + loadedTransaction.getReceiverHex());

            ServerThread serverThread = new ServerThread();
            serverThread.startServer();
            serverThread.start();


            //System.out.println(serverThread.getServerIsRunning());

            while(!serverThread.getServerIsRunning()){
                //System.out.println("not started");
            }

            ClientGroup clients = new ClientGroup();

            Scanner userInput = new Scanner(System.in);
            Boolean running = true;

            while(running){
                System.out.println("Enter command.");
                switch(userInput.nextLine()){
                    case "s": //Stop servers
                        running = false;
                        serverThread.stopServer();
                        transactionGroupDb.disconnect();
                        transactionDb.disconnect();
                        DbEnvironment.disconnect();
                        break;
                    case "m": //Send message
                        System.out.println("Please enter your message.");
                        clients.streamMessage(userInput.nextLine());
                        break;
                    case "c": //Count connected clients
                        System.out.printf("There are %d connected clients.\n",serverThread.getConnectedClients().size());
                        break;
                    case "n": //Connect as client
                        System.out.println("Please enter the IP.");
                        clients.addClient(new Client(userInput.nextLine(),Config.connectionPort));
                        break;
                    default:
                        System.out.println("Unable to do command");
                        break;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
