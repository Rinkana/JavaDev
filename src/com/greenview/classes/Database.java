package com.greenview.classes;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;

/**
 * Created by max on 19-4-15.
 */
public class Database {

    Environment myDbEnvironment = null;
    public Database(){

        this.connect();

        this.disconnect();

    }

    private void connect(){

        try {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);

            //boolean dirCreated = new File("./transactions").mkdir();
            myDbEnvironment = new Environment(new File("./transactions"), envConfig);
        }catch (DatabaseException dbe){
            System.out.println("DB connection issues");
        }
    }

    public void disconnect(){
        try{
            if(myDbEnvironment != null){
                myDbEnvironment.close();
            }else{
                System.out.println("Unable to close environment, no environment open.");
            }
        }catch (DatabaseException dbe){
            System.out.println("Unable to close the environment");
        }
    }
}
