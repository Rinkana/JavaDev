package com.greenview;

import com.greenview.classes.DbEnvironmentHandle;
import com.greenview.classes.DbHandle;
import com.greenview.classes.Transaction;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("App startup init");

        Transaction transaction = new Transaction(12);
        System.out.println("Transaction loaded: " + transaction.getTransactionID());
        System.out.println("Sender: " + transaction.getSender());
        System.out.println("Receiver: " + transaction.getReceiver());

        try {
            DbEnvironmentHandle DbEnvironment = new DbEnvironmentHandle();
            DbHandle DbConnection = new DbHandle(DbEnvironment);

            DbConnection.disconnect();
            DbEnvironment.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
