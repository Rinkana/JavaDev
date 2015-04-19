package com.greenview;

import com.greenview.classes.Database;
import com.greenview.classes.Transaction;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("App startup init");

        Transaction transaction = new Transaction(12);
        System.out.println("Transaction loaded: " + transaction.getTransactionID());
        System.out.println("Sender: " + transaction.getSender());
        System.out.println("Receiver: " + transaction.getReceiver());

        Database database = new Database();

    }
}
