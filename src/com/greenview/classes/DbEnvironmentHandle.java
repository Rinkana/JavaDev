package com.greenview.classes;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;
import java.nio.file.Files;

/**
 * @Author: max
 * @Date: 19-4-15
 * @Project: bbs
 */
public class DbEnvironmentHandle {
    final String ENVIRONMENT_LOCATION = "./transactions";

    private Environment envHandle = null;

    public DbEnvironmentHandle(){
        this.connect();
    }

    private void connect(){
        try {
            //Environment config that is always the same.
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setTransactional(true);

            //We need a folder for the environment. It does not create itself so we do it for him.
            File envLocation = new File(ENVIRONMENT_LOCATION);
            if(!envLocation.exists()){
                boolean dircreated = envLocation.mkdir();
            }
            //Connect to the environment
            envHandle = new Environment(new File(ENVIRONMENT_LOCATION), envConfig);
        }catch (DatabaseException dbe){
            System.out.println("DB connection issues");
        }
    }

    public void disconnect(){
        try{
            if(envHandle != null){
                envHandle.close();
            }else{
                System.out.println("Unable to close environment, no environment open.");
            }
        }catch (DatabaseException dbe){
            System.out.println("Unable to close the environment");
        }
    }

    public Environment getEnvironment() throws Exception {
        if(this.envHandle != null){
            return this.envHandle;
        }else{
            throw new Exception("Unable to return environment. None set");
        }
    }
}
