package com.greenview.classes;

/**
 * Created by max on 19-4-15.
 */
public class Transaction {

    private Integer     transactionID   = 0;
    private Long        sender          = 0L;
    private Long        receiver        = 0L;

    public Transaction(Integer transactionID){

        this.transactionID = transactionID;

        try {
            this.loadFromDatabase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadFromDatabase() throws Exception {
        if(this.transactionID == 0){
            throw new Exception("Unable to load transaction!");
        }

        this.sender     = 123712477L;
        this.receiver   = 12L;
    }

    public Integer getTransactionID(){
        return this.transactionID;
    }

    public String getSender(){
        String senderHex = Long.toHexString(this.sender);
        senderHex = senderHex.toUpperCase();

        return senderHex;
    }

    public String getReceiver(){
        String receiverHex = Long.toHexString(this.receiver);
        receiverHex = receiverHex.toUpperCase();

        return receiverHex;
    }
}
