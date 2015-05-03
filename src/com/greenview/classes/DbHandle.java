package com.greenview.classes;

import com.sleepycat.je.*;

import java.io.UnsupportedEncodingException;

/**
 * @Author: max
 * @Date: 19-4-15
 * @Project: bbs
 */
public class DbHandle {
    private DbEnvironmentHandle dbEnvironment = null;
    private Database DbHandle = null;
    private String DbName = "";

    public DbHandle(DbEnvironmentHandle dbEnvironment, String DbName, boolean allowDuplicates){
        this.dbEnvironment = dbEnvironment;

        this.DbName = DbName;

        this.connect(allowDuplicates);
    }

    private void connect(boolean allowDuplicates){
        try{
            //Default database config. Is not variable
            DatabaseConfig dbconfig = new DatabaseConfig();
            dbconfig.setAllowCreate(true);
            dbconfig.setSortedDuplicates(allowDuplicates);
            dbconfig.setTransactional(true);
            DbHandle = this.dbEnvironment.getEnvironment().openDatabase(null, this.DbName, dbconfig);

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

            //BerkeleyDb requires bytearrays for values and needs to be within a DatabaseEntry class.
            DatabaseEntry DbEntryKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry DbEntryValue = new DatabaseEntry(value.getBytes("UTF-8"));

            this.DbHandle.putNoOverwrite(null,DbEntryKey, DbEntryValue);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertRecord(DatabaseEntry DbEntryKey, DatabaseEntry DbEntryValue){
        try{
            this.DbHandle.putNoOverwrite(null, DbEntryKey, DbEntryValue);
        }catch (Exception e){
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

    public void updateEnvironment(DbEnvironmentHandle dbEnvironment){
        //I have no idea if this function will be used once.
        this.disconnect();
        this.dbEnvironment = dbEnvironment;
        //this.connect();
    }
}
