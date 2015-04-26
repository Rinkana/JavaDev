package com.greenview.classes;

import com.sleepycat.je.*;

import java.io.UnsupportedEncodingException;

/**
 * @Author: max
 * @Date: 19-4-15
 * @Project: bbs
 */
public class DbHandle {
    private DbEnvironmentHandle DbEnvironment = null;
    private Database DbHandle = null;
    private String DbName = "";

    public DbHandle(DbEnvironmentHandle DbEnvironment, String DbName){
        this.DbEnvironment = DbEnvironment;

        this.DbName = DbName;

        this.connect();
    }

    private void connect(){
        try{
            DatabaseConfig dbconfig = new DatabaseConfig();
            dbconfig.setAllowCreate(true);
            dbconfig.setSortedDuplicates(true);
            dbconfig.setTransactional(true);
            DbHandle = this.DbEnvironment.getEnvironment().openDatabase(null, this.DbName, dbconfig);

            System.out.println("DB connection made!");
        }catch (DatabaseException dbe){
            System.out.println(dbe.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect(){
        try{
            if(DbHandle != null){
                DbHandle.close();
            }
        }catch (DatabaseException dbe){
            System.out.println(dbe.toString());
        }
    }

    public Database getHandle() throws Exception {
        if(DbHandle != null){
            return this.DbHandle;
        }else{
            throw new Exception("Failed to return hande, handle is not set");
        }
    }

    public void insertRecord(String key, String value){
        try {
            DatabaseEntry DbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry DbEntryValue = new DatabaseEntry(value.getBytes("UTF-8"));

            this.DbHandle.putNoOverwrite(null,DbEntryKey, DbEntryValue);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void getRecord(String key){
        try {
            DatabaseEntry DbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry DbEntryValue = new DatabaseEntry();

            if(this.DbHandle.get(null, DbEntryKey, DbEntryValue, LockMode.DEFAULT) == OperationStatus.SUCCESS){
                byte[] bytevalue = DbEntryValue.getData();
                String value = new String(bytevalue);
                System.out.println("Yo, i found this value for yerr: " + value);
            }else{
                System.out.println("Geen record gevonden");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(String key){
        try {
            DatabaseEntry DbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));

            this.DbHandle.delete(null, DbEntryKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEnvironment(DbEnvironmentHandle DbEnvironment){
        this.disconnect();
        this.DbEnvironment = DbEnvironment;
        this.connect();
    }
}
