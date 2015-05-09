package com.greenview.classes;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

import java.io.UnsupportedEncodingException;

/**
 * @Author: max
 * @Date: 26-4-15
 * @Project: bbs
 */
public class TransactionTupleBinding extends TupleBinding {

    public void objectToEntry(Object object, TupleOutput to){
        Transaction transaction = (Transaction)object;

        //Warning: Write the writes in the same order as entryToObject
        //to.writeInt(transaction.getTransactionID());
        try {
            to.writeString(new String(transaction.getPreviousTransactionID(), "UTF-8"));
            to.writeLong(transaction.getSender());
            to.writeLong(transaction.getReceiver());
            to.writeLong(transaction.getAmount());
        } catch (Exception e) { }
    }

    public Object entryToObject(TupleInput ti){
        Transaction transaction = new Transaction();

        //Warning: Write the sets in the same order as used in objectToEntry
        //transaction.setTransactionID(ti.readInt());
        try {
            transaction.setPreviousTransactionID(ti.readString().getBytes("UTF-8"));
            transaction.setSender(ti.readLong());
            transaction.setReceiver(ti.readLong());
            transaction.setAmount(ti.readLong());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transaction;
    }
}
