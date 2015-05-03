package com.greenview.classes;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * @Author: max
 * @Date: 2-5-15
 * @Project: bbs
 */
public class TransactionGroupTupleBinding extends TupleBinding {

    public Object entryToObject(TupleInput ti) {
        TransactionGroup transactionGroup = new TransactionGroup();

        transactionGroup.setHash(ti.readString());
        transactionGroup.setPrevHash(ti.readString());

        return transactionGroup;
    }

    public void objectToEntry(Object object, TupleOutput to) {
        TransactionGroup transactionGroup = (TransactionGroup)object;

        to.writeString(transactionGroup.getHash());
        to.writeString(transactionGroup.getPrevHash());
    }
}
