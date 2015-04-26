package com.greenview.classes;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;

import java.io.Serializable;

/**
 * @Author: max
 * @Date: 19-4-15
 * @Project: bbs
 */
public class Transaction implements Serializable {

    private Integer     transactionID   = 0;
    private Long        sender          = 0L;
    private Long        receiver        = 0L;

    public Transaction(){

    }

    public void setTransactionID(Integer transactionID){
        this.transactionID = transactionID;
    }

    public Integer getTransactionID(){
        return this.transactionID;
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

    public void saveToDatabase(DbHandle database){
        try{
            TupleBinding tupleBinding = new TransactionTupleBinding();
            DatabaseEntry dbEntryKey = new DatabaseEntry(this.getTransactionID().toString().getBytes("UTF-8"));
            DatabaseEntry dbEntryValue = new DatabaseEntry();

            tupleBinding.objectToEntry(this, dbEntryValue);

            database.insertRecord(dbEntryKey,dbEntryValue);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static Transaction loadFromDatabase(Integer key, DbHandle database){
        try {
            TupleBinding tupleBinding = new TransactionTupleBinding();

            DatabaseEntry dbEntryKey = new DatabaseEntry(key.toString().getBytes("UTF-8"));
            DatabaseEntry dbEntryValue = new DatabaseEntry();

            database.getHandle().get(null, dbEntryKey, dbEntryValue, LockMode.DEFAULT);

            return (Transaction) tupleBinding.entryToObject(dbEntryValue);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Transaction();
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
}
