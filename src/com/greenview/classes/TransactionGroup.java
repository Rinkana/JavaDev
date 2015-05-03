package com.greenview.classes;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;

/**
 * @Author: max
 * @Date: 2-5-15
 * @Project: bbs
 */
public class TransactionGroup {

    private String hash;
    private String prevHash;

    public TransactionGroup(){

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public void saveToDatabase(DbHandle database){
        try{
            TupleBinding tupleBinding = new TransactionGroupTupleBinding();
            DatabaseEntry dbEntryKey = new DatabaseEntry(this.getHash().getBytes("UTF-8"));
            DatabaseEntry dbEntryValue = new DatabaseEntry();

            tupleBinding.objectToEntry(this, dbEntryValue);

            database.insertRecord(dbEntryKey,dbEntryValue);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static TransactionGroup loadFromDatabase(String hash, DbHandle database){
        try {
            TupleBinding tupleBinding = new TransactionGroupTupleBinding();

            DatabaseEntry dbEntryKey = new DatabaseEntry(hash.getBytes("UTF-8"));
            DatabaseEntry dbEntryValue = new DatabaseEntry();

            database.getHandle().get(null, dbEntryKey, dbEntryValue, LockMode.DEFAULT);

            return (TransactionGroup) tupleBinding.entryToObject(dbEntryValue);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new TransactionGroup();
    }
}
