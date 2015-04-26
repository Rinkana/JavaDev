package com.greenview.classes;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * @Author: max
 * @Date: 26-4-15
 * @Project: bbs
 */
public class TransactionTupleBinding extends TupleBinding {

    public void objectToEntry(Object object, TupleOutput to){
        Transaction transaction = (Transaction)object;

        //Warning: Write the writes in the same order as entryToObject
        to.writeInt(transaction.getTransactionID());
        to.writeLong(transaction.getSender());
        to.writeLong(transaction.getReceiver());
    }

    public Object entryToObject(TupleInput ti){
        Transaction transaction = new Transaction();

        //Warning: Write the sets in the same order as used in objectToEntry
        transaction.setTransactionID(ti.readInt());
        transaction.setSender(ti.readLong());
        transaction.setReceiver(ti.readLong());

        return transaction;
    }
}
