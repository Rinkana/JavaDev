package com.greenview.classes;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;

/**
 * Created by max on 19-4-15.
 */
public class DbHandle {
    private DbEnvironmentHandle DbEnvironment = null;
    private Database dbHandle = null;

    public DbHandle(DbEnvironmentHandle DbEnvironment){
        this.DbEnvironment = DbEnvironment;

        this.connect();
    }

    private void connect(){
        try{
            DatabaseConfig dbconfig = new DatabaseConfig();
            dbconfig.setAllowCreate(true);
            dbHandle = this.DbEnvironment.getEnvironment().openDatabase(null, "transactions", dbconfig);

            System.out.println("DB connection made!");
        }catch (DatabaseException dbe){
            System.out.println(dbe.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect(){
        try{
            if(dbHandle != null){
                dbHandle.close();
            }
        }catch (DatabaseException dbe){
            System.out.println(dbe.toString());
        }
    }

    public void updateEnvironment(DbEnvironmentHandle DbEnvironment){
        this.disconnect();
        this.DbEnvironment = DbEnvironment;
        this.connect();
    }
}
