package com.greenview.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: max
 * @Date: 27-4-15
 * @Project: bbs
 */
public class ServerClient {

    private SocketChannel clientConnection  = null;
    private ByteBuffer byteBuffer           = null;
    private Boolean clientConnected         = false;

    public ServerClient(SocketChannel clientConnection){
        this.clientConnection = clientConnection;
        this.clientConnected = true;

        //this.readIncomming();
    }

    public void readIncomming(){
        //while(this.clientConnected){
            try{

                this.byteBuffer = ByteBuffer.allocate(48);
                Integer bytesRead = this.clientConnection.read(this.byteBuffer);

                System.out.println(new String(this.byteBuffer.array()));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        //}
    }

    public void close(){
        try{
            this.clientConnection.close();
            this.clientConnected = false;
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
