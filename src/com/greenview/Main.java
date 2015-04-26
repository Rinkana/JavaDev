package com.greenview;

import com.greenview.classes.DbEnvironmentHandle;
import com.greenview.classes.DbHandle;
import com.greenview.classes.Transaction;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("App startup init");

        Transaction transaction = new Transaction();
        transaction.setTransactionID(13);
        transaction.setReceiver(1000000L);
        transaction.setSender(999999L);


        //System.out.println("Transaction loaded: " + transaction.getTransactionID());
        //System.out.println("Sender: " + transaction.getSender());
        //System.out.println("Receiver: " + transaction.getReceiver());

        try {
            DbEnvironmentHandle DbEnvironment = new DbEnvironmentHandle();
            DbHandle transactionDb = new DbHandle(DbEnvironment,"transactions", true);

            //transaction.saveToDatabase(transactionDb);

            Transaction loadedTransaction = transaction.loadFromDatabase(13, transactionDb);

            //DbHandle transactionClassDb = new DbHandle(DbEnvironment, "transactionClass", false);

            //Transaction transaction = Transaction.loadTransaction(12, transactionDb, transactionClassDb);
            System.out.println("Transaction loaded: " + loadedTransaction.getTransactionID());
            System.out.println("Sender: " + loadedTransaction.getSenderHex());
            System.out.println("Receiver: " + loadedTransaction.getReceiverHex());


            //Database DbHandle = DbConnection.getHandle();

            //transactionDb.insertRecord("een sleutel","De waarde van de sleutel");
            //transactionDb.getRecord("een sleutel");

            transactionDb.disconnect();
            //transactionClassDb.disconnect();
            DbEnvironment.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
