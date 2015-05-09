package com.greenview.classes;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * @Author: max
 * @Date: 19-4-15
 * @Project: bbs
 */
public class Transaction{

    private byte[]      transactionID;
    private byte[]      previousTransactionID;
    private Long        sender              = 0L;
    private Long        receiver            = 0L;
    private Long        amount              = 0L;
    private String      TransactionGroup    = "";

    public Transaction(){

    }

    private void setTransactionID(byte[] transactionID){
        this.transactionID = transactionID;
    }

    public byte[] getTransactionID(){
        return this.transactionID;
    }

    public byte[] getPreviousTransactionID() {
        return this.previousTransactionID;
    }

    public void setPreviousTransactionID(byte[] previousTransactionID) {
        this.previousTransactionID = previousTransactionID;
    }

    public void setSender(Long sender){
        this.sender = sender;
    }

    public String getSenderHex(){
        String senderHex = Long.toHexString(this.sender);
        senderHex = senderHex.toUpperCase();

        return senderHex;
    }

    public Long getSender(){
        return this.sender;
    }

    public void setReceiver(Long receiver){
        this.receiver = receiver;
    }

    public String getReceiverHex(){
        String receiverHex = Long.toHexString(this.receiver);
        receiverHex = receiverHex.toUpperCase();

        return receiverHex;
    }

    public Long getReceiver(){
        return this.receiver;
    }


    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void saveToDatabase(DbHandle database){
        try{
            //Load the last saved transaction ID.
            String previousKey = "";
            try{
                previousKey = database.getLastRecord();
            }catch(Exception e){ }

            this.setPreviousTransactionID(previousKey.getBytes("UTF-8"));

            TupleBinding tupleBinding = new TransactionTupleBinding();
            DatabaseEntry dbEntryValue = new DatabaseEntry();

            tupleBinding.objectToEntry(this, dbEntryValue);

            //Hash the data genarated
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(dbEntryValue.toString().getBytes("UTF-8"));
            byte[] digest = md.digest();
            String key = digest.toString();
            DatabaseEntry dbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));


            System.out.println(dbEntryKey.toString());
            System.out.println(digest);

            //That was all, for now. Lets save!
            database.insertRecord(dbEntryKey, dbEntryValue);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static Transaction loadFromDatabase(String key, DbHandle database) throws Exception {
        try {
            TupleBinding tupleBinding = new TransactionTupleBinding();

            DatabaseEntry dbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry dbEntryValue = new DatabaseEntry();

            OperationStatus getStatus = database.getHandle().get(null, dbEntryKey, dbEntryValue, LockMode.DEFAULT);

            if( getStatus == OperationStatus.SUCCESS ){
                Transaction transaction = (Transaction) tupleBinding.entryToObject(dbEntryValue);
                transaction.setTransactionID(key.getBytes("UTF-8"));
                return transaction;
            }else if(getStatus == OperationStatus.NOTFOUND){
                System.out.println("NOOO KEY IS NOT FOUND HERR");
            }

        } catch (Exception e) {
        }

        throw new Exception("Record not found");
    }




    /*
    public void saveToDatabase(DbHandle mainDb, DbHandle catalogDb){
        try {

            StoredClassCatalog classCatalog = new StoredClassCatalog(catalogDb.getHandle());
            EntryBinding dataBinding = new SerialBinding<>(classCatalog, this.getClass());

            String key = this.getTransactionID().toString();

            DatabaseEntry DbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry DbEntryValue = new DatabaseEntry();

            dataBinding.objectToEntry(this, DbEntryValue);

            mainDb.insertRecord(DbEntryKey, DbEntryValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Transaction loadTransaction(Integer key, DbHandle mainDb, DbHandle catalogDb){
        try {
            StoredClassCatalog classCatalog = new StoredClassCatalog(catalogDb.getHandle());
            EntryBinding dataBinding = new SerialBinding<>(classCatalog, Transaction.class);

            DatabaseEntry DbEntryKey = new DatabaseEntry( key.toString().getBytes("UTF-8") );
            DatabaseEntry DbEntryValue = new DatabaseEntry();

            mainDb.getHandle().get(null, DbEntryKey, DbEntryValue, LockMode.DEFAULT);

            Transaction transaction = (Transaction) dataBinding.entryToObject(DbEntryValue);

            return transaction;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new Transaction();
    }*/

    //Iedereen is enkel een verstuurder van hun eigen gegevens.
    //Op het moment dat iemand een nieuwe transactie maakt stuurt hij dit naar de rest van zijn connecties.
    //En die sturen het ook weer door naar de
    //Een client kan 2 dingen doen.
    //1. Nieuwe transactie inserten
    //2. Clients vragen te transactie te verwijderen als hij niet valiede is.
}
